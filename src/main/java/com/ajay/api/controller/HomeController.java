package com.ajay.api.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.ajay.api.model.User;
import com.ajay.api.service.UserService;
import com.ajay.api.util.UserExcelExporter;
import com.ajay.api.util.UserPdfExporter;
import com.lowagie.text.DocumentException;

@Controller
@RequestMapping("/api")
public class HomeController {

	@Autowired
	private UserService userService;

	@RequestMapping("/home")
	public String homePage(Model m) {
		m.addAttribute("msg", "Welcome From Home Controller");
		return "Home";
	}

	@RequestMapping("/viewAll")
	public String viewAllPage(Model m) {
		System.out.println("HomeController.viewAllPage()");
		m.addAttribute("msg", "Welcome From View All Page");
		List<User> users = userService.getAllUsers();
		m.addAttribute("users", users);
		return "ViewAll";
	}

	@RequestMapping("/regPage")
	public String regPage(Model m) {
		m.addAttribute("msg", "Welcome From Register Page");
		m.addAttribute("user", new User());
		return "Register";
	}

	@RequestMapping("/save")
	public RedirectView addUser(@ModelAttribute User user, Model model,RedirectAttributes ra) {   //returnType:RedirectView ,param:RedirectAttributes ra
		System.out.println("HomeController.addUser()");
		String msg = userService.addUser(user);

		ra.addAttribute("msg", msg);
		RedirectView redirectView=new RedirectView("/api/viewAll");
		return redirectView;
		
//		model.addAttribute("msg", "Welcome From Save -> View All Page");
//		return "redirect:/api/viewAll";
	}

	/**
	 * For Excel Export
	 * 
	 * @param response
	 * @throws IOException
	 */
	@GetMapping("/user/excelexport")
	public void exportToExcel(HttpServletResponse response) throws IOException {

		response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE); // Content-type="application/octet-stream"

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=Users.xlsx";
		response.setHeader(headerKey, headerValue);

		// Get All Users From Database
		List<User> listUsers = userService.getAllUsers();

		UserExcelExporter excelExporter = new UserExcelExporter(listUsers);
		excelExporter.export(response);
	}

	/**
	 * For PDF Export uri: /api/user/pdfexport
	 * 
	 * @throws IOException
	 * @throws DocumentException
	 */
	@GetMapping("/user/pdfexport")
	public void exportToPdf(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType(MediaType.APPLICATION_PDF_VALUE); // Content-type=application/pdf

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDate = dateFormat.format(new Date(System.currentTimeMillis()));

		// Use For Download the pdf file automatically
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=users_" + currentDate + ".pdf";
		response.setHeader(headerKey, headerValue);

		List<User> listUsers = userService.getAllUsers();

		// For PDF Export
		UserPdfExporter pdfExporter = new UserPdfExporter(listUsers);

		pdfExporter.export(response);
	}

}
