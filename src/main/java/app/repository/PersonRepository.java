package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import app.model.entity.Person;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    Person findByName(String username);
}
