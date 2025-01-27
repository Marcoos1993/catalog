package com.devsuperior.catalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.catalog.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
