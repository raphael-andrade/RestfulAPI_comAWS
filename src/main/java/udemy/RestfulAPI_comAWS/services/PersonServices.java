package udemy.RestfulAPI_comAWS.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import udemy.RestfulAPI_comAWS.Exceptions.ResourceNotFoundException;
import udemy.RestfulAPI_comAWS.data.vo.v1.PersonVO;
import udemy.RestfulAPI_comAWS.data.vo.v2.PersonVOV2;
import udemy.RestfulAPI_comAWS.mapper.DozerMapper;
import udemy.RestfulAPI_comAWS.mapper.custom.PersonMapper;
import udemy.RestfulAPI_comAWS.model.Person;
import udemy.RestfulAPI_comAWS.repositories.PersonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices {

    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;

    @Autowired
    PersonMapper mapper;

    public List<PersonVO> findAll() {
        logger.info("Finding all people!");
        return DozerMapper.parseListObjects(repository.findAll(),PersonVO.class) ;
    }

    public PersonVO findById(Long id) {

        logger.info("Finding one person!");

        var entity = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No records found for this ID"));
        return DozerMapper.parseObject(entity, PersonVO.class);
    }

    public PersonVO create(PersonVO person) {
        logger.info("Creating one person!");
        var entity = DozerMapper.parseObject(person, Person.class);
        var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
        return vo;

    }

    public PersonVO update(PersonVO person) {
        logger.info("Updating one person!");
        var entity = repository.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
        return vo;
    }

    public void delete(Long id) {
        logger.info("Deleting one person!");
        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        repository.delete(entity);
    }

    public PersonVOV2 createV2(PersonVOV2 person) {
        logger.info("Creating one person with V2!");
        var entity = mapper.convertVoToEntity(person);
        var vo = mapper.convertEntityToVo(repository.save(entity));
        return vo;

    }
}
