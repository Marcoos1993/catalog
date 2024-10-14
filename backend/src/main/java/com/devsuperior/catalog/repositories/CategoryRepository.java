package com.devsuperior.catalog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.catalog.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
	
	public List<Category> findAll();
	
}
