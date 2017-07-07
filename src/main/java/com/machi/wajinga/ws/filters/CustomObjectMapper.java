package com.machi.wajinga.ws.filters;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@Provider
public class CustomObjectMapper implements ContextResolver<ObjectMapper> {

	final ObjectMapper defaultObjectMapper;

	public CustomObjectMapper() {
		defaultObjectMapper = createDefaultMapper();
	}

	@Override
	public ObjectMapper getContext(Class<?> type) {
		return defaultObjectMapper;
	}

	private static ObjectMapper createDefaultMapper() {

		final ObjectMapper result = new ObjectMapper();
		result.configure(SerializationConfig.Feature.USE_STATIC_TYPING, false);
		result.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		result.setSerializationInclusion(Inclusion.NON_NULL);

		return result;
	}
}
