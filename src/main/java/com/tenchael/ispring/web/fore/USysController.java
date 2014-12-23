package com.tenchael.ispring.web.fore;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tenchael.ispring.common.PageNavUtil;
import com.tenchael.ispring.domain.Menu;
import com.tenchael.ispring.domain.Role;
import com.tenchael.ispring.domain.User;
import com.tenchael.ispring.model.UserInfo;
import com.tenchael.ispring.security.WebUserDetails;
import com.tenchael.ispring.service.MenuService;
import com.tenchael.ispring.service.RoleService;
import com.tenchael.ispring.service.UserService;

@Controller
@RequestMapping("/back")
public class USysController extends UBaseController {

	private static Logger logger = LoggerFactory
			.getLogger(USysController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private MenuService menuService;

	@RequestMapping("/users")
	public String userList(Model model, Integer page, String userName) {

		page = page == null ? 1 : page < 1 ? 1 : page;

		int pageSize = 10;

		Pageable pageable = new PageRequest(page, pageSize, Direction.DESC,
				"id");
		Page<User> pageData = this.userService.searchByUserName(userName,
				pageable);

		model.addAttribute("dataList", pageData.getContent());
		model.addAttribute("totalCount", pageData.getTotalElements());
		model.addAttribute("totalPage", pageData.getTotalPages());
		model.addAttribute("currentPage", page);
		model.addAttribute("pageNav", PageNavUtil.getPageNavHtml(
				page.intValue(), pageSize, (int) pageData.getTotalElements(),
				15));

		return "ucenter/sys/userList";
	}

	@RequestMapping("/userAdd")
	public String userAdd(Model model, User userEntity) {
		short status = 1;
		userEntity.setStatus(status);
		model.addAttribute("userEntity", userEntity);
		return "ucenter/sys/userEdit";
	}

	@RequestMapping("/userEdit")
	public String userEdit(Model model, long id) {

		User userEntity = this.userService.getById(id);
		model.addAttribute("userEntity", userEntity);
		return "ucenter/sys/userEdit";
	}

	@RequestMapping("/changePwd")
	public String changePwd(Model model) {
		WebUserDetails userInfo = this.getUserInfo();
		long userId = userInfo.getUserId();

		User userEntity = this.userService.getById(userId);
		model.addAttribute("userEntity", userEntity);
		return "ucenter/sys/pwdEdit";
	}

	@RequestMapping("/roles")
	public String roleList(Model model, Integer page, String roleName) {

		page = page == null ? 1 : page < 1 ? 1 : page;
		int pageSize = 10;
		Pageable pageable = new PageRequest(page, pageSize, Direction.ASC, "id");
		Page<Role> pageData = this.roleService.findByRoleName(roleName,
				pageable);

		model.addAttribute("dataList", pageData.getContent());
		model.addAttribute("totalCount", pageData.getTotalElements());
		model.addAttribute("totalPage", pageData.getTotalPages());
		model.addAttribute("currentPage", page);
		model.addAttribute(
				"pageNav",
				PageNavUtil.getPageNavHtml(page.intValue(), 10,
						(int) pageData.getTotalElements(), 15));

		return "ucenter/sys/roleList";
	}

	@RequestMapping("/roleAdd")
	public String roleAdd(Model model, Role roleEntity) {
		short status = 1;
		roleEntity.setStatus(status);
		model.addAttribute("roleEntity", roleEntity);
		return "ucenter/sys/roleEdit";
	}

	@RequestMapping("/roleEdit")
	public String roleEdit(Model model, short id) {

		Role roleEntity = this.roleService.getById(id);
		model.addAttribute("roleEntity", roleEntity);
		return "ucenter/sys/roleEdit";
	}

	@RequestMapping("/menus")
	public String menuList(Model model, Integer page, String menuName) {

		page = page == null ? 1 : page < 1 ? 1 : page;
		int pageSize = 10;
		Pageable pageable = new PageRequest(page, pageSize, Direction.DESC,
				"id");
		Page<Menu> pageData = this.menuService.findByMenuName(menuName,
				pageable);

		model.addAttribute("dataList", pageData.getContent());
		model.addAttribute("totalCount", pageData.getTotalElements());
		model.addAttribute("totalPage", pageData.getTotalPages());
		model.addAttribute("currentPage", page);
		model.addAttribute(
				"pageNav",
				PageNavUtil.getPageNavHtml(page.intValue(), 10,
						(int) pageData.getTotalElements(), 15));

		return "ucenter/sys/menuList";
	}

	@RequestMapping("/menuAdd")
	public String menuAdd(Model model, Menu menuEntity) {
		menuEntity.setNavMenu(1);

		List<Menu> parentMenus = this.menuService.getChildMenus(0);
		Menu rootMenu = new Menu();
		rootMenu.setId(0);
		rootMenu.setMenuName("--顶级菜单--");
		parentMenus.add(0, rootMenu);

		model.addAttribute("menuEntity", menuEntity);
		model.addAttribute("parentMenus", parentMenus);

		return "ucenter/sys/menuEdit";
	}

	@RequestMapping("/menuEdit")
	public String menuEdit(Model model, int id) {

		Menu menuEntity = this.menuService.getById(id);

		List<Menu> parentMenus = this.menuService.getChildMenus(0);
		Menu rootMenu = new Menu();
		rootMenu.setId(0);
		rootMenu.setMenuName("--顶级菜单--");
		parentMenus.add(0, rootMenu);

		model.addAttribute("menuEntity", menuEntity);
		model.addAttribute("parentMenus", parentMenus);

		return "ucenter/sys/menuEdit";
	}

	@RequestMapping("/userRoleSet")
	public String userRoleSet(Model model, long userId) {
		User userEntity = this.userService.getById(userId);
		List<Role> allRoles = this.roleService.getAvailableRoles();

		UserInfo userInfo = new UserInfo();
		userInfo.setId(userEntity.getId());
		userInfo.setUserName(userEntity.getUserName());
		for (Role roleEntity : userEntity.getRoles()) {
			userInfo.getRoles().add(roleEntity.getId());
		}

		model.addAttribute("allRoles", allRoles);
		model.addAttribute("userInfo", userInfo);

		return "ucenter/sys/userRoleSet";
	}

	@RequestMapping("/roleResourceSet")
	public String roleResourceSet(Model model, short roleId) {

		Role roleEntity = this.roleService.getById(roleId);

		Set<Menu> menus = roleEntity.getMenus();
		List<Integer> menuIds = new ArrayList<Integer>();
		for (Menu menu : menus) {
			menuIds.add(menu.getId());
		}

		model.addAttribute("roleEntity", roleEntity);
		model.addAttribute("menuIds", StringUtils.join(menuIds, ","));

		return "ucenter/sys/roleResourceSet";
	}

}