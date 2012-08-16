package com.annconia.security.repository;

import com.annconia.security.entity.SecurityUser;

public interface SecurityUserRepositoryExtension {

	public SecurityUser authenticate(String username, String password);

}
