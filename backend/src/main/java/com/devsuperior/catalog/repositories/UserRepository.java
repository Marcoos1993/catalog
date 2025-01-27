package com.devsuperior.catalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.catalog.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
