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
    var celebrity = celebrityPerson();
    var team = List.of(
        commonPerson(celebrity),
        commonPerson(celebrity),
        commonPerson(celebrity),
        celebrity,
        commonPerson(celebrity),
        commonPerson(celebrity),
        commonPerson(celebrity),
        commonPerson(celebrity)
    );

    var found = service.findCelebrity(team);

    assertThat(found).isPresent().contains(celebrity);
  }

  @Test
  @DisplayName("Should not find a Celebrity which is not a team member")
  void notFindCelebrity() {
    var celebrity = celebrityPerson();
    var team = List.of(
        commonPerson(celebrity),
        commonPerson(celebrity),
        commonPerson(celebrity),
        commonPerson(celebrity),
        commonPerson(celebrity),
        commonPerson(celebrity),
        commonPerson(celebrity)
    );

    var found = service.findCelebrity(team);

    assertThat(found).isEmpty();
  }

  @Test
  @DisplayName("Should not find any Celebrity which is not known by all team members")
  void notFindAnyCelebrity() {
    var celebrityOne = celebrityPerson();
    var celebrityTwo = celebrityPerson();
    var team = List.of(
        celebrityOne,
        commonPerson(celebrityOne),
        commonPerson(celebrityOne),
        commonPerson(celebrityOne),
        celebrityTwo,
        commonPerson(celebrityOne),
        commonPerson(celebrityTwo),
        commonPerson(celebrityOne),
        commonPerson(celebrityTwo)
    );

    var found = service.findCelebrity(team);

    assertThat(found).isEmpty();
  }

  private Person celebrityPerson() {
    return new Person(faker.name().firstName(), null);
  }

  private Person commonPerson(Person celeb) {
    return new Person(faker.name().firstName(), celeb);
  }

}
