package telran.b7a.person.service;

import java.time.LocalDate;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import telran.b7a.person.dao.PersonRepository;
import telran.b7a.person.dto.AddressDto;
import telran.b7a.person.dto.CityPopulationDto;
import telran.b7a.person.dto.EmployeeDto;
import telran.b7a.person.dto.PersonDto;
import telran.b7a.person.dto.exceptions.PersonNotFoundException;
import telran.b7a.person.model.Address;
import telran.b7a.person.model.Employee;
import telran.b7a.person.model.Person;

@Service
public class PersonServiceImpl implements PersonService {
	PersonRepository personRepository;
	ModelMapper modelMapper;

	@Autowired
	public PersonServiceImpl(PersonRepository personRepository, ModelMapper modelMapper) {
		this.personRepository = personRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	@Transactional
	public boolean addPerson(PersonDto personDto) {
		if(personRepository.existsById(personDto.getId())) {
			return false;
		}
		personRepository.save(modelMapper.map(personDto, Person.class));
		return true;
	}
	
	@Override
	@Transactional
	public boolean addEmployee(EmployeeDto employeeDto) {
		if(personRepository.existsById(employeeDto.getId())) {
			return false;
		}
		personRepository.save(modelMapper.map(employeeDto, Person.class));
		return true;
	}


	@Override
	public PersonDto findPersonById(Integer id) {
		Person person = personRepository.findById(id)
				.orElseThrow(() -> new PersonNotFoundException());
		return modelMapper.map(person, PersonDto.class);
	}
	
	@Override
	public EmployeeDto findEmployeeById(Integer id) {
		Employee employee = (Employee) personRepository.findById(id)
				.orElseThrow(() -> new PersonNotFoundException());
		return modelMapper.map(employee, EmployeeDto.class);
	}

	@Override
	@Transactional
	public PersonDto removePerson(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException());
		personRepository.deleteById(id);
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	@Transactional
	public PersonDto updatePersonName(Integer id, String name) {
		Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException());
		person.setName(name);
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	@Transactional
	public PersonDto updatePersonAddress(Integer id, AddressDto addressDto) {
		Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException());
		person.setAddress(modelMapper.map(addressDto, Address.class));
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<PersonDto> findPersonsByName(String name) {
		return personRepository.findByName(name)
						.map(p -> modelMapper.map(p, PersonDto.class))
						.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<PersonDto> findPersonsBetweenAges(Integer minAge, Integer maxAge) {
		LocalDate from = LocalDate.now().minusYears(maxAge);
		LocalDate to = LocalDate.now().minusYears(minAge);
		return personRepository.findByBirthDateBetween(from, to)
				.map(p -> modelMapper.map(p, PersonDto.class))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<PersonDto> findPersonsByCity(String city) {
		return personRepository.findByAddressCity(city)
				.map(p -> modelMapper.map(p, PersonDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public Iterable<CityPopulationDto> getCityPopulation() {
		return personRepository.getCityPopulation();
	}

}
