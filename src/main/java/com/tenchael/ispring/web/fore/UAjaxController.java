package com.tenchael.ispring.web.fore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tenchael.ispring.common.PageNavUtil;
import com.tenchael.ispring.common.ValidatorUtil;
import com.tenchael.ispring.domain.Menu;
import com.tenchael.ispring.domain.Role;
import com.tenchael.ispring.domain.User;
import com.tenchael.ispring.model.TreeData;
import com.tenchael.ispring.model.UserInfo;
import com.tenchael.ispring.security.WebSecurityMetadataSource;
import com.tenchael.ispring.security.WebUserDetails;
import com.tenchael.ispring.service.MenuService;
import com.tenchael.ispring.service.RoleService;
import com.tenchael.ispring.service.UserService;

@Controller
@RequestMapping("/back")
public class UAjaxController extends UBaseController {

	private static Logger logger = LoggerFactory
			.getLogger(UAjaxController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private WebSecurityMetadataSource webSecurityMetadataSource;

	@RequestMapping("/queryUsers")
	@ResponseBody
	public Map<String, Object> queryUsers(Model model, Integer page,
			String userName) {
		boolean resultStatus = true;
		page = page == null ? 1 : page < 1 ? 1 : page;

		int pageSize = 10;
		Pageable pageable = new PageRequest(page, pageSize, Direction.DESC,
				"id");

		Page<User> pageData = this.userService.searchByUserName(userName,
				pageable);

		Map<String, Object> resMap = new HashMap<String, Object>();

		resMap.put("datas", pageData.getContent());
		resMap.put("totalCount", pageData.getTotalElements());
		resMap.put("totalPage", pageData.getTotalPages());
		resMap.put("currentPage", page);
		resMap.put("pageNav", PageNavUtil.getAjaxPageNavHtml(page.intValue(),
				pageSize, (int) pageData.getTotalElements(), 15));

		resMap.put("resultStatus", resultStatus);
		return resMap;

	}

	@RequestMapping(value = "/changeUserStatus", method = RequestMethod.POST)
	@ResponseBody
	public boolean changeUserStatus(long id, short status) {
		boolean result = true;
		try {
			User user = userService.getById(id);
			user.setStatus(status);
			userService.save(user);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	@RequestMapping(value = "/delUser", method = RequestMethod.POST)
	@ResponseBody
	public int delUser(long id) {
		// 删除单个
		int resultCode = 1;
		try {
			userService.delete(id);
		} catch (Exception e) {
			resultCode = 0;
		}
		return resultCode;
	}

	@RequestMapping(value = "/delUsers", method = RequestMethod.POST)
	@ResponseBody
	public int delUsers(Long[] userIds) {
		// 批量删除
		int resultCode = userService.delUsers(userIds);
		return resultCode;
	}

	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveUser(Model model, User userEntity) {
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
			this.userService.save(userEntity);
		} catch (Exception e) {
			resultStatus = false;
		}

		resMap.put("resultStatus", resultStatus);
		return resMap;

	}

	@RequestMapping(value = "/delRole", method = RequestMethod.POST)
	@ResponseBody
	public int delRole(long id) {
		// 删除单个
		int resultCode = userService.delete(id);
		return resultCode;
	}

	@RequestMapping(value = "/changeRoleStatus", method = RequestMethod.POST)
	@ResponseBody
	public boolean changeRoleStatus(long id, short status) {
		boolean result = true;
		try {
			User user = userService.getById(id);
			user.setStatus(status);
			userService.save(user);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	@RequestMapping(value = "/saveRole", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveRole(Model model, Role roleEntity) {
		boolean resultStatus = true;
		Map<String, Object> resMap = new HashMap<String, Object>();

		String roleName = roleEntity.getRoleName();
		if (roleEntity == null || StringUtils.isBlank(roleName)) {
			resultStatus = false;
			resMap.put("errorMsg", "角色名不能为空");
			resMap.put("resultStatus", resultStatus);
			return resMap;
		}

		// 过滤非法字符
		roleName = ValidatorUtil.filterUnSafeChar(roleName).trim();
		roleEntity.setRoleName(roleName);

		try {
			this.roleService.save(roleEntity);
		} catch (Exception e) {
			resultStatus = false;
		}

		resMap.put("resultStatus", resultStatus);
		return resMap;

	}

	@RequestMapping(value = "/delMenu", method = RequestMethod.POST)
	@ResponseBody
	public int delMenu(int id) {
		// 删除单个
		int resultCode = menuService.delete(id);
		return resultCode;
	}

	@RequestMapping(value = "/saveMenu", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveMenu(Model model, Menu menuEntity) {
		boolean resultStatus = true;
		Map<String, Object> resMap = new HashMap<String, Object>();

		String menuName = menuEntity.getMenuName();
		if (menuEntity == null || StringUtils.isBlank(menuName)) {
			resultStatus = false;
			resMap.put("errorMsg", "角色名不能为空");
			resMap.put("resultStatus", resultStatus);
			return resMap;
		}

		// 过滤非法字符
		menuName = ValidatorUtil.filterUnSafeChar(menuName).trim();
		menuEntity.setMenuName(menuName);

		try {
			this.menuService.save(menuEntity);
		} catch (Exception e) {
			resultStatus = false;
		}

		resMap.put("resultStatus", resultStatus);
		return resMap;

	}

	@RequestMapping(value = "/saveUserRole", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveUserRole(Model model, UserInfo userInfo) {
		boolean resultStatus = true;
		Map<String, Object> resMap = new HashMap<String, Object>();

		long userId = userInfo.getId();
		if (userId <= 0) {
			resultStatus = false;
			resMap.put("errorMsg", "没有指定用户");
			resMap.put("resultStatus", resultStatus);
			return resMap;
		}

		resultStatus = this.userService.saveUserRole(userInfo);

		resMap.put("resultStatus", resultStatus);
		return resMap;

	}

	@RequestMapping(value = "/getMenuTree", method = RequestMethod.POST)
	@ResponseBody
	public List<TreeData> getMenuTree(Model model) {
		List<TreeData> treeData = this.menuService.getTreeData();
		return treeData;
	}

	@RequestMapping(value = "/saveRoleResource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveRoleResource(Model model, short roleId,
			String menuIds) {
		boolean resultStatus = true;
		Map<String, Object> resMap = new HashMap<String, Object>();

		// 需要注意的是jsTree如果一个节点下所有子节点都被选中，则只会返回这个父节点的ID，下面的子节点ID不会返回
		String[] mIds = menuIds.split(",");

		resultStatus = this.menuService.saveRoleResource(roleId, mIds);
		if (resultStatus) {
			// 角色权限改变，重新更新资源和角色关系
			webSecurityMetadataSource.reloadResource();
		}

		resMap.put("resultStatus", resultStatus);
		return resMap;

	}

	@RequestMapping(value = "/saveUserPwd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveUserPwd(Model model, String passWord,
			String rePassWord) {
		boolean resultStatus = true;
		Map<String, Object> resMap = new HashMap<String, Object>();

		if (StringUtils.isBlank(passWord) || StringUtils.isBlank(rePassWord)) {
			resultStatus = false;
			resMap.put("errorMsg", "密码不能为空");
			resMap.put("resultStatus", resultStatus);
			return resMap;
		}

		if (!passWord.equals(rePassWord)) {
			resultStatus = false;
			resMap.put("errorMsg", "两次输入的密码不一致");
			resMap.put("resultStatus", resultStatus);
			return resMap;
		}

		// 取当前登录用户
		WebUserDetails userInfo = this.getUserInfo();
		long userId = userInfo.getUserId();

		User userEntity = this.userService.getById(userId);

		userEntity.setPassWord(passWord);

		User u = this.userService.save(userEntity);

		if (null == u) {
			resultStatus = false;
		}

		resMap.put("resultStatus", resultStatus);
		return resMap;

	}

}