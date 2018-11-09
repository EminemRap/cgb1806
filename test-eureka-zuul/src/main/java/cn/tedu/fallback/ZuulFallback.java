package cn.tedu.fallback;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
//import org.springframework.stereotype.Component;

//@Component	// 在 zuul 中实现断路器
// 但这样实现的断路器刷新时间比较长，一旦服务正常，刷新正常
public class ZuulFallback implements ZuulFallbackProvider {

	@Override
	public String getRoute() {
		
		return "consumer-feign-hystrix";
		
	}

	@Override
	public ClientHttpResponse fallbackResponse() {
		
		return new ClientHttpResponse() {
			
			@Override
			public HttpHeaders getHeaders() {
				
				HttpHeaders header = new HttpHeaders();
				header.setContentType(MediaType.APPLICATION_JSON_UTF8);
				
				return header;
				
			}
			
			@Override
			public InputStream getBody() throws IOException {
				
				String defaultValue = "Tim Cook";
				return new ByteArrayInputStream(defaultValue.getBytes());
				
			}
			
			@Override
			public String getStatusText() throws IOException {
				
				return HttpStatus.BAD_REQUEST.getReasonPhrase();
				
			}
			
			@Override
			public HttpStatus getStatusCode() throws IOException {
				
				return HttpStatus.BAD_REQUEST;
				
			}
			
			@Override
			public int getRawStatusCode() throws IOException {
				return 0;
			}
			
			@Override
			public void close() {
				
			}
		};
		
	}

}
