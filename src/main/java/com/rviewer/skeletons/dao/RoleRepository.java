package com.rviewer.skeletons.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rviewer.skeletons.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	public Role findByName(String name);
}
