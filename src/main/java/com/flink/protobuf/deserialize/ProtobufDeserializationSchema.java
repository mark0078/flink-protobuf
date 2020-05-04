package com.flink.protobuf.deserialize;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.flink.api.common.serialization.AbstractDeserializationSchema;

import com.flink.protobuf.util.JsonUtils;
import com.github.os72.protobuf.dynamic.DynamicSchema;
import com.github.os72.protobuf.dynamic.MessageDefinition;
import com.github.os72.protobuf.dynamic.MessageDefinition.Builder;
import com.google.common.collect.Lists;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.DynamicMessage;
import com.googlecode.protobuf.format.JsonFormat;
public class ProtobufDeserializationSchema extends AbstractDeserializationSchema<Person> {
	
	private final long serialVersionUID = 1L;
	private Class clazz;
	private List<TypeDefinition> fields;
	public ProtobufDeserializationSchema()
	{
		init();
	}
	
	private void init() {
		Type genericSuperclass = this.getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        clazz = (Class) actualTypeArguments[0];
        fields = parseFeilds();
	}
	
    @Override
    public Person deserialize(final byte[] message) throws RuntimeException {
        try {
        	DynamicSchema.Builder schemaBuilder = DynamicSchema.newBuilder();
    		schemaBuilder.setName(getName(clazz.getName()) + ".proto");
    		Builder builder = MessageDefinition.newBuilder(getName(clazz.getName()));
    		if(CollectionUtils.isEmpty(fields)) {
    			return null;
    		}
    		fields.forEach(field -> builder.addField(field.getDeclareState(), field.getType(), field.getName(), Integer.valueOf(field.getCode())));
    		MessageDefinition msgDef = builder.build();
    		schemaBuilder.addMessageDefinition(msgDef);
    		DynamicSchema schema = schemaBuilder.build();
    		DynamicMessage.Builder msgBuilder = schema.newMessageBuilder(getName(clazz.getName()));
    		Descriptor msgDesc = msgBuilder.getDescriptorForType();
    		DynamicMessage dynamicMessage = DynamicMessage.parseFrom(msgDesc, message);
    		return JsonUtils.toPojo(JsonFormat.printToString(dynamicMessage), clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 截取类名
     * @param className
     * @return
     */
    private String getName(String className) {
    	String[] array = className.split("\\.");
    	return array[array.length -1];
    }
	/**
	 * 解析注解生成schema
	 * @return
	 */
	private List<TypeDefinition> parseFeilds() {
		 Field[] field = this.clazz.getDeclaredFields();
		 List<TypeDefinition> definitions = Lists.newArrayList();
		 if(field != null) {
			 Arrays.stream(field).forEach(f -> {
				 TypeDefinitionAnnotation annotation = f.getAnnotation(TypeDefinitionAnnotation.class);
				 definitions.add(new TypeDefinition(annotation.declareState(), annotation.type(), annotation.name(), annotation.code()));
			 });
		 }
		return definitions;
	}
	

}
