package com.ajay.api.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.ajay.api.model.User;
import com.ajay.api.service.UserService;
import com.ajay.api.util.UserExcelExporter;

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
	public String addUser(@ModelAttribute User user, Model m) {
		String msg = userService.addUser(user);
		m.addAttribute("msg", msg);

//		List<User> users = userService.getAllUsers();
//		m.addAttribute("users", users);
		return "redirect:viewAll";

	}

	@GetMapping("/user/excelexport")
	public void exportToExcel(HttpServletResponse response) throws IOException {
		
		response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE); 			// Content-type="application/octet-stream"
		
		String headerKey="Content-Disposition";
		String headerValue="attachment; filename=Users.xlsx";
		response.setHeader(headerKey, headerValue);
		
		// Get All Users From Database
		List<User> listUsers=userService.getAllUsers();
		
		UserExcelExporter excelExporter=new UserExcelExporter(listUsers);
		excelExporter.export(response);
	}
}
