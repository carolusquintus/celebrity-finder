package dev.carv.service.impl;

import dev.carv.dto.Person;
import dev.carv.service.CelebrityService;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CelebrityServiceLambdasImpl implements CelebrityService {

  public Optional<Person> findCelebrity(List<Person> team) {
    if (team != null && team.size() > 1) {
      var celebrities = theCelebrities(team);

      if (celebrities.size() == 1) {
        var knownPeople = knownPeople(team);
        var knownPeopleQuantity = knownPeopleQuantity(knownPeople);
        return theCelebrity(celebrities.get(0), knownPeopleQuantity, team.size() - 1);
      }
    }
    return Optional.empty();
  }

  private List<Person> theCelebrities(List<Person> team) {
    return team.parallelStream()
        .filter(member -> member.getPeople() == null || member.getPeople().isEmpty())
        .collect(Collectors.toList());
  }

  private List<Person> knownPeople(List<Person> team) {
    return team.parallelStream()
        .filter(member -> member.getPeople() != null && !member.getPeople().isEmpty())
        .flatMap(member -> member.getPeople().stream())
        .collect(Collectors.toList());
  }

  private Map<Person, Long> knownPeopleQuantity(List<Person> knownPeople) {
    return knownPeople.parallelStream()
        .collect(Collectors.groupingByConcurrent(Function.identity(), Collectors.counting()));
  }

  private Optional<Person> theCelebrity(
      Person celebrity, Map<Person, Long> knownPeopleQuantity, int expected) {

    if (knownPeopleQuantity.containsKey(celebrity)
        && knownPeopleQuantity.get(celebrity) == expected) {
      log.debug("{} is The Celebrity known by {}.", celebrity, expected);
      return Optional.of(celebrity);
    }

    return Optional.empty();
  }

}
