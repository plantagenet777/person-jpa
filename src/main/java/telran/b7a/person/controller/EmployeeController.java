package telran.b7a.person.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.b7a.person.dto.EmployeeDto;
import telran.b7a.person.dto.PersonDto;
import telran.b7a.person.service.PersonService;

@RestController
@RequestMapping("/pull/employee")
public class EmployeeController {

	PersonService personService;

	@Autowired
	public EmployeeController(PersonService personService) {
		this.personService = personService;
	}

	@PostMapping
	public boolean addEmployee(@RequestBody EmployeeDto personDto) {
		return personService.addEmployee(personDto);
	}
	
	@GetMapping("/{id}")
	public EmployeeDto findEmployee(@PathVariable Integer id) {
		return personService.findEmployeeById(id);
	}

}
