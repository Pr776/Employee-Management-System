package com.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.spring.entity.Employee;
import com.spring.service.EmployeeService;

@Controller
public class EmployeeController {
	
	@Autowired  
	private final EmployeeService employeeService;

	    public EmployeeController(EmployeeService employeeService) {
	        this.employeeService = employeeService;
	    }

		@GetMapping("/employee")
	    public String listEmployees(Model model) {
	        List<Employee> employees = employeeService.getAllEmployees();
	        model.addAttribute("employees", employees);
	        return "Employees";
	    }
		
		@GetMapping("/employeeCreate")
		public String createEmployeeForm(Model model) {
			Employee employee = new Employee();
			model.addAttribute("employee", employee);
			return "createemployee";
			
		}
		
		@PostMapping("/employees")
		public String saveEmployee(@ModelAttribute("employee") Employee employee) {
			employeeService.saveEmployee(employee);
			return "redirect:/employees";
		}
		
		@GetMapping("/employees/edit/{id}")
		public String editEmployeeForm(@PathVariable Long id, Model model) {
			model.addAttribute("employee", employeeService.getEmployeeById(id));
			return "edit_employee";
		}

		@PostMapping("/employees/{id}")
		public String updateEmployee(@PathVariable Long id,
				@ModelAttribute("employee") Employee employee,
				Model model) {
			
			
			Employee existingEmployee = employeeService.getEmployeeById(id);
			existingEmployee.setId(id);
			existingEmployee.setFirstName(employee.getFirstName());
			existingEmployee.setLastName(employee.getLastName());
			existingEmployee.setEmail(employee.getEmail());
			
			
			employeeService.updateEmployee(existingEmployee);
			return "redirect:/employees";		
		}
		
		
		@GetMapping("/employees/{id}")
		public String deleteEmployee(@PathVariable Long id) {
			employeeService.deleteEmployeeById(id);
			return "redirect:/employees";
		}

}
