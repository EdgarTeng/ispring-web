package com.tenchael.ispring.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenchael.ispring.common.DEncryptionUtils;
import com.tenchael.ispring.common.DateUtil;
import com.tenchael.ispring.domain.Role;
import com.tenchael.ispring.domain.User;
import com.tenchael.ispring.model.UserInfo;
import com.tenchael.ispring.repository.UserDao;
import com.tenchael.ispring.service.RoleService;
import com.tenchael.ispring.service.UserService;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
	private static Logger logger = LoggerFactory
			.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleService roleService;

	public List<User> findAll() {
		return userDao.findAll();
	}

	public Page<User> findAll(Pageable page) {
		return userDao.findAll(page);
	}

	public User getById(Long id) {
		return userDao.findOne(id);
	}

	@Transactional(readOnly = false)
	public User save(User entity) {
		try {
			Long userId = entity.getId();
			String userName = entity.getUserName();
			String passWord = entity.getPassWord();

			if (userId != null) {
				User updateEntity = this.getById(userId);

				updateEntity.setStatus(entity.getStatus());
				updateEntity.setUserName(userName);
				updateEntity.setLastUpdate(DateUtil.getCurrentTime());

				if (StringUtils.isNotBlank(passWord)) {
					updateEntity.setPassWord(DEncryptionUtils
							.standPwdEncoder(passWord));
				}

				return this.userDao.save(updateEntity);
			} else {
				// 新增
				if (null != this.getByUserName(userName)) {
					return null;
				}

				if (StringUtils.isBlank(passWord)) {
					// 新增用户，如果用户没有输入密码，则设置默认的密码
					passWord = "123456";
				}

				entity.setCreateTime(DateUtil.getCurrentTime());
				entity.setLastUpdate(DateUtil.getCurrentTime());
				entity.setPassWord(DEncryptionUtils.standPwdEncoder(passWord));
				return userDao.save(entity);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@Transactional(readOnly = false)
	public int delete(Long id) {
		int result = 1;
		try {
			userDao.delete(id);
		} catch (Exception e) {
			result = 0;
		}
		return result;
	}

	public User getByUserName(String username) {
		return userDao.findByUserName(username);
	}

	public Page<User> searchByUserName(final String userName, Pageable pageable) {
		Specification<User> spec = new Specification<User>() {

			public Predicate toPredicate(Root<User> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.<String> get("userName"), userName);
			}
		};
		return userDao.findAll(spec, pageable);
	}

	@Transactional(readOnly = false)
	public int delUsers(Long[] userIds) {
		int result = userIds.length;
		try {
			List<Long> ids = new ArrayList<Long>();
			for (Long id : userIds) {
				ids.add(id);
			}
			List<User> users = userDao.findAll(ids);
			userDao.deleteInBatch(users);
		} catch (Exception e) {
			result = 0;
		}
		return result;
	}

	@Transactional(readOnly = false)
	public boolean saveUserRole(UserInfo userInfo) {
		boolean result = true;
		try {
			User user = userDao.findOne(userInfo.getId());
			Set<Role> roles = new HashSet<Role>();
			for (Short roleId : userInfo.getRoles()) {
				Role role = roleService.getById(roleId);
				roles.add(role);
			}
			user.setRoles(roles);
			userDao.save(user);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

}
