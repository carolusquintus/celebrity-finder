package dev.carv.util.provider;

import com.github.javafaker.Faker;
import dev.carv.dto.Person;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Stream;

public interface CommonProvider {

  Faker faker = new Faker(new Locale("es"));

  default Person personProvider(Set<Person> people) {
    return new Person(faker.name().firstName(), faker.name().lastName(), people);
  }

  default Stream<List<Person>> teamProvider() {
    return Stream.of(
        null,
        List.of(),
        List.of(personProvider(null))
    );
  }

}
