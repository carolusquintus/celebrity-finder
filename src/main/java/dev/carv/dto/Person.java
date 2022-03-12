package dev.carv.dto;

import static lombok.AccessLevel.NONE;

import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;

@Data
@Setter(NONE)
@AllArgsConstructor
public class Person {

  private String firstName;
  private String lastName;

  @ToString.Exclude
  private Collection<Person> people;

}
