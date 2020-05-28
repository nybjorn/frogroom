package info.nyberg.frogroom;

import info.nyberg.frogroom.models.Frog;
import info.nyberg.frogroom.repositories.FrogRepositry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// https://github.com/eugenp/tutorials/tree/master/spring-boot-modules/spring-boot/src/test/java/com/baeldung/demo/boottest
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = FrogRoomApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class FrogRoomGraphQLTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private FrogRepositry frogRepositry;
    @Value("${spring.security.user.name}")
    private String userName;
    @Value("${spring.security.user.password}")
    private String password;

    @Test
    public void givenFrogs_whenGetFrogs_CorrectCredentials_thenStatus200()
            throws Exception {

        mvc.perform(post("/graphql").contentType("application/json")
                .header("Authorization", getBasicAuth())
                .content("{ \"query\": \"{ frogs {name latinName description foods { name }} }\" }"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void givenFrogs_whenGetFrogs_WrongCredentials_thenStatus401()
            throws Exception {

        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((userName+": wrong password").getBytes());

        mvc.perform(post("/graphql")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", basicAuth)
                .content("{ \"query\": \"{ frogs {name latinName description foods { name }} }\" }"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenValidInput_thenCreateFrog() throws IOException, Exception {
        Frog kermit = new Frog();
        kermit.setName("Kermit");
        kermit.setDescription("Green frog, into pigs");
        kermit.setLatinName("Hyalinobatrachium dianae");
        kermit.setGender(1);
        mvc.perform(post("/api/v1/frogs")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization",
                        getBasicAuth())
                .content("toJson(kermit)"))
                .andDo(print())
                .andExpect(status().isCreated());

        List<Frog> found = frogRepositry.findAll();
        assertThat(found).extracting(Frog::getName).contains("Kermit");
    }

    private String getBasicAuth() {
        return "Basic " + Base64.getEncoder().encodeToString((userName + ":" + password).getBytes());
    }
}
