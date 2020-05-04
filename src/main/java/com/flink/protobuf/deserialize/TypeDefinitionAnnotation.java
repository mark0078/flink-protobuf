package com.flink.protobuf.deserialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TypeDefinitionAnnotation {

	String declareState();
	String type();
	String name();
	String code();
}
