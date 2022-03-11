package dev.carv.service;

import dev.carv.dto.Person;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

/**
 * Problem: Find the Celebrity.
 * In a team of N people, a celebrity is known by everyone but he/she doesn't know anybody.
 */
@Slf4j
public class CelebrityService {

  public Optional<Person> findCelebrity(List<Person> team) {

    var knownPeople = new ArrayList<Person>();
    var celebrities = new LinkedHashMap<Person, Integer>();

    for (var celeb : team) {
      if (celeb.getPeople() != null && !celeb.getPeople().isEmpty()) {
        knownPeople.addAll(celeb.getPeople());
        log.debug("{} is a common person.", celeb);
      } else {
        celebrities.put(celeb, 0);
        log.debug("{} is a possible celebrity.", celeb);
      }
    }

    celebritiesKnownByPeople(celebrities, knownPeople);

    return theCelebrity(celebrities, team.size() - 1);
  }

  private void celebritiesKnownByPeople(
      Map<Person, Integer> celebrities, List<Person> knownPeople) {
    for (var person : knownPeople) {
      if (celebrities.containsKey(person)) {
        int quantity = celebrities.get(person) + 1;
        celebrities.put(person, quantity);
      }
    }
  }

  private Optional<Person> theCelebrity(Map<Person, Integer> celebrities, int expected) {
    for (var celeb : celebrities.entrySet()) {
      if (celeb.getValue() == expected) {
        log.debug("{} is The Celebrity known by {}.", celeb.getKey(), celeb.getValue());
        return Optional.of(celeb.getKey());
      }
      log.debug("{} is just an influencer known by {}.", celeb.getKey(), celeb.getValue());
    }
    return Optional.empty();
  }

}
