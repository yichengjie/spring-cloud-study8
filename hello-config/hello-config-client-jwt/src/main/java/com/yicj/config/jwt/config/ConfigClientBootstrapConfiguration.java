package com.yicj.config.jwt.config;

import com.yicj.config.jwt.dto.LoginRequest;
import com.yicj.config.jwt.dto.Token;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.config.client.ConfigClientProperties;
import org.springframework.cloud.config.client.ConfigServicePropertySourceLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Data
@Slf4j
@Order
@Configuration
public class ConfigClientBootstrapConfiguration {

    @Value("${spring.cloud.config.username}")
    private String jwtUserName ;

    @Value("${spring.cloud.config.password}")
    private String jwtPassword ;

    @Value("${spring.cloud.config.endpoint}")
    private String jwtEndpoint ;

    private String jwtToken ;

    @Autowired
    private ConfigurableEnvironment environment ;

    @PostConstruct
    public void init(){
        RestTemplate restTemplate = new RestTemplate() ;
        LoginRequest loginBackend = new LoginRequest() ;
        loginBackend.setUsername(jwtUserName);
        loginBackend.setPassword(jwtPassword);
        String serviceUrl = jwtEndpoint ;
        try {
            Token token = restTemplate.postForObject(serviceUrl, loginBackend, Token.class);
            if (token.getToken() == null){
                throw new Exception("token empty error !") ;
            }
            this.setJwtToken(token.getToken());
        }catch (Exception e){
            log.error("obtain token error : ", e);
        }
    }


    @Bean
    public ConfigClientProperties clientProperties(){
        ConfigClientProperties clientProperties = new ConfigClientProperties(this.environment) ;
        clientProperties.setEnabled(false);
        return clientProperties ;
    }


    @Bean
    public ConfigServicePropertySourceLocator configServicePropertySourceLocator(
            ConfigClientProperties clientProperties){
        ConfigServicePropertySourceLocator sourceLocator = new ConfigServicePropertySourceLocator(clientProperties) ;
        sourceLocator.setRestTemplate(customRestTemplate());
        return sourceLocator ;
    }


    private RestTemplate customRestTemplate(){
        Map<String, String> headers = new HashMap<>() ;
        headers.put("token", "Bearer:" + jwtToken) ;
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory() ;
        requestFactory.setReadTimeout((60 *1000 *3) + 5000); // todo 3m5s make
        RestTemplate template = new RestTemplate(requestFactory) ;
        template.setInterceptors(Arrays.asList(new GenericRequestHeaderInterceptor(headers)));
        return template ;
    }


    public static class GenericRequestHeaderInterceptor implements ClientHttpRequestInterceptor {

        private final Map<String, String> headers;

        public GenericRequestHeaderInterceptor(Map<String, String> headers) {
            this.headers = headers;
        }

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {
            for (Map.Entry<String, String> header : this.headers.entrySet()) {
                request.getHeaders().add(header.getKey(), header.getValue());
            }
            return execution.execute(request, body);
        }

        protected Map<String, String> getHeaders() {
            return this.headers;
        }
    }

}
