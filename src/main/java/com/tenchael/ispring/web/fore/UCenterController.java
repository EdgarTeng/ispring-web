package com.tenchael.ispring.web.fore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tenchael.ispring.common.ValidatorUtil;
import com.tenchael.ispring.service.MenuService;
import com.tenchael.ispring.service.UserService;

@Controller
@RequestMapping("/back")
public class UCenterController {

	private static Logger logger = LoggerFactory
			.getLogger(UCenterController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private MenuService menuService;

	@RequestMapping(value = { "/", "/index", "/main" })
	public String index(Model model, HttpServletRequest request,
			HttpServletResponse response) {

		menuService.reloadResourceForUser(request);

		String userIp = ValidatorUtil.getIpAddr(request);

		int totalUserCount = 0;

		model.addAttribute("userIp", userIp);
		model.addAttribute("totalUserCount", totalUserCount);

		return "ucenter/index";

	}

	@RequestMapping("/accessDenied")
	public String accessDenied() {

		return "ucenter/not_permission";
	}

	@RequestMapping("/logout")
	public String logout() {

		return "redirect:/j_spring_security_logout";
		// return "logout";
	}

}