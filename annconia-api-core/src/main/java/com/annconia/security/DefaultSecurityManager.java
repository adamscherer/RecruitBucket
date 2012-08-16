package com.annconia.security;

import java.util.List;

import com.annconia.security.entity.PasswordUtility;
import com.annconia.security.entity.SecurityUser;
import com.annconia.security.repository.SecurityGroupRepository;
import com.annconia.security.repository.SecurityUserRepository;

public class DefaultSecurityManager implements SecurityManager {

	SecurityUserRepository userRepository;

	SecurityGroupRepository groupRepository;

	public void createUser(SecurityUser user) {
		userRepository.save(user);
	}

	public void updateUser(SecurityUser user) {
		userRepository.save(user);
	}

	public void deleteUser(String username) {
		userRepository.delete(username);
	}

	public void changePassword(String username, String oldPassword, String newPassword) {
		SecurityUser user = userRepository.findByUsername(username);
		if (user != null && PasswordUtility.isValidPassword(oldPassword, user.getPassword())) {
			user.setPassword(PasswordUtility.encodePassword(newPassword));
			userRepository.save(user);
		}
	}

	public boolean userExists(String username) {
		SecurityUser user = userRepository.findByUsername(username);

		return (user != null);
	}

	public List<String> searchUsername(String firstName, String lastName) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> findAllGroups() {
		/*
		return CollectionUtils.collect(groupRepository.findAll(), new Transformer<SecurityGroup, String>() {

			public String transform(SecurityGroup group) {
				return group.getName();
			}

		}, new ArrayList<String>());
		*/
		return null;
	}

	public List<String> findUsersInGroup(String groupName) {
		// TODO Auto-generated method stub
		return null;
	}

	public void createGroup(String groupName, List<String> roles) {
		// TODO Auto-generated method stub

	}

	public void deleteGroup(String groupName) {
		// TODO Auto-generated method stub

	}

	public void renameGroup(String oldName, String newName) {
		// TODO Auto-generated method stub

	}

	public void addUserToGroup(String username, String group) {
		// TODO Auto-generated method stub

	}

	public void removeUserFromGroup(String username, String groupName) {
		// TODO Auto-generated method stub

	}

	public List<String> findGroupRoles(String groupName) {
		// TODO Auto-generated method stub
		return null;
	}

	public void addGroupRole(String groupName, String role) {
		// TODO Auto-generated method stub

	}

	public void removeGroupRole(String groupName, String role) {
		// TODO Auto-generated method stub

	}

	public SecurityUserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(SecurityUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public SecurityGroupRepository getGroupRepository() {
		return groupRepository;
	}

	public void setGroupRepository(SecurityGroupRepository groupRepository) {
		this.groupRepository = groupRepository;
	}

}
