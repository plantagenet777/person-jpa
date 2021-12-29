package telran.b7a.person.service;

import telran.b7a.person.dto.AddressDto;
import telran.b7a.person.dto.CityPopulationDto;
import telran.b7a.person.dto.EmployeeDto;
import telran.b7a.person.dto.PersonDto;
import telran.b7a.person.model.Employee;

public interface PersonService {

	boolean addPerson(PersonDto personDto);
	boolean addEmployee(EmployeeDto employeeDto);

	PersonDto findPersonById(Integer id);
	EmployeeDto findEmployeeById(Integer id);

	PersonDto removePerson(Integer id);

	PersonDto updatePersonName(Integer id, String name);

	PersonDto updatePersonAddress(Integer id, AddressDto addressDto);
	
	Iterable<PersonDto> findPersonsByName(String name);
	
	Iterable<PersonDto> findPersonsBetweenAges(Integer minAge, Integer maxAge);
	
	Iterable<PersonDto> findPersonsByCity(String city);
	
	Iterable<CityPopulationDto> getCityPopulation();
	
	

}
