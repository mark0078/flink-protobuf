package com.flink.protobuf.deserialize;

import com.flink.protobuf.util.JsonUtils;
import com.github.os72.protobuf.dynamic.DynamicSchema;
import com.github.os72.protobuf.dynamic.MessageDefinition;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.DescriptorValidationException;
import com.google.protobuf.DynamicMessage;

public class Demo {
	
	/**
	 * test
	 * @param args
	 * @throws DescriptorValidationException
	 */
	public static void main(String[] args) throws DescriptorValidationException {
		DynamicSchema.Builder schemaBuilder = DynamicSchema.newBuilder();
		schemaBuilder.setName("PersonSchemaDynamic.proto");

		MessageDefinition msgDef = MessageDefinition.newBuilder("Person") // message Person
				.addField("required", "int32", "id", 1)		// required int32 id = 1
				.addField("required", "string", "name", 2)	// required string name = 2
				.build();

		schemaBuilder.addMessageDefinition(msgDef);
		DynamicSchema schema = schemaBuilder.build();

		// Create dynamic message from schema
		DynamicMessage.Builder msgBuilder = schema.newMessageBuilder("Person");
		Descriptor msgDesc = msgBuilder.getDescriptorForType();
		DynamicMessage msg = msgBuilder
				.setField(msgDesc.findFieldByName("id"), 1)
				.setField(msgDesc.findFieldByName("name"), "Alan Turing")
				.build();
		Person person = (new ProtobufDeserializationSchema()).deserialize(msg.toByteArray());
		System.out.println(JsonUtils.toJsonStringRLX(person));
	}
}
