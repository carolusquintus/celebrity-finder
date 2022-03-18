package dev.carv.service.impl;

import dev.carv.dto.Person;
import dev.carv.service.CelebrityService;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CelebrityServiceImpl implements CelebrityService {

  public Optional<Person> findCelebrity(List<Person> team) {
    if (team != null && team.size() > 1) {

      var celebrity = getPossibleCelebrity(team);

      if (celebrity.isPresent() && celebrityKnownByPeople(celebrity.get(), team) == team.size()) {
        return celebrity;
      }
    }
    return Optional.empty();
  }

  private Optional<Person> getPossibleCelebrity(List<Person> team) {
    int celebrities = 0;
    Optional<Person> celebrity = Optional.empty();

    for (var teamIterator = team.listIterator(); teamIterator.hasNext(); ) {
      var member = teamIterator.next();
      if (member.getPeople() == null || member.getPeople().isEmpty()) {
        celebrities++;
        celebrity = Optional.of(member);
        teamIterator.remove();
        log.debug("{} is a possible celebrity.", member);
      }
      log.debug("{} is a common person.", member);

      if (celebrities > 1) {
        return Optional.empty();
      }
    }
    return celebrity;
  }

  private long celebrityKnownByPeople(Person celebrity, List<Person> team) {
    long knownBy = 0;
    for (var person : team) {
      if (person.getPeople().contains(celebrity)) {
        knownBy++;
      }
    }
    return knownBy;
  }
}
