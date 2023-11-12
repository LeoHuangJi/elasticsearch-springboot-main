package com.poc.es.elasticsearchspringboot.service;

import com.poc.es.elasticsearchspringboot.exception.RecordNotFoundException;
import com.poc.es.elasticsearchspringboot.model.Employee;
import com.poc.es.elasticsearchspringboot.model.News;
import com.poc.es.elasticsearchspringboot.model.Product;

import java.io.IOException;
import java.util.List;

public interface ESService {

    public News fetchEmployeeById(String id) throws  IOException;

    public String insertEmployee(Employee employee) throws IOException;

    public boolean bulkInsertEmployees(List<Employee> employees) throws IOException;
    public List<Employee> fetchEmployeesWithMustQuery(Employee employee) throws IOException;
    public List<Employee> fetchEmployeesWithShouldQuery(Employee employee) throws IOException;

    public String deleteEmployeeById(Long id) throws IOException;

    public String updateEmployee(Employee employee) throws IOException;
    List<Employee> employeesCustomSearch(Employee employee) throws IOException;
    List<Product> productCustomSearch(Product entry, int pageNumber, int pageSize) throws IOException;

	boolean bulkInsertProducts(List<Product> products)
			throws IOException;

}
