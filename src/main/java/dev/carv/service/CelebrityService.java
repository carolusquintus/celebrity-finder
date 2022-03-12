package dev.carv.service;

import dev.carv.dto.Person;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

/**
 * Problem: Find the Celebrity.
 * In a team of N people, a celebrity is known by everyone but he/she doesn't know anybody.
 */
@Slf4j
public class CelebrityService {

  public Optional<Person> findCelebrity(List<Person> team) {
    if (team != null && !team.isEmpty()) {

      int celebrities = 0;
      Optional<Person> celebrity = Optional.empty();

      for (var member : team) {
        if (member.getPeople() == null || member.getPeople().isEmpty()) {
          celebrities++;
          celebrity = Optional.of(member);
          log.debug("{} is a possible celebrity.", member);
        }
        log.debug("{} is a common person.", member);

        if (celebrities > 1) {
          return Optional.empty();
        }
      }

      if (celebrity.isPresent()) {
        var knownPeople = knownPeopleFlatted(team);
        var knownPeopleQuantityMap = knownPeopleQuantityMap(knownPeople);
        return theCelebrity(celebrity.get(), knownPeopleQuantityMap, team.size() - 1);
      }
    }
    return Optional.empty();
  }

  private List<Person> knownPeopleFlatted(List<Person> team) {
    return team.parallelStream()
        .filter(member -> member.getPeople() != null && !member.getPeople().isEmpty())
        .flatMap(member -> member.getPeople().stream())
        .sorted(Comparator.comparing(Person::getLastName))
        .collect(Collectors.toList());
  }

  private Map<Person, Long> knownPeopleQuantityMap(List<Person> knownPeople) {
    return knownPeople.parallelStream()
        .collect(Collectors.groupingByConcurrent(Function.identity(), Collectors.counting()));
  }

  private Optional<Person> theCelebrity(
      Person celebrity, Map<Person, Long> knownPeopleQuantityMap, int expected) {

    if (knownPeopleQuantityMap.containsKey(celebrity)
        && knownPeopleQuantityMap.get(celebrity) == expected) {
      log.debug("{} is The Celebrity known by {}.", celebrity, expected);
      return Optional.of(celebrity);
    }

    return Optional.empty();
  }
}
