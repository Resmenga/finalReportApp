package com.org.andreorg.reportapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
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
	public ModelAndView login(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	@RequestMapping(value="admin/home", method = RequestMethod.GET)
	public ModelAndView home(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Employee employee = employeeService.findEmployeeByEmail(auth.getName());
		modelAndView.addObject("employeeName", "Welcome " + employee.getFirstName() + " " + employee.getLastName() + " (" + employee.getEmail() + ")");
		modelAndView.addObject("adminMessage","Content Available Only for Employees with Admin Role");
		modelAndView.setViewName("admin/home");
		return modelAndView;
	}
}