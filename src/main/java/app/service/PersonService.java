package app.service;

import app.model.dto.CreateFolderDto;
import app.model.dto.CreatePersonDto;
import app.model.dto.PersonDto;
import app.model.entity.Person;
import app.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    private PersonRepository personRepository;
    private FolderService folderService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public PersonService(PersonRepository personRepository, FolderService folderService, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;

        this.folderService = folderService;
        this.passwordEncoder = passwordEncoder;
    }
//Вот тут тоже есть вопросы в folders , ожидает List Folders
    public void save(CreatePersonDto createDto) {
        Person person = getPerson(createDto);
        personRepository.save(person);
        Integer id = personRepository.findAll().size();
        Person findPerson =personRepository.getReferenceById(id);
        folderService.save( CreateFolderDto.builder()
                .name("root")
                .path("root")
                .person(PersonDto.builder()
                        .id(findPerson.getId())
                        .email(findPerson.getEmail())
                        .password(findPerson.getPassword())
                        .folders(findPerson.getFolders())
                        .notes(findPerson.getNotes())
                        .build()
                )
                .build()
        );
    }
    private Person getPerson(CreatePersonDto createDto) {
        return Person.builder()
                .name(createDto.getName())
                .email(createDto.getEmail())
                .password(passwordEncoder.encode(createDto.getPassword()))
                .build();
    }
    public Person getPersonById(Integer id) {
        return personRepository.getReferenceById(id);
    }
    public List<Person> getAll(){
        return personRepository.findAll();
    }

    public CreatePersonDto getPersonDto(Integer id) {
        Person person = getPersonById(id);
        return CreatePersonDto.builder()
                .name(person.getName())
                .email(person.getEmail())
                .password(passwordEncoder.encode(person.getPassword()))
                .build();
    }
    public void editPerson (CreatePersonDto createDto, Integer id){
        Person person = getPersonById(id);
        person.setName(createDto.getName());
        person.setEmail(createDto.getEmail());
        person.setPassword(passwordEncoder.encode(createDto.getPassword()));
        personRepository.save(person);
    }
    public void deletePerson (Integer id){
        Person person = getPersonById(id);
        personRepository.delete(person);
    }

}
