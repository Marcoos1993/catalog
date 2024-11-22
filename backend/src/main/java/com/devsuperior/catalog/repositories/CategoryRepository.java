package com.devsuperior.catalog.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.catalog.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
	
	public Page<Category> findAll(Pageable pageable);
	
}
