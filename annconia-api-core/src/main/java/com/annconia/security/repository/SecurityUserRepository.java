package com.annconia.security.repository;

import org.springframework.data.repository.CrudRepository;

import com.annconia.security.entity.SecurityUser;


public interface SecurityUserRepository extends SecurityUserRepositoryExtension, CrudRepository<SecurityUser, String> {

	SecurityUser findByUsername(String username);

}
