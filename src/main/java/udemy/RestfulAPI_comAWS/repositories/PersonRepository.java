package udemy.RestfulAPI_comAWS.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import udemy.RestfulAPI_comAWS.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {
}
