package dev.carv.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import dev.carv.dto.Person;
import dev.carv.service.impl.CelebrityServiceImpl;
import dev.carv.util.provider.CommonProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@TestInstance(PER_CLASS)
class CelebrityServiceImplTest implements CommonProvider {

  CelebrityService service;

  @BeforeAll
  void setUpAll() {
    service = new CelebrityServiceImpl();
  }

  @Test
  @DisplayName("Should find a Celebrity in a team")
  void findCelebrity() {

    var celeb0 = personProvider(null);

    var person0 = personProvider(Set.of(celeb0));
    var person1 = personProvider(Set.of(celeb0, person0));
    var person2 = personProvider(Set.of(celeb0));
    var person3 = personProvider(Set.of(celeb0));
    var person4 = personProvider(Set.of(celeb0, person3, person0));
    var person5 = personProvider(Set.of(celeb0));
    var person6 = personProvider(Set.of(celeb0, person3, person1, person2, person4, person0));
    var person7 = personProvider(Set.of(celeb0, person6, person3, person1, person2, person4, person0));

    var team = new ArrayList<>(List.of(
      person0, person1, person2, person3,
      celeb0,
      person4, person5, person6, person7
    ));

    var found = service.findCelebrity(team);

    assertThat(found).isPresent().contains(celeb0);
  }

  @Test
  @DisplayName("Should not find a Celebrity which is not a team member")
  void notFindCelebrity() {

    var celeb0 = personProvider(null);

    var person0 = personProvider(Set.of(celeb0));
    var person1 = personProvider(Set.of(celeb0, person0));
    var person2 = personProvider(Set.of(celeb0));
    var person3 = personProvider(Set.of(celeb0));
    var person4 = personProvider(Set.of(celeb0, person3, person0));
    var person5 = personProvider(Set.of(celeb0));
    var person6 = personProvider(Set.of(celeb0, person3, person1, person2, person4, person0));
    var person7 = personProvider(Set.of(celeb0, person6, person3, person1, person2, person4, person0));
    var person8 = personProvider(Set.of());

    var team = List.of(
      person0, person1, person2, person3,
      person4, person5, person6, person7,
      person8
    );

    var found = service.findCelebrity(team);

    assertThat(found).isEmpty();
  }

  @Test
  @DisplayName("Should not find any Celebrity which is not known by all team members")
  void notFindAnyCelebrity() {

    var celeb0 = personProvider(null);
    var celeb1 = personProvider(Set.of());

    var person0 = personProvider(Set.of(celeb0));
    var person1 = personProvider(Set.of(celeb0, person0));
    var person2 = personProvider(Set.of(celeb0));
    var person3 = personProvider(Set.of(celeb1));
    var person4 = personProvider(Set.of(celeb1, person3, person0));
    var person5 = personProvider(Set.of(celeb0));
    var person6 = personProvider(Set.of(celeb1, person3, person1, person2, person4, person0));
    var person7 = personProvider(Set.of(celeb0, person6, person3, person1, person2, person4, person0));
    var person8 = personProvider(Set.of());

    var team = List.of(
      celeb0,
      person0, person1, person2, person3,
      celeb1,
      person4, person5, person6, person7,
      person8
    );

    var found = service.findCelebrity(team);

    assertThat(found).isEmpty();
  }

  @DisplayName("Should not find a Celebrity on")
  @ParameterizedTest(name = "{0} list.")
  @MethodSource("teamProvider")
  void notFindCelebrityOnList(List<Person> team) {
    var found = service.findCelebrity(team);

    assertThat(found).isEmpty();
  }

}
