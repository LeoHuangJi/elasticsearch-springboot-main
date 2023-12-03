package com.poc.es.elasticsearchspringboot.controller;

import com.poc.es.elasticsearchspringboot.dto.ProductDTO;
import com.poc.es.elasticsearchspringboot.dto.ProductFilter;
import com.poc.es.elasticsearchspringboot.exception.RecordNotFoundException;
import com.poc.es.elasticsearchspringboot.model.Employee;
import com.poc.es.elasticsearchspringboot.model.News;
import com.poc.es.elasticsearchspringboot.model.Product;
import com.poc.es.elasticsearchspringboot.service.ESService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
public class ESRestController {

	@Autowired
	private ESService esService;

	/*
	 * @GetMapping(value = "/index/{id}") public ResponseEntity<News>
	 * fetchEmployeeById(@PathVariable("id") String id) throws
	 * RecordNotFoundException, IOException { News employee =
	 * esService.fetchEmployeeById(id); return ResponseEntity.ok(employee); }
	 * 
	 * @PostMapping("/index/fetchWithMust") public ResponseEntity<List<Employee>>
	 * fetchEmployeesWithMustQuery(@RequestBody Employee employeeSearchRequest)
	 * throws IOException { List<Employee> employees =
	 * esService.fetchEmployeesWithMustQuery(employeeSearchRequest); return
	 * ResponseEntity.ok(employees); }
	 * 
	 * @PostMapping("/index/fetchWithShould") public ResponseEntity<List<Employee>>
	 * fetchEmployeesWithShouldQuery(@RequestBody Employee employeeSearchRequest)
	 * throws IOException { List<Employee> employees =
	 * esService.fetchEmployeesWithShouldQuery(employeeSearchRequest); return
	 * ResponseEntity.ok(employees); }
	 */

	/*
	 * @PostMapping("/index/search") public ResponseEntity<List<Employee>>
	 * employeesCustomSearch(@RequestBody Employee employeeSearchRequest) throws
	 * IOException { List<Employee> employees =
	 * esService.employeesCustomSearch(employeeSearchRequest); return
	 * ResponseEntity.ok(employees); }
	 */
	/*
	 * @PostMapping("/index") public ResponseEntity<String>
	 * insertRecords(@RequestBody Employee employee) throws IOException { String
	 * status = esService.insertEmployee(employee); return
	 * ResponseEntity.ok(status); }
	 * 
	 * @PostMapping("/index/bulk") public ResponseEntity<String>
	 * bulkInsertEmployees(@RequestBody List<Employee> employees) throws IOException
	 * { boolean isSuccess = esService.bulkInsertEmployees(employees); if(isSuccess)
	 * { return ResponseEntity.ok("Records successfully ingested!"); } else { return
	 * ResponseEntity.internalServerError().body("Oops! unable to ingest data"); } }
	 */

	@GetMapping("/product/migration")
	public ResponseEntity<Object> insertRecords() throws IOException {
		Object s = esService.migrationProduct();
		return ResponseEntity.ok(s);

	}
	@PostMapping("/product/index")
	public ResponseEntity<Object> insertRecords(@RequestBody ProductDTO employee) throws IOException {
		Object s = esService.insertProduct(employee);
		return ResponseEntity.ok(s);

	}

	@PostMapping("/product/search")
	public ResponseEntity<Object> productCustomSearch(@RequestBody ProductFilter product) throws IOException,ParseException {

		Object products = esService.productCustomSearch(product);
		return ResponseEntity.ok(products);
	}

}
