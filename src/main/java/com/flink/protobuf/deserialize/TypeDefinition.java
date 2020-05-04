package com.flink.protobuf.deserialize;
/**
 * schema 字段 定义
 * @author zouzijing
 *
 */
public class TypeDefinition {
	private String declareState;
	private String type;
	private String name;
	private String code;
	public TypeDefinition(String declareState, String type, String name, String code) {
		this.declareState = declareState;
		this.type = type;
		this.code = code;
		this.name = name;
	}
	public String getDeclareState() {
		return declareState;
	}
	public String getType() {
		return type;
	}
	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
}