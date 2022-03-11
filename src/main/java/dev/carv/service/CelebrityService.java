package dev.carv.service;

import dev.carv.dto.Person;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

/**
 * Problem: Find the Celebrity.
 * In a team of N people, a celebrity is known by everyone but he/she doesn't know anybody.
 */
@Slf4j
public class CelebrityService {

  public Optional<Person> findCelebrity(List<Person> team) {
    for (var celeb : team) {
      if (celeb.getKnown() == null && isKnownByAllTeam(celeb, team)) {
        return Optional.of(celeb);
      }
      log.debug("{} is a common person.", celeb);
    }
    return Optional.empty();
  }

  private boolean isKnownByAllTeam(Person celeb, List<Person> team) {
    int knownBy = 0;
    for (var member : team) {
      if (member.getKnown() != null
          && celeb.getName().equalsIgnoreCase(member.getKnown().getName())) {
        log.debug("{} is known by {}.", celeb, member);
        knownBy++;
      }
    }
    log.debug("{} is known by {}.", celeb, knownBy);
    return knownBy == (team.size() - 1);
  }

}
