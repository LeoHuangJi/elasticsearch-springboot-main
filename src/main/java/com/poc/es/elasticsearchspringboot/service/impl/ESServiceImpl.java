package com.poc.es.elasticsearchspringboot.service.impl;

import com.poc.es.elasticsearchspringboot.connector.ESClientConnector;
import com.poc.es.elasticsearchspringboot.dto.ProductDTO;
import com.poc.es.elasticsearchspringboot.dto.ProductFilter;
import com.poc.es.elasticsearchspringboot.exception.RecordNotFoundException;
import com.poc.es.elasticsearchspringboot.model.Employee;
import com.poc.es.elasticsearchspringboot.model.News;
import com.poc.es.elasticsearchspringboot.model.Product;
import com.poc.es.elasticsearchspringboot.service.ESService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Service
public class ESServiceImpl implements ESService {

	@Autowired
	private ESClientConnector esClientConnector;

	/*
	 * @Override public News fetchEmployeeById(String id) throws
	 * RecordNotFoundException, IOException { return
	 * esClientConnector.fetchEmployeeById(id); }
	 * 
	 * @Override public String insertEmployee(Employee employee) throws IOException
	 * { return esClientConnector.insertEmployee(employee); }
	 * 
	 * @Override public boolean bulkInsertEmployees(List<Employee> employees) throws
	 * IOException { return esClientConnector.bulkInsertEmployees(employees); }
	 * 
	 * @Override public boolean bulkInsertProducts() throws IOException { return
	 * esClientConnector.bulkInsertProducts(); }
	 * 
	 * @Override public List<Employee> fetchEmployeesWithMustQuery(Employee
	 * employee) throws IOException { return
	 * esClientConnector.fetchEmployeesWithMustQuery(employee); }
	 * 
	 * @Override public List<Employee> fetchEmployeesWithShouldQuery(Employee
	 * employee) throws IOException { return
	 * esClientConnector.fetchEmployeesWithShouldQuery(employee); }
	 */

	/*
	 * @Override public List<Employee> employeesCustomSearch(Employee employee)
	 * throws IOException { return
	 * esClientConnector.employeesCustomSearch(employee); }
	 */
	@Override
	public Object migrationProduct() throws IOException {
		return esClientConnector.migrationProduct();
	}

	@Override
	public Object insertProduct(ProductDTO product) throws IOException {
		return esClientConnector.insertProduct(product);
	}

	@Override
	public Object productCustomSearch(ProductFilter product) throws IOException, ParseException {
		return esClientConnector.productCustomSearch(product);
	}

	/*
	 * @Override public String deleteEmployeeById(Long id) throws IOException {
	 * return esClientConnector.deleteEmployeeById(id); }
	 * 
	 * @Override public String updateEmployee(Employee employee) throws IOException
	 * { return esClientConnector.updateEmployee(employee); }
	 */
}
