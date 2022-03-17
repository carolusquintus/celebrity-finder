package dev.carv.service;

import dev.carv.dto.Person;
import java.util.List;
import java.util.Optional;

/**
 * Problem: Find the Celebrity.
 * In a team of N people, a celebrity is known by everyone but he/she doesn't know anybody.
 */
public interface CelebrityService {

  Optional<Person> findCelebrity(List<Person> team);

}
