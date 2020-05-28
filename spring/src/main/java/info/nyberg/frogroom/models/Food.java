package info.nyberg.frogroom.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity(name = "FOODS")
@JsonIgnoreProperties({
  "hibernateLazyInitializer",
  "handler"
}) // Fixes No serializer found for class org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor
public class Food {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "FOOD_ID")
  private Long foodId;

  @Column(name = "NAME")
  private String name;

  @Column(name = "DESCRIPTION")
  private String description;

  @Column(name = "NUTRION")
  private String nutrion;

  @ManyToMany(mappedBy = "foods") // I.E the foods property in Frog class
  @JsonIgnore // Circular foods -> frogs -> foods ..
  private List<Frog> frogs;

  public List<Frog> getFrogs() {
    return frogs;
  }

  public void setFrogs(List<Frog> frogs) {
    this.frogs = frogs;
  }

  public Food() {}

  public Long getFoodId() {
    return foodId;
  }

  public void setFoodId(Long foodId) {
    this.foodId = foodId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getNutrion() {
    return nutrion;
  }

  public void setNutrion(String nutrion) {
    this.nutrion = nutrion;
  }
}
