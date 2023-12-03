package com.poc.es.elasticsearchspringboot.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

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

@Entity
@Table(name = "PRODUCT")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "TITLE")
	private String title;
	@Column(name = "SAPO")
	private String sapo;
	@Column(name = "STATUS")
	private boolean status;
	@Column(name = "PRICE")
	private Long price;
	@Column(name = "PCODE")
	private String pcode;
	@Column(name = "CREATEDATE")
	private Timestamp createdDate;

}
