package com.tenchael.ispring.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenchael.ispring.domain.Menu;
import com.tenchael.ispring.domain.Role;
import com.tenchael.ispring.model.MenuInfo;
import com.tenchael.ispring.model.NodeData;
import com.tenchael.ispring.model.TreeData;
import com.tenchael.ispring.repository.MenuDao;
import com.tenchael.ispring.service.MenuService;
import com.tenchael.ispring.service.RoleService;

@Service
@Transactional(readOnly = true)
public class MenuServiceImpl implements MenuService {

	private static Logger logger = LoggerFactory
			.getLogger(MenuServiceImpl.class);

	@Autowired
	private MenuDao menuDao;

	@Autowired
	private RoleService roleService;

	public List<Menu> findAll() {
		return menuDao.findAll();
	}

	public Page<Menu> findAll(Pageable page) {
		return menuDao.findAll(page);
	}

	public Menu getById(Integer id) {
		return menuDao.findOne(id);
	}

	@Transactional(readOnly = false)
	public Menu save(Menu entity) {
		return menuDao.save(entity);
	}

	@Transactional(readOnly = false)
	public int delete(Integer id) {
		int result = 1;
		try {
			menuDao.delete(id);
		} catch (Exception e) {
			result = 0;
		}
		return result;
	}

	public void reloadResourceForUser(HttpServletRequest request) {
		// 取得所有可显示的菜单
		List<Menu> allMenus = this.getAllNavMenus();

		List<MenuInfo> menus = this.loadMenuInfos(allMenus);

		request.getSession().setAttribute("menus", menus);

	}

	public List<Menu> getAllNavMenus() {
		Specification<Menu> spec = new Specification<Menu>() {
			public Predicate toPredicate(Root<Menu> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.<Integer> get("navMenu"), 1);
			}
		};
		return menuDao.findAll(spec);
	}

	public List<MenuInfo> loadMenuInfos(List<Menu> allMenus) {

		List<MenuInfo> menus = new ArrayList<MenuInfo>();

		for (Menu menu : allMenus) {
			// 先遍历出第1级菜单
			if (menu.getParentId() == 0) {
				MenuInfo currentMenu = new MenuInfo(menu.getId(),
						menu.getMenuName(), menu.getMenuCode(),
						menu.getMenuUrl());
				menus.add(currentMenu);
				this.loadChildMenus(currentMenu, allMenus);
			}

		}

		return menus;

	}

	private void loadChildMenus(MenuInfo currentMenu, List<Menu> allMenus) {

		for (Menu menu : allMenus) {
			// 如果是当前菜单的子菜单
			if (menu.getParentId() == currentMenu.getMenuId()) {
				MenuInfo childMenu = new MenuInfo(menu.getId(),
						menu.getMenuName(), menu.getMenuCode(),
						menu.getMenuUrl());
				currentMenu.addChild(childMenu);

				// 递归
				this.loadChildMenus(childMenu, allMenus);
			}

		}

	}

	public List<TreeData> getTreeData() {

		List<TreeData> treeDatas = new ArrayList<TreeData>();

		// 取得所有菜单
		List<Menu> allMenus = this.findAll();

		List<MenuInfo> menus = this.loadMenuInfos(allMenus);

		for (MenuInfo menu : menus) {
			//
			TreeData treeData = new TreeData();
			NodeData nodeData = new NodeData();
			nodeData.setTitle(menu.getMenuName());
			nodeData.getAttr().put("id", menu.getMenuId());

			treeData.getData().add(nodeData);
			this.loadTreeChild(treeData, menu.getChildMenus());
			treeDatas.add(treeData);
		}

		return treeDatas;

	}

	private void loadTreeChild(TreeData treeData, List<MenuInfo> menus) {

		for (MenuInfo menu : menus) {
			TreeData child = new TreeData();
			NodeData childNode = new NodeData();
			childNode.setTitle(menu.getMenuName());
			childNode.getAttr().put("id", menu.getMenuId());
			child.getData().add(childNode);

			// 递归
			this.loadTreeChild(child, menu.getChildMenus());
			treeData.getChildren().add(child);
		}

	}

	public boolean saveRoleResource(short roleId, String[] menuIds) {

		Role roleEntity = roleService.getById(roleId);

		Set<Menu> menus = new HashSet<Menu>();
		// 提交的菜单ID
		for (String menuId : menuIds) {
			Menu menuEntity = this.getById(Integer.parseInt(menuId));
			menus.add(menuEntity);
			// 取菜单下所有子菜单
			List<Menu> childMenus = this.getChildMenus(menuEntity.getId());
			for (Menu child : childMenus) {
				menus.add(child);
			}
		}

		roleEntity.setMenus(menus);

		try {
			this.roleService.save(roleEntity);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}

		return true;
	}

	public List<Menu> getChildMenus(final Integer id) {
		Specification<Menu> spec = new Specification<Menu>() {

			public Predicate toPredicate(Root<Menu> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.<Integer> get("parentId"), id);
			}
		};
		return menuDao.findAll(spec);
	}

	public Page<Menu> findByMenuName(final String menuName, Pageable pageable) {
		Specification<Menu> spec = new Specification<Menu>() {
			public Predicate toPredicate(Root<Menu> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.<String> get("menuName"), menuName);
			}
		};
		return menuDao.findAll(spec, pageable);
	}

}
