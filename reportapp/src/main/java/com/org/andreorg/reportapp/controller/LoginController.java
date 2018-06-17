package com.org.andreorg.reportapp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.org.andreorg.reportapp.model.Employee;
import com.org.andreorg.reportapp.service.EmployeeService;


@Controller
public class LoginController {
	
	@Autowired
	private EmployeeService employeeService;

	@RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView getLoginPage(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	@RequestMapping(value="admin/home", method = RequestMethod.GET)
	public ModelAndView getHomePage(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Employee employee = employeeService.findEmployeeByEmail(auth.getName());
		modelAndView.addObject("employeeName", "Welcome " + employee.getFirstName() + " " + employee.getLastName() + " (" + employee.getEmail() + ")");
		modelAndView.addObject("adminMessage","Content Available Only for Employees with Admin Role");
		modelAndView.setViewName("admin/home");
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/addEmployee", method = RequestMethod.GET)
	public ModelAndView getCreateNewEmployeePage(){
		ModelAndView modelAndView = new ModelAndView();
		Employee employee = new Employee();
		modelAndView.addObject("employee", employee);
		modelAndView.setViewName("admin/addEmployee");
		return modelAndView;
	}
	
	@RequestMapping(value = "/admin/addEmployee", method = RequestMethod.POST)
	public ModelAndView createNewEmployee(@Valid Employee employee, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		Employee employeeExists = employeeService.findEmployeeByEmail(employee.getEmail());
		if (employeeExists != null) {
			bindingResult
					.rejectValue("email", "error.employee",
							"There is already a employee registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {
			employeeService.saveEmployee(employee);;
			modelAndView.addObject("successMessage", "Employee has been added successfully");
			modelAndView.addObject("employee", new Employee());
			modelAndView.setViewName("admin/addEmployee");
			
		}
		return modelAndView;
	}
}