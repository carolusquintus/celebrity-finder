package dev.carv.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import com.github.javafaker.Faker;
import dev.carv.dto.Person;
import dev.carv.service.impl.CelebrityServiceLambdasImpl;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(PER_CLASS)
class CelebrityServiceLambdasImplTest {

  Faker faker;
  CelebrityService service;

  @BeforeAll
  void setUpAll() {
    faker = new Faker(new Locale("es"));
    service = new CelebrityServiceLambdasImpl();
  }

  @Test
  @DisplayName("Should find a Celebrity in a team")
  void findCelebrity() {

    var celeb0 = newPerson(null);

    var person0 = newPerson(Set.of(celeb0));
    var person1 = newPerson(Set.of(celeb0, person0));
    var person2 = newPerson(Set.of(celeb0));
    var person3 = newPerson(Set.of(celeb0));
    var person4 = newPerson(Set.of(celeb0, person3, person0));
    var person5 = newPerson(Set.of(celeb0));
    var person6 = newPerson(Set.of(celeb0, person3, person1, person2, person4, person0));
    var person7 = newPerson(Set.of(celeb0, person6, person3, person1, person2, person4, person0));

    var team = List.of(
      person0, person1, person2, person3,
      celeb0,
      person4, person5, person6, person7
    );

    var found = service.findCelebrity(team);

    assertThat(found).isPresent().contains(celeb0);
  }

  @Test
  @DisplayName("Should not find a Celebrity which is not a team member")
  void notFindCelebrity() {

    var celeb0 = newPerson(null);

    var person0 = newPerson(Set.of(celeb0));
    var person1 = newPerson(Set.of(celeb0, person0));
    var person2 = newPerson(Set.of(celeb0));
    var person3 = newPerson(Set.of(celeb0));
    var person4 = newPerson(Set.of(celeb0, person3, person0));
    var person5 = newPerson(Set.of(celeb0));
    var person6 = newPerson(Set.of(celeb0, person3, person1, person2, person4, person0));
    var person7 = newPerson(Set.of(celeb0, person6, person3, person1, person2, person4, person0));

    var team = List.of(
      person0, person1, person2, person3,
      person4, person5, person6, person7
    );

    var found = service.findCelebrity(team);

    assertThat(found).isEmpty();
  }

  @Test
  @DisplayName("Should not find any Celebrity which is not known by all team members")
  void notFindAnyCelebrity() {

    var celeb0 = newPerson(null);
    var celeb1 = newPerson(Set.of());

    var person0 = newPerson(Set.of(celeb0));
    var person1 = newPerson(Set.of(celeb0, person0));
    var person2 = newPerson(Set.of(celeb0));
    var person3 = newPerson(Set.of(celeb1));
    var person4 = newPerson(Set.of(celeb1, person3, person0));
    var person5 = newPerson(Set.of(celeb0));
    var person6 = newPerson(Set.of(celeb1, person3, person1, person2, person4, person0));
    var person7 = newPerson(Set.of(celeb0, person6, person3, person1, person2, person4, person0));

    var team = List.of(
        celeb0,
        person0, person1, person2, person3,
        celeb1,
        person4, person5, person6, person7
    );

    var found = service.findCelebrity(team);

    assertThat(found).isEmpty();
  }

  @Test
  @DisplayName("Should not find a Celebrity in one person list")
  void notFindCelebrityOnePersonList() {
    var celeb0 = newPerson(null);
    var found = service.findCelebrity(List.of(celeb0));

    assertThat(found).isEmpty();
  }

  @Test
  @DisplayName("Should not find a Celebrity on empty list")
  void notFindCelebrityEmptyList() {
    var found = service.findCelebrity(List.of());

    assertThat(found).isEmpty();
  }

  @Test
  @DisplayName("Should not find a Celebrity on null list")
  void notFindCelebrityNullList() {
    var found = service.findCelebrity(null);

    assertThat(found).isEmpty();
  }

  private Person newPerson(Set<Person> people) {
    return new Person(faker.name().firstName(), faker.name().lastName(), people);
  }

}
