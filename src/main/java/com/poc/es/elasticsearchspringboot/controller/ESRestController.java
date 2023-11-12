package com.poc.es.elasticsearchspringboot.controller;

import com.poc.es.elasticsearchspringboot.exception.RecordNotFoundException;
import com.poc.es.elasticsearchspringboot.model.Employee;
import com.poc.es.elasticsearchspringboot.model.News;
import com.poc.es.elasticsearchspringboot.model.Product;
import com.poc.es.elasticsearchspringboot.service.ESService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class ESRestController {


    @Autowired
    private ESService esService;

    @GetMapping(value = "/index/{id}")
    public ResponseEntity<News> fetchEmployeeById(@PathVariable("id") String id) throws RecordNotFoundException, IOException {
    	News employee = esService.fetchEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @PostMapping("/index/fetchWithMust")
    public ResponseEntity<List<Employee>> fetchEmployeesWithMustQuery(@RequestBody Employee employeeSearchRequest) throws IOException {
        List<Employee> employees = esService.fetchEmployeesWithMustQuery(employeeSearchRequest);
        return ResponseEntity.ok(employees);
    }

    @PostMapping("/index/fetchWithShould")
    public ResponseEntity<List<Employee>> fetchEmployeesWithShouldQuery(@RequestBody Employee employeeSearchRequest) throws IOException {
        List<Employee> employees = esService.fetchEmployeesWithShouldQuery(employeeSearchRequest);
        return ResponseEntity.ok(employees);
    }
    
    @PostMapping("/index/search")
    public ResponseEntity<List<Employee>> employeesCustomSearch(@RequestBody Employee employeeSearchRequest) throws IOException {
        List<Employee> employees = esService.employeesCustomSearch(employeeSearchRequest);
        return ResponseEntity.ok(employees);
    }

    @PostMapping("/index")
    public ResponseEntity<String> insertRecords(@RequestBody Employee employee) throws IOException {
        String status = esService.insertEmployee(employee);
        return ResponseEntity.ok(status);
    }

    @PostMapping("/index/bulk")
    public ResponseEntity<String> bulkInsertEmployees(@RequestBody List<Employee> employees) throws IOException {
        boolean isSuccess = esService.bulkInsertEmployees(employees);
        if(isSuccess) {
            return ResponseEntity.ok("Records successfully ingested!");
        } else {
            return ResponseEntity.internalServerError().body("Oops! unable to ingest data");
        }
    }
    
    @PostMapping("/product/search")
    public ResponseEntity<List<Product>> productCustomSearch(@RequestBody Product product) throws IOException {
        List<Product> products = esService.productCustomSearch(product,1,10);
        return ResponseEntity.ok(products);
    }
    @PostMapping("/product/bulk")
    public ResponseEntity<String> bulkInsertProduct(@RequestBody List<Product> products) throws IOException {
        boolean isSuccess = esService.bulkInsertProducts(products);
        if(isSuccess) {
            return ResponseEntity.ok("Records successfully ingested!");
        } else {
            return ResponseEntity.internalServerError().body("Oops! unable to ingest data");
        }
    }

    @DeleteMapping("/index/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") Long id) throws IOException {
        String status = esService.deleteEmployeeById(id);
        return ResponseEntity.ok(status);
    }

    @PutMapping("/index")
    public ResponseEntity<String> updateEmployee(@RequestBody Employee employee) throws IOException {
        String status = esService.updateEmployee(employee);
        return ResponseEntity.ok(status);
    }
}
