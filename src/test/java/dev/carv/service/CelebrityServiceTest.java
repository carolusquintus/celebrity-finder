package dev.carv.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.javafaker.Faker;
import dev.carv.dto.Person;
import java.util.List;
import java.util.Locale;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestInstance;

@TestInstance(Lifecycle.PER_CLASS)
class CelebrityServiceTest {

  Faker faker;
  CelebrityService service;

  @BeforeAll
  void setUpAll() {
    faker = new Faker(new Locale("es"));
    service = new CelebrityService();
  }

  @Test
  @DisplayName("Should find a Celebrity in a team")
  void findCelebrity() {

    var celeb0 = celebrityPerson();

    var person0 = commonPerson(List.of(celeb0));
    var person1 = commonPerson(List.of(celeb0, person0));
    var person2 = commonPerson(List.of(celeb0));
    var person3 = commonPerson(List.of(celeb0));
    var person4 = commonPerson(List.of(celeb0, person3, person0));
    var person5 = commonPerson(List.of(celeb0));
    var person6 = commonPerson(List.of(celeb0, person3, person1, person2, person4, person0));
    var person7 = commonPerson(List.of(celeb0, person6, person3, person1, person2, person4, person0));

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

    var celeb0 = celebrityPerson();

    var person0 = commonPerson(List.of(celeb0));
    var person1 = commonPerson(List.of(celeb0, person0));
    var person2 = commonPerson(List.of(celeb0));
    var person3 = commonPerson(List.of(celeb0));
    var person4 = commonPerson(List.of(celeb0, person3, person0));
    var person5 = commonPerson(List.of(celeb0));
    var person6 = commonPerson(List.of(celeb0, person3, person1, person2, person4, person0));
    var person7 = commonPerson(List.of(celeb0, person6, person3, person1, person2, person4, person0));

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
    var celeb0 = celebrityPerson();
    var celeb1 = celebrityPerson();

    var person0 = commonPerson(List.of(celeb0));
    var person1 = commonPerson(List.of(celeb0, person0));
    var person2 = commonPerson(List.of(celeb0));
    var person3 = commonPerson(List.of(celeb1));
    var person4 = commonPerson(List.of(celeb1, person3, person0));
    var person5 = commonPerson(List.of(celeb0));
    var person6 = commonPerson(List.of(celeb1, person3, person1, person2, person4, person0));
    var person7 = commonPerson(List.of(celeb0, person6, person3, person1, person2, person4, person0));

    var team = List.of(
        celeb0,
        person0, person1, person2, person3,
        celeb1,
        person4, person5, person6, person7
    );

    var found = service.findCelebrity(team);

    assertThat(found).isEmpty();
  }

  private Person celebrityPerson() {
    return new Person(faker.name().firstName(), null);
  }

  private Person commonPerson(List<Person> people) {
    return new Person(faker.name().firstName(), people);
  }

}
