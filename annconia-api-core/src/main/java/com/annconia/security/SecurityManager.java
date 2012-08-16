package com.annconia.security;

import java.util.List;

import com.annconia.security.entity.SecurityUser;

public interface SecurityManager {

	/**
	 * Create a new user with the supplied details.
	 */
	void createUser(SecurityUser user);

	/**
	 * Update the specified user.
	 */
	void updateUser(SecurityUser user);

	/**
	 * Remove the user with the given login name from the system.
	 */
	void deleteUser(String username);

	/**
	 * Modify the current user's password. This should change the user's password in
	 * the persistent user repository (database, LDAP etc).
	 *
	 * @param oldPassword current password (for re-authentication if required)
	 * @param newPassword the password to change to
	 */
	void changePassword(String username, String oldPassword, String newPassword);

	/**
	 * Check if a user with the supplied login name exists in the system.
	 */
	boolean userExists(String username);

	/**
	 * Locates the users who are in the security repository
	 *
	 * @param firstName the first name of the user
	 * @param lastName the last name of the user
	 * @return the usernames of the group members
	 */
	List<String> searchUsername(String firstName, String lastName);

	/**
	 * Returns the names of all groups that this group manager controls.
	 */
	List<String> findAllGroups();

	/**
	 * Locates the users who are members of a group
	 *
	 * @param groupName the group whose members are required
	 * @return the usernames of the group members
	 */
	List<String> findUsersInGroup(String groupName);

	/**
	 * Creates a new group with the specified list of authorities.
	 *
	 * @param groupName the name for the new group
	 * @param authorities the authorities which are to be allocated to this group.
	 */
	void createGroup(String groupName, List<String> roles);

	/**
	 * Removes a group, including all members and authorities.
	 *
	 * @param groupName the group to remove.
	 */
	void deleteGroup(String groupName);

	/**
	 * Changes the name of a group without altering the assigned authorities or members.
	 */
	void renameGroup(String oldName, String newName);

	/**
	 * Makes a user a member of a particular group.
	 *
	 * @param username the user to be given membership.
	 * @param group the name of the group to which the user will be added.
	 */
	void addUserToGroup(String username, String group);

	/**
	 * Deletes a user's membership of a group.
	 *
	 * @param username the user
	 * @param groupName the group to remove them from
	 */
	void removeUserFromGroup(String username, String groupName);

	/**
	 * Obtains the list of roles which are assigned to a group.
	 */
	List<String> findGroupRoles(String groupName);

	/**
	 * Assigns a new role to a group.
	 */
	void addGroupRole(String groupName, String role);

	/**
	 * Deletes an role from those assigned to a group
	 */
	void removeGroupRole(String groupName, String role);
}
