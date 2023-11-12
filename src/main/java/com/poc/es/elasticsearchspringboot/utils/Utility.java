package com.poc.es.elasticsearchspringboot.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Utility {

	public static ResponseEntity<Object> ResponseData(HttpStatus status, boolean error,String message, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map.put("timestamp", new Date());
            map.put("status", status.value());
            map.put("success", error);
            map.put("message", message);
            map.put("data", responseObj);

            return new ResponseEntity<Object>(map,status);
        } catch (Exception e) {
            map.clear();
            map.put("timestamp", new Date());
            map.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("success",false);
            map.put("message", e.getMessage());
            map.put("data", null);
            return new ResponseEntity<Object>(map,status);
        }
    }
}
