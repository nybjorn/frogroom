package info.nyberg.frogroom.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import org.hibernate.annotations.Type;

@Entity(name = "FROGS")
@JsonIgnoreProperties({
  "hibernateLazyInitializer",
  "handler"
}) // Fixes No serializer found for class org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor
public class Frog {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "FROG_ID")
  private Long frogId;

  @Column(name = "NAME")
  private String name;

  @Column(name = "LATIN_NAME")
  private String latinName;

  @Column(name = "DESCRIPTION")
  private String description;

  @Column(name = "GENDER")
  private Integer gender;

  @Lob
  @Type(type = "org.hibernate.type.BinaryType")
  @Column(name = "PHOTO")
  private byte[] photo;

  @ManyToMany
  @JoinTable(
      name = "FROG_FOODS",
      joinColumns = @JoinColumn(name = "FROG_ID"),
      inverseJoinColumns = @JoinColumn(name = "FOOD_ID"))
  private List<Food> foods;

  public List<Food> getFoods() {
    return foods;
  }

  public void setFoods(List<Food> foods) {
    this.foods = foods;
  }

  public Frog() {}

  public byte[] getPhoto() {
    return photo;
  }

  public void setPhoto(byte[] photo) {
    this.photo = photo;
  }

  public Long getFrogId() {
    return frogId;
  }

  public void setFrogId(Long frogId) {
    this.frogId = frogId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLatinName() {
    return latinName;
  }

  public void setLatinName(String latinName) {
    this.latinName = latinName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getGender() {
    return gender;
  }

  public void setGender(Integer gender) {
    this.gender = gender;
  }
}
