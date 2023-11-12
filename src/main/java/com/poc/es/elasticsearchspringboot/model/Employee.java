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


public class Employee {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String jobTitle;
    private String phone;
    private Integer size;
}
