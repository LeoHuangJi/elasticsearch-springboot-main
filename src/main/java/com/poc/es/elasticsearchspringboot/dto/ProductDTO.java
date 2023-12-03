package com.poc.es.elasticsearchspringboot.dto;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.poc.es.elasticsearchspringboot.model.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class ProductDTO {
	
	private Long id;
	private String title;
	private String sapo;
	private boolean status;
	private Long price;
	private String pcode;
	private String createdDate;
}
