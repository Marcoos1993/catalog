package com.devsuperior.catalog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.catalog.services.ProductService;

@RestController
@RequestMapping(value = "/producties")
public class ProductController {

	@Autowired
	private ProductService productService;


		
}
