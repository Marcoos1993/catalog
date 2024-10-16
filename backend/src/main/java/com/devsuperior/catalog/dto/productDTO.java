package com.devsuperior.catalog.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.devsuperior.catalog.entities.Category;
import com.devsuperior.catalog.entities.Product;

public class productDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String description;
	private Double price;
	private String imgUrl;
	private Date date;

	private List<CategoryDTO> categories = new ArrayList<>();
	
	public productDTO() {
	}

	public productDTO(Long id, String name, String description, Double price, String imgUrl, Date date) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.imgUrl = imgUrl;
		this.date = date;
	}
	
	public productDTO(Product entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.description = entity.getDescription();
		this.price = entity.getPrice();
		this.imgUrl = entity.getImgUrl();
		this.date = entity.getDate();
		
	}
	
	public productDTO(Product entity, Set<Category> categories) {
		this(entity);
		categories.forEach(x -> this.categories.add(new CategoryDTO(x)));
		}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<CategoryDTO> getCategories() {
		return categories;
	}
	
	}
	
	

