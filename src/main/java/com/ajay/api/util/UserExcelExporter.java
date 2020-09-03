package com.ajay.api.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ajay.api.model.User;

public class UserExcelExporter {

	private XSSFWorkbook workbook;
	private XSSFSheet sheet;

	private List<User> listUsers;

	public UserExcelExporter(List<User> listUsers) {
		this.listUsers = listUsers;

		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("Users");
	}

	private void writeHeaderRow() {
		Row row = sheet.createRow(1);
		Cell cell0 = row.createCell(0);
		cell0.setCellValue("User ID");

		Cell cell1 = row.createCell(1);
		cell1.setCellValue("User Name");

		Cell cell2 = row.createCell(2);
		cell2.setCellValue("User Role");
	}

	private void writeDataRow() {
		int sequence = 2;
		for (User user : listUsers) {
			Row row = sheet.createRow(sequence);
			Cell cell0 = row.createCell(0);
			cell0.setCellValue(user.getId());

			Cell cell1 = row.createCell(1);
			cell1.setCellValue(user.getName());

			Cell cell2 = row.createCell(2);
			cell2.setCellValue(String.valueOf(user.getRoles()));
			sequence++;
		}
		sequence = 2;
	}

	public void export(HttpServletResponse response) throws IOException {
		writeHeaderRow();
		writeDataRow();

		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);

		workbook.close();
		outputStream.close();
	}

}
