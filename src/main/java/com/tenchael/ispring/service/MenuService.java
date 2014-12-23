package com.tenchael.ispring.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tenchael.ispring.domain.Menu;
import com.tenchael.ispring.model.MenuInfo;
import com.tenchael.ispring.model.TreeData;

public interface MenuService extends BasicService<Menu, Integer> {

	public void reloadResourceForUser(HttpServletRequest request);

	public List<MenuInfo> loadMenuInfos(List<Menu> allMenus);

	public List<TreeData> getTreeData();

	public boolean saveRoleResource(short roleId, String[] mIds);
	
	public List<Menu> getChildMenus(final Integer id);

	public Page<Menu> findByMenuName(String menuName, Pageable pageable);

}
