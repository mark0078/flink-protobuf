package com.flink.protobuf.deserialize;

import lombok.Data;

@Data
public class Person {
	@TypeDefinitionAnnotation(declareState = "required", type = "int32", name = "id", code = "1")
	String id;
	@TypeDefinitionAnnotation(declareState = "required", type = "string", name = "name", code = "2")
	String name;
}
