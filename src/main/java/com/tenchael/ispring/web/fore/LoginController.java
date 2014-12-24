package com.tenchael.ispring.web.fore;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.captcha.Captcha;
import nl.captcha.backgrounds.TransparentBackgroundProducer;
import nl.captcha.gimpy.DropShadowGimpyRenderer;
import nl.captcha.servlet.CaptchaServletUtil;
import nl.captcha.text.renderer.DefaultWordRenderer;
import nl.captcha.text.renderer.WordRenderer;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tenchael.ispring.common.ValidatorUtil;
import com.tenchael.ispring.domain.Role;
import com.tenchael.ispring.domain.User;
import com.tenchael.ispring.model.UserInfo;
import com.tenchael.ispring.service.RoleService;
import com.tenchael.ispring.service.UserService;

@Controller
public class LoginController {

	private static Logger logger = LoggerFactory
			.getLogger(LoginController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;

	@RequestMapping(value = { "/login", "/ulogin" })
	public String login() {
		return "login";

	}

	@RequestMapping(value = { "/register" })
	public String register(Model model) {
		User user = new User();
		model.addAttribute("bean", user);
		return "register";

	}

	@RequestMapping(value = "/do_register", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> do_register(Model model, User userEntity) {
		boolean resultStatus = true;
		Map<String, Object> resMap = new HashMap<String, Object>();

		String userName = userEntity.getUserName();
		if (userEntity == null || StringUtils.isBlank(userName)) {
			resultStatus = false;
			resMap.put("errorMsg", "用户名不能为空");
			resMap.put("resultStatus", resultStatus);
			return resMap;
		}

		// 过滤非法字符
		userName = ValidatorUtil.filterUnSafeChar(userName).trim();
		userEntity.setUserName(userName);

		try {
			User newUser = this.userService.save(userEntity);

			// 设置用户角色
			UserInfo userInfo = new UserInfo();
			userInfo.setId(newUser.getId());
			List<Short> roles = new ArrayList<Short>();
			Short roleId = 2;// 2为普通用户
			roles.add(roleId);
			userInfo.setRoles(roles);
			userService.saveUserRole(userInfo);

		} catch (Exception e) {
			resultStatus = false;
		}

		resMap.put("resultStatus", resultStatus);
		return resMap;

	}

	@RequestMapping(value = { "/authorizeFailed" })
	public String accessDenied() {

		return "authorize_failed";

	}

	@RequestMapping("/getVerifyCode")
	public void getVerifyMCode(Model model, HttpServletRequest request,
			HttpServletResponse response) {

		List<Color> colors = new ArrayList<Color>();
		colors.add(Color.GREEN);
		colors.add(Color.BLUE);
		colors.add(Color.ORANGE);
		colors.add(Color.RED);

		List<Font> fonts = new ArrayList<Font>();
		fonts.add(new Font("Geneva", 2, 32));
		fonts.add(new Font("Courier", 3, 32));
		fonts.add(new Font("Arial", 1, 32));

		// WordRenderer wordRenderer = new ColoredEdgesWordRenderer(colors,
		// fonts);
		WordRenderer wordRenderer = new DefaultWordRenderer(colors, fonts);

		Captcha captcha = new Captcha.Builder(150, 50).addText(wordRenderer)
				.gimp(new DropShadowGimpyRenderer())
				.addBackground(new TransparentBackgroundProducer()).build();

		request.getSession().setAttribute("verifyCode", captcha.getAnswer());

		CaptchaServletUtil.writeImage(response, captcha.getImage());

	}

}
