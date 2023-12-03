/*
 * package com.poc.es.elasticsearchspringboot.config;
 * 
 * 
 * import lombok.Getter; import lombok.Setter;
 * 
 * import java.util.List;
 * 
 * import org.apache.http.Header; import org.apache.http.HttpHost; import
 * org.apache.http.HttpResponseInterceptor; import
 * org.apache.http.auth.AuthScope; import
 * org.apache.http.auth.UsernamePasswordCredentials; import
 * org.apache.http.client.CredentialsProvider; import
 * org.apache.http.entity.ContentType; import
 * org.apache.http.impl.client.BasicCredentialsProvider; import
 * org.apache.http.impl.client.HttpClientBuilder; import
 * org.apache.http.message.BasicHeader; import org.elasticsearch.client.Node;
 * import org.elasticsearch.client.RestClient; import
 * org.elasticsearch.client.RestClientBuilder; import
 * org.elasticsearch.client.RestHighLevelClient; import
 * org.springframework.boot.context.properties.ConfigurationProperties; import
 * org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.http.HttpHeaders;
 * 
 * @Configuration public class ESRestClient {
 * 
 * private String hostName;
 * 
 * @Bean public RestHighLevelClient getElasticSearchClient() {
 * 
 * String hostName = "localhost"; int port = 9200;
 * 
 * 
 * RestClientBuilder builder = RestClient.builder(new HttpHost(hostName, port,
 * "http"));
 * 
 * BasicCredentialsProvider credsProv = new BasicCredentialsProvider();
 * credsProv.setCredentials( AuthScope.ANY, new
 * UsernamePasswordCredentials("elastic", "123456") ); // Create the low-level
 * client RestClient restClient =
 * builder.setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
 * //.setDefaultHeaders( // List.of(new BasicHeader(HttpHeaders.CONTENT_TYPE,
 * ContentType.APPLICATION_JSON.toString())))
 * .setDefaultCredentialsProvider(credsProv)
 * .addInterceptorLast((HttpResponseInterceptor) (response, context) -> response
 * .addHeader("X-Elastic-Product", "Elasticsearch"))) .build();
 * 
 * // Create the transport with a Jackson mapper ElasticsearchTransport
 * transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
 * 
 * // And create the API client return new ElasticsearchClient(transport);
 * 
 * } }
 */