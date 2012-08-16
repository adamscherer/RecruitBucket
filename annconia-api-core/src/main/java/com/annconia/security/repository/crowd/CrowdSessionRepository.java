package com.annconia.security.repository.crowd;

import com.annconia.security.entity.Session;
import com.annconia.security.repository.SessionRepository;

public abstract class CrowdSessionRepository implements SessionRepository {

	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void delete(String arg0) {
		// TODO Auto-generated method stub

	}

	public void delete(Session arg0) {
		// TODO Auto-generated method stub

	}

	public void delete(Iterable<? extends Session> arg0) {
		// TODO Auto-generated method stub

	}

	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public Iterable<Session> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public Session findOne(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
