package com.poc.es.elasticsearchspringboot.config;

import java.io.IOException;

import javax.annotation.PreDestroy;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.elasticsearch.client.RestHighLevelClient;

@Configuration
public class ElasticConfig {
	@Autowired
	Environment environment;

	private RestHighLevelClient client;

	@SuppressWarnings("deprecation")

	@Bean(destroyMethod = "close")
	public RestHighLevelClient prepareConnection() {

		RestClientBuilder restBuilder = RestClient.builder(new HttpHost("localhost", 9200, "http"));
		String username = "elastic";// new String(environment.getProperty("es.username").toString());
		String password = "123456";// new String(environment.getProperty("es.password").toString());
		if (username != null & password != null) {
			
			final CredentialsProvider creadential = new BasicCredentialsProvider();
			creadential.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
			
			restBuilder.setHttpClientConfigCallback(new HttpClientConfigCallback() {
				@Override
				public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
					return httpClientBuilder.setDefaultCredentialsProvider(creadential)
							
							.setDefaultIOReactorConfig(IOReactorConfig.custom().setIoThreadCount(1).build());
				}
			});
			restBuilder.setDefaultHeaders(new Header[] {
					new org.apache.http.message.BasicHeader(HttpHeaders.ACCEPT, "application/vnd.elasticsearch+json;compatible-with=7"),
					new org.apache.http.message.BasicHeader(HttpHeaders.CONTENT_TYPE, "application/vnd.elasticsearch+json;compatible-with=7") });
			restBuilder.setRequestConfigCallback(
					requestConfigBuilder -> requestConfigBuilder.setConnectTimeout(10000).setSocketTimeout(60000)
							/*
							 * time of inactivity to wait for packets[data] to receive.
							 */
							.setConnectionRequestTimeout(0)); // time to fetch a connection from the connection pool 0
																// for infinite.
			client = new RestHighLevelClient(restBuilder);
			return client;
		}
		return null;
	}


	/*
	 * it gets called when bean instance is getting removed from the context if
	 * scope is not a prototype
	 */
	/*
	 * If there is a method named shutdown or close then spring container will try
	 * to automatically configure them as callback methods when bean is being
	 * destroyed
	 */

	@PreDestroy
	public void clientClose() {
		try {
			this.client.close();
		} catch (IOException e) {

		}
	}

}