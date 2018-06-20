package com.org.andreorg.reportapp.export;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.org.andreorg.reportapp.model.Employee;

public class ExcelReportView extends AbstractXlsView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setHeader("Content-Disposition", "attachment;filename=\"employee.xls\"");
		@SuppressWarnings("unchecked")
		List<Employee> employeeList = (List<Employee>) model.get("employeeList");
		Sheet sheet = workbook.createSheet("Employee Data");
		Row header = sheet.createRow(0);
		header.createCell(0).setCellValue("Employee ID");
		header.createCell(1).setCellValue("Employee First Name");
		header.createCell(2).setCellValue("Employee Last Name");
		header.createCell(3).setCellValue("Employee Address");
		header.createCell(4).setCellValue("Employee Email");
		header.createCell(5).setCellValue("Employee Phone Number");
		header.createCell(6).setCellValue("Employee Department ID");
		header.createCell(7).setCellValue("Employee Salary");
		header.createCell(7).setCellValue("Employee Create TS");

		int rowNum = 1;
		for (Employee employee : employeeList) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(employee.getEmpID());
			row.createCell(1).setCellValue(employee.getFirstName());
			row.createCell(2).setCellValue(employee.getLastName());
			row.createCell(3).setCellValue(employee.getAddress());
			row.createCell(4).setCellValue(employee.getEmail());
			row.createCell(5).setCellValue(employee.getPhoneNumber());
			row.createCell(6).setCellValue(employee.getDepartmentID());
			row.createCell(7).setCellValue(employee.getSalary());
			row.createCell(8).setCellValue("");
		}
	}
}