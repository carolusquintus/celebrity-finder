package dev.carv.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Person {

  private String name;

  @ToString.Exclude
  private List<Person> people;

}
