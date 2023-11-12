package com.poc.es.elasticsearchspringboot.model;

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


public class Product {
	 private Long id;
	    private String title;
	    private String sapo;
	    private String detail;
	    private Long price;
	    private Long discountPrice;
	   
}
