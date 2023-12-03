package com.poc.es.elasticsearchspringboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poc.es.elasticsearchspringboot.model.Product;


public interface  ProductRepository extends JpaRepository<Product, Long> {
	
}
