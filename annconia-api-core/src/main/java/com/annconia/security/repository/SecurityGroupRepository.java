package com.annconia.security.repository;

import org.springframework.data.repository.CrudRepository;

import com.annconia.security.entity.SecurityGroup;


public interface SecurityGroupRepository extends CrudRepository<SecurityGroup, String> {

}
