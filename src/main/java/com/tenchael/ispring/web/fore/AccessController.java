package com.tenchael.ispring.web.fore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tenchael.ispring.domain.User;
import com.tenchael.ispring.service.UserService;

@Controller
@RequestMapping
public class AccessController {
	@Autowired
	private UserService userService;

	@RequestMapping("/login")
	public String login(Model model,
			@RequestParam(required = false) String message) {
		model.addAttribute("message", message);
		return "access/login";
	}

	@RequestMapping("/register")
	public String register(Model model,
			@RequestParam(required = false) String message) {
		User user = new User();
		model.addAttribute("bean", user);
		model.addAttribute("message", message);
		return "access/register";
	}

	@RequestMapping("/do_register")
	public String save(User user, Model model) {
		userService.save(user);
		return "access/login";
	}

	@RequestMapping(value = "/denied")
	public String denied() {
		return "access/denied";
	}

	@RequestMapping(value = "/login/failure")
	public String loginFailure() {
		String message = "Login Failure!";
		return "redirect:/login?message=" + message;
	}

	@RequestMapping(value = "/logout/success")
	public String logoutSuccess() {
		String message = "Logout Success!";
		return "redirect:/login?message=" + message;
	}
}
