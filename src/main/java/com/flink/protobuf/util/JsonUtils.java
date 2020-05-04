package com.flink.protobuf.util;


import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


@Slf4j
public class JsonUtils {

	
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	static {
		//允许属性名称 不用双引号包裹
		objectMapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		//忽略未知的属性
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		//date类型返回时间戳
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
		//空数组返回[]
		objectMapper.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, true);
		//枚举类型返回name
		objectMapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
		//
		//objectMapper.setPropertyNamingStrategy(new UpperCasePropertyNamingStrategy());
	}
	
	public static String toJsonString(Object o) throws JsonProcessingException {
		return objectMapper.writeValueAsString(o);
	}
	
	public static void main(String[] args) {
		//System.out.println(JsonUtils.toPojoRLX(null, String.class));
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T toPojo(String content, Class clazz) throws JsonParseException, JsonMappingException, IOException {
		return (T) objectMapper.readValue(content, clazz);
	}
	
	public static <T> T toComplexPojo (String content, TypeReference<T> valueTypeRef) throws JsonParseException, JsonMappingException, IOException {
		return objectMapper.readValue(content,valueTypeRef);
	}
	
	
	public static String toJsonStringRLX(Object o) {
		try {
			return objectMapper.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			log.error("o:{}, msg:{}", o, e.getMessage());
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T toPojoRLX(String content, Class clazz) {
		if(content == null) {
			return null;
		}
		try {
			return (T) objectMapper.readValue(content, clazz);
		} catch (JsonParseException e) {
			e.printStackTrace();
			log.error("content:{}, msg:{}", content, e.getMessage());
		} catch (JsonMappingException e) {
			e.printStackTrace();
			log.error("content:{}, msg:{}", content, e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			log.error("content:{}, msg:{}", content, e.getMessage());
		}
		return null;
	}
	
	public static <T> T toComplexPojoRLX(String content, TypeReference<T> valueTypeRef)  {
		try {
			return objectMapper.readValue(content,valueTypeRef);
		} catch (JsonParseException e) {
			e.printStackTrace();
			log.error("content:{}, valueTypeRef:{}, msg:{}", content, valueTypeRef, e.getMessage());
		} catch (JsonMappingException e) {
			e.printStackTrace();
			log.error("content:{}, valueTypeRef:{}, msg:{}", content, valueTypeRef, e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			log.error("content:{}, valueTypeRef:{}, msg:{}", content, valueTypeRef, e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			log.error("content:{}, valueTypeRef:{}, msg:{}", content, valueTypeRef, e.getMessage());
		}
		return null;
	}
	
	public static ObjectMapper getObjectMapper() {
		return objectMapper;
	}
	
	public static boolean isJSONValid(String jsonString) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			mapper.readTree(jsonString);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
}
