package com.org.andreorg.reportapp.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.org.andreorg.reportapp.export.ExcelReportView;
import com.org.andreorg.reportapp.model.Employee;
import com.org.andreorg.reportapp.model.PagerModel;
import com.org.andreorg.reportapp.service.EmployeeService;

@Controller
public class AppController {

	private static final int BUTTONS_TO_SHOW = 3;
	private static final int INITIAL_PAGE = 0;
	private static final int INITIAL_PAGE_SIZE = 5;
	private static final int[] PAGE_SIZES = { 5, 10 };

	@Autowired
	private EmployeeService employeeService;

	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
	public ModelAndView getLoginPage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}

	@RequestMapping(value = "/admin/home", method = RequestMethod.GET)
	public ModelAndView getHomePage(@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page,
			@RequestParam(value = "searchOption", defaultValue = "empty", required = false) String searchOption,
			@RequestParam(value = "searchOptionValue", defaultValue = "empty", required = false) String searchOptionValue) {
		// page size
		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		// Evaluate page. If requested parameter is null or less than 0 (to
		// prevent exception), return initial size. Otherwise, return value of
		// param. decreased by 1.
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Employee employee = employeeService.findEmployeeByEmail(auth.getName());
		modelAndView.addObject("employeeName",
				"Welcome " + employee.getFirstName() + " " + employee.getLastName() + " (" + employee.getEmail() + ")");
		modelAndView.addObject("adminMessage", "Content Available Only for Employees with Admin Role");
		modelAndView.setViewName("admin/home");

		// TODO: call service instead?
		// TODO: Refactor this part and deal with nested ifs
		Page<Employee> employees = employeeService.findAllEmployees(PageRequest.of(evalPage, evalPageSize));
		if (!searchOption.equals("empty") && !searchOptionValue.equals("empty")) {
			Pageable pageable = PageRequest.of(evalPage, evalPageSize);
			if (searchOption.equalsIgnoreCase("fName")) {
				employees = employeeService.findByFirstName(pageable, searchOptionValue);
			} else if (searchOption.equalsIgnoreCase("lName")) {
				employees = employeeService.findByLastName(pageable, searchOptionValue);
			} else if (searchOption.equalsIgnoreCase("email")) {
				employees = employeeService.findByEmail(pageable, searchOptionValue);
			} else if (searchOption.equalsIgnoreCase("deptID")) {
					employees = employeeService.findByDepartmentID(pageable, Integer.parseInt(searchOptionValue));
			} else if (searchOption.equalsIgnoreCase("empID")) {
					employees = employeeService.findByEmpID(pageable, Long.parseLong(searchOptionValue));
			} else if (searchOption.equalsIgnoreCase("salary")) {
					employees = employeeService.findBySalary(pageable, Integer.parseInt(searchOptionValue));
			}
		} else {
			employees = employeeService.findAll(PageRequest.of(evalPage, evalPageSize));
		}

		PagerModel pager = new PagerModel(employees.getTotalPages(), employees.getNumber(), BUTTONS_TO_SHOW);
		modelAndView.addObject("employees", employees);
		modelAndView.addObject("selectedPageSize", evalPageSize);
		modelAndView.addObject("pageSizes", PAGE_SIZES);
		modelAndView.addObject("pager", pager);
		return modelAndView;
	}

	@RequestMapping(value = "/admin/deleteEmployee", method = RequestMethod.POST)
	public ModelAndView deleteEmployee(@RequestParam("empID") Long id) {
		employeeService.deleteById(id); // TODO: call service instead?
		return new ModelAndView("redirect:/admin/home?pageSize=" + INITIAL_PAGE_SIZE + "&page=" + INITIAL_PAGE);
	}

	@RequestMapping(value = "/admin/addEmployee", method = RequestMethod.GET)
	public ModelAndView getCreateNewEmployeePage() {
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
			bindingResult.rejectValue("email", "error.employee",
					"There is already a employee registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("admin/addEmployee");
		} else {
			employeeService.saveEmployee(employee);
			modelAndView.addObject("successMessage", "Employee has been added successfully");
			modelAndView.addObject("employee", new Employee());
			modelAndView.setViewName("admin/addEmployee");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/admin/updateEmployee/{empID}", method = RequestMethod.GET)
	public ModelAndView getUpdateEmployeePage(@PathVariable("empID") Long id, 
			@RequestParam(value = "successMessage", defaultValue = "false", required = false) String successMessage) {
		ModelAndView modelAndView = new ModelAndView();
		Optional<Employee> employee = employeeService.findById(id);
		modelAndView.addObject("employee", employee);
		modelAndView.setViewName("admin/updateEmployee");
		if(successMessage.equalsIgnoreCase("true")) {
			modelAndView.addObject("successMessage", "Employee has been updated successfully");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/admin/updateEmployee", method = RequestMethod.POST)
	public ModelAndView updateEmployee(@Valid Employee employee, BindingResult bindingResult) {
		employeeService.updateEmployee(employee);
		return new ModelAndView("redirect:/admin/updateEmployee/" + employee.getEmpID()+"?successMessage=true");
	}

	@RequestMapping(value = "/admin/report", method = RequestMethod.GET)
	public ModelAndView getExcel() {
		List<Employee> employeeList = (List<Employee>) employeeService.findAll();
		return new ModelAndView(new ExcelReportView(), "employeeList", employeeList);
	}
}