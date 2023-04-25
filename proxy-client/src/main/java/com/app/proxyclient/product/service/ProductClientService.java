package com.app.proxyclient.product.service;

import com.app.proxyclient.product.dto.ProductDto;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class ProductClientService {
    private final static Logger log = LoggerFactory.getLogger(ProductClientService.class);
    private final String productUrl = "http://localhost:8082/api/products/";
    private ObjectMapper mapper = new ObjectMapper().configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    private RestTemplate restTemplate;

    public ProductDto findProductById(HttpServletRequest http, String productId) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String authHeader = http.getHeader("Authorization");
        headers.set("Authorization", authHeader);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ProductDto res;
        try {
            res = restTemplate.exchange(productUrl + productId, HttpMethod.GET, entity, ProductDto.class).getBody();
            if (res != null && res.getQuantity() > 0) {
                return res;
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return null;
    }

    public ProductDto updateProductById(HttpServletRequest http, String productId, Integer quantity, Integer quantityBuy) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String authHeader = http.getHeader("Authorization");
        headers.set("Authorization", authHeader);

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> bodyRequest = new HashMap<>();

        bodyRequest.put("id", productId);
        bodyRequest.put("quantity", quantity - quantityBuy);

        HttpEntity<Map<String, Object>> httpEntityRequest = new HttpEntity<>(bodyRequest, headers);

        ProductDto res;
        try {
            res = restTemplate.exchange(productUrl + productId, HttpMethod.PUT, httpEntityRequest, ProductDto.class).getBody();
            if (res != null && res.getQuantity() > 0) {
                return res;
            }
        } catch (HttpStatusCodeException e) {
            log.error(e.getMessage(), e);
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                log.error(e.getMessage(), e);;
            } else {
                log.error(e.getMessage(), e);;
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return null;
    }
}
