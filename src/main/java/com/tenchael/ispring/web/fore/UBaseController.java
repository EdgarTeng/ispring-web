package com.tenchael.ispring.web.fore;

import org.springframework.security.core.context.SecurityContextHolder;

import com.tenchael.ispring.security.WebUserDetails;

public abstract class UBaseController {

	protected WebUserDetails getUserInfo() {
		WebUserDetails webUserDetails = (WebUserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		return webUserDetails;
	}

}
