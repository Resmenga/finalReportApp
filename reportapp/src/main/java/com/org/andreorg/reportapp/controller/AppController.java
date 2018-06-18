package com.org.andreorg.reportapp.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.org.andreorg.reportapp.model.Employee;
import com.org.andreorg.reportapp.model.PagerModel;
import com.org.andreorg.reportapp.repository.EmployeeRepository;
import com.org.andreorg.reportapp.service.EmployeeService;


@Controller
public class AppController {
	
	private static final int BUTTONS_TO_SHOW = 3;
	private static final int INITIAL_PAGE = 0;
	private static final int INITIAL_PAGE_SIZE = 5;
	private static final int[] PAGE_SIZES = { 5, 10};
	
	@Autowired
	private EmployeeService employeeService;
	

	@Autowired
	private EmployeeRepository employeeRepository;

	@RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView getLoginPage(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/home", method = RequestMethod.GET)
	public ModelAndView getHomePage(@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page){
		// page size
		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		// Evaluate page. If requested parameter is null or less than 0 (to
		// prevent exception), return initial size. Otherwise, return value of
		// param. decreased by 1.
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
		
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Employee employee = employeeService.findEmployeeByEmail(auth.getName());
		modelAndView.addObject("employeeName", "Welcome " + employee.getFirstName() + " " + employee.getLastName() + " (" + employee.getEmail() + ")");
		modelAndView.addObject("adminMessage","Content Available Only for Employees with Admin Role");
		modelAndView.setViewName("admin/home");
		
		Page<Employee> employees = employeeRepository.findAll(PageRequest.of(evalPage, evalPageSize)); //TODO: call service instead?
		
		PagerModel pager = new PagerModel(employees.getTotalPages(),employees.getNumber(),BUTTONS_TO_SHOW);
		modelAndView.addObject("employees",employees);
		modelAndView.addObject("selectedPageSize", evalPageSize);
		modelAndView.addObject("pageSizes", PAGE_SIZES);
		modelAndView.addObject("pager", pager);
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/deleteEmployee", method = RequestMethod.POST)
	public ModelAndView deleteEmployee(@RequestParam("empID") Long id){
		employeeRepository.deleteById(id); //TODO: call service instead?
		return new ModelAndView("redirect:/admin/home?pageSize="+INITIAL_PAGE_SIZE+"&page="+INITIAL_PAGE);
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
			modelAndView.setViewName("admin/addEmployee");
		} else {
			employeeService.saveEmployee(employee);;
			modelAndView.addObject("successMessage", "Employee has been added successfully");
			modelAndView.addObject("employee", new Employee());
			modelAndView.setViewName("admin/addEmployee");
			
		}
		return modelAndView;
	}
}