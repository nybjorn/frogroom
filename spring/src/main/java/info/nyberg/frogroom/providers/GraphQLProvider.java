package info.nyberg.frogroom.providers;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import info.nyberg.frogroom.controllers.GraphQLDataFetchers;
import java.io.IOException;
import java.nio.file.Files;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
public class GraphQLProvider {
  private GraphQL graphQL;
  @Autowired GraphQLDataFetchers graphQLDataFetchers;

  @Autowired ResourceLoader resourceLoader;

  @Bean
  public GraphQL graphQL() {
    return graphQL;
  }

  private GraphQLSchema buildSchema(String sdl) {
    TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
    RuntimeWiring runtimeWiring = buildWiring();
    SchemaGenerator schemaGenerator = new SchemaGenerator();
    return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
  }

  @PostConstruct
  public void init() throws IOException {

    Resource resource = resourceLoader.getResource("classpath:schema.graphqls");
    String sdl = new String(Files.readAllBytes(resource.getFile().toPath()));

    GraphQLSchema graphQLSchema = buildSchema(sdl);
    this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
  }

  private RuntimeWiring buildWiring() {
    return RuntimeWiring.newRuntimeWiring()
        .type(
            newTypeWiring("RootQuery")
                .dataFetcher("frog", graphQLDataFetchers.getFrogByIdDataFetcher())
                .dataFetcher("frogs", graphQLDataFetchers.getFrogsDataFetcher()))
        //    .type(newTypeWiring("RootMutation")
        //            .dataFetcher("addFrog", graphQLDataFetchers.addFrogDataFetcher()))
        .build();
  }
}
