package com.annconia.security.repository;

import org.springframework.data.repository.CrudRepository;

import com.annconia.security.entity.Session;


public interface SessionRepository extends CrudRepository<Session, String> {

}
