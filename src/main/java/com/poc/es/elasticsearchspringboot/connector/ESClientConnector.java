package com.poc.es.elasticsearchspringboot.connector;

import com.poc.es.elasticsearchspringboot.dto.ProductDTO;
import com.poc.es.elasticsearchspringboot.dto.ProductFilter;
import com.poc.es.elasticsearchspringboot.exception.RecordNotFoundException;
import com.poc.es.elasticsearchspringboot.model.Employee;
import com.poc.es.elasticsearchspringboot.model.News;
import com.poc.es.elasticsearchspringboot.model.Product;
import com.poc.es.elasticsearchspringboot.repository.ProductRepository;

import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.core.TimeValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.ScrollableHitSource.Hit;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class ESClientConnector {

	@Value("${elastic.index}")
	private String index;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private RestHighLevelClient client;
	@Autowired
	private ObjectMapper objectMapper;

	public Object insertProduct(ProductDTO product) throws IOException {

		IndexRequest request = new IndexRequest("products", "_doc", product.getId().toString());
		request.source(new Gson().toJson(product), XContentType.JSON);
		IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
		return indexResponse;
	}

	public Object migrationProduct() throws IOException {
		List<Product> ps = productRepository.findAll();
		BulkRequest bulkRequest = new BulkRequest();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		objectMapper.setDateFormat(df);
		ps.forEach(item -> {

			ProductDTO dto = objectMapper.convertValue(item, ProductDTO.class);

			IndexRequest indexRequest = new IndexRequest("products").id(item.getId().toString())
					.source(new Gson().toJson(dto), XContentType.JSON);

			bulkRequest.add(indexRequest);
		});

		BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
		if (bulkResponse.hasFailures()) {
			// Handle failures
			System.out.println("Bulk request has failures: " + bulkResponse.buildFailureMessage());
		} else {
			// Handle successful bulk request
			System.out.println("Bulk request succeeded!");
			return bulkResponse;

		}

		return "";
	}

	/*
	 * public boolean bulkInsertEmployees(List<Employee> employeeList) throws
	 * IOException { BulkRequest.Builder builder = new BulkRequest().Builder();
	 * 
	 * employeeList.stream().forEach(employee -> builder.operations( op ->
	 * op.index(i ->
	 * i.index(index).id(String.valueOf(employee.getId())).document(employee))));
	 * BulkResponse bulkResponse = elasticsearchClient.bulk(builder.build()); return
	 * !bulkResponse.errors(); }
	 * 
	 * public boolean bulkInsertProducts() throws IOException { List<Product> ps =
	 * productRepository.findAll();
	 * 
	 * BulkRequest.Builder builder = new BulkRequest.Builder();
	 * 
	 * ps.stream().forEach(prod -> builder .operations(op -> op.index(i ->
	 * i.index("products").id(String.valueOf(prod.getId())).document(prod))));
	 * BulkResponse bulkResponse = elasticsearchClient.bulk(builder.build()); return
	 * !bulkResponse.errors(); }
	 * 
	 * public News fetchEmployeeById(String id) throws RecordNotFoundException,
	 * IOException {
	 * 
	 * GetResponse<News> response = elasticsearchClient.get(req ->
	 * req.index(index).id(id), News.class);
	 * 
	 * //return new Employee(); return response.source(); }
	 * 
	 * public List<Employee> fetchEmployeesWithMustQuery(Employee employee) throws
	 * IOException { List<Query> queries = prepareQueryList(employee);
	 * 
	 * SearchResponse<Employee> employeeSearchResponse = elasticsearchClient.search(
	 * req -> req.index(index).size(employee.getSize()).query(query ->
	 * query.bool(bool -> bool.must(queries))), Employee.class);
	 * 
	 * return
	 * employeeSearchResponse.hits().hits().stream().map(Hit::source).collect(
	 * Collectors.toList()); }
	 */

	private String previousDate(Timestamp date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = dateFormat.format(date);
		try {
			Date myDate = dateFormat.parse(strDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(myDate);
			calendar.add(Calendar.DAY_OF_YEAR, -1);
			Date previousDate = calendar.getTime();
		return dateFormat.format(previousDate);
		} catch (Exception e) {
			// TODO: handle exception
		}
	
		return null;
		
		
	}

	public Object productCustomSearch(ProductFilter entry) throws IOException, ParseException {

		BoolQueryBuilder qb = QueryBuilders.boolQuery();
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		if (!ObjectUtils.isEmpty(entry.getTitle())) {
			qb.must(QueryBuilders.matchQuery("title", entry.getTitle()));

		}

		if (!ObjectUtils.isEmpty(entry.getPcode())) {
			qb.must(QueryBuilders.matchQuery("pcode", entry.getPcode()));
		}

		if (!ObjectUtils.isEmpty(entry.getPrice())) {
			qb.must(QueryBuilders.rangeQuery("price").lte(entry.getPrice()));
			qb.must(QueryBuilders.rangeQuery("price").gte(entry.getPrice()));
		}

		// filter
		
		
		if (!ObjectUtils.isEmpty(entry.getFromDate()) && ObjectUtils.isEmpty(entry.getToDate())) {
			String fromDate = dateFormat.format(entry.getFromDate());
			qb.filter(QueryBuilders.rangeQuery("createdDate").gte(fromDate).format("yyyy-MM-dd"));
			//sourceBuilder.sort("createdDate", SortOrder.ASC);
		}
		if (ObjectUtils.isEmpty(entry.getFromDate()) && !ObjectUtils.isEmpty(entry.getToDate())) {
			String toDate = dateFormat.format(entry.getToDate());
			qb.filter(QueryBuilders.rangeQuery("createdDate").lte(toDate).format("yyyy-MM-dd"));
			//sourceBuilder.sort("createdDate", SortOrder.DESC);
		}

		if (!ObjectUtils.isEmpty(entry.getFromDate()) && !ObjectUtils.isEmpty(entry.getToDate())) {
			String fromDate = dateFormat.format(entry.getFromDate());
			String toDate = dateFormat.format(entry.getToDate());
			qb.filter(QueryBuilders.rangeQuery("createdDate").gte(fromDate).lte(toDate).format("yyyy-MM-dd"));
			//sourceBuilder.sort("createdDate", SortOrder.ASC);
		}

		// điều kiện tìm kiếm, nếu keyword null mới sort
		
		int offset = (entry.getPageIndex() - 1) * entry.getPageSize();
		sourceBuilder.query(qb);
		sourceBuilder.from(offset);
		sourceBuilder.size(entry.getPageSize());
		sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
		
		// đa cho truy vấn
		System.out.println("query:" + sourceBuilder.query());
		// Tạo SearchRequest và thiết lập index cần tìm kiếm
		SearchRequest searchRequest = new SearchRequest("products");
		searchRequest.source(sourceBuilder);
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		// return searchResponse;

		List<Product> profileDocuments = new ArrayList<>();

		/*
		 * for (SearchHit hit : searchResponse.getHits().getHits()){ profileDocuments
		 * .add(objectMapper.convertValue(hit .getSourceAsMap(), Product.class)); }
		 */
		// client.close();
		return searchResponse;

		// Search by product name

		// Combine name and price queries to search the product index

	}

	/*
	 * public List<Product> productCustomSearch(Product entry, int pageNumber, int
	 * pageSize) throws IOException {
	 * 
	 * // Search by product name
	 * 
	 * // Combine name and price queries to search the product index
	 * 
	 * SearchResponse<Product> searchResponse = elasticsearchClient.search(req -> {
	 * req.index("products");
	 * 
	 * if (!ObjectUtils.isEmpty(entry.getTitle())) {
	 * 
	 * Query byTtile = MatchQuery.of(m ->
	 * m.field("title").query(entry.getTitle()))._toQuery(); req.query(byTtile); }
	 * 
	 * if (entry.getPrice() > 0) { req.query(q -> q.range(range ->
	 * range.field("price").from(entry.getPrice().toString())
	 * .to(entry.getPrice().toString())));
	 * 
	 * }
	 * 
	 * if (pageSize > 0) { req.from((pageNumber - 1) * pageSize).size(pageSize); }
	 * // if (sortOptions != null) { // req.sort(sortOptions); // } return req; },
	 * Product.class);
	 * 
	 * return
	 * searchResponse.hits().hits().stream().map(Hit::source).collect(Collectors.
	 * toList()); }
	 */

	/*
	 * public String deleteEmployeeById(Long id) throws IOException { DeleteRequest
	 * request = DeleteRequest.of(req -> req.index(index).id(String.valueOf(id)));
	 * DeleteResponse response = elasticsearchClient.delete(request); return
	 * response.result().toString(); }
	 * 
	 * public String updateEmployee(Employee employee) throws IOException {
	 * UpdateRequest<Employee, Employee> updateRequest = UpdateRequest .of(req ->
	 * req.index(index).id(String.valueOf(employee.getId())).doc(employee));
	 * UpdateResponse<Employee> response = elasticsearchClient.update(updateRequest,
	 * Employee.class); return response.result().toString(); }
	 * 
	 * private List<Query> prepareQueryList(Employee employee) { Map<String, String>
	 * conditionMap = new HashMap<>(); conditionMap.put("id.keyword",
	 * employee.getId().toString()); conditionMap.put("firstName.keyword",
	 * employee.getFirstName()); conditionMap.put("lastName.keyword",
	 * employee.getLastName()); conditionMap.put("gender.keyword",
	 * employee.getGender()); conditionMap.put("jobTitle.keyword",
	 * employee.getJobTitle()); conditionMap.put("phone.keyword",
	 * employee.getPhone()); conditionMap.put("email.keyword", employee.getEmail());
	 * 
	 * return conditionMap.entrySet().stream().filter(entry ->
	 * !ObjectUtils.isEmpty(entry.getValue())) .map(entry ->
	 * QueryBuilderUtils.termQuery(entry.getKey(), entry.getValue()))
	 * .collect(Collectors.toList()); }
	 */

}
