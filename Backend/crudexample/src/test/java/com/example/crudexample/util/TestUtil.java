package com.example.crudexample.util;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtil {
	private TestUtil() {
	}

	/**
	 * 
	 * @param json
	 * @return parsed String
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public static String parse(String json) throws JsonProcessingException, IOException {
		String statusCode = "";

		try {
			JsonFactory factory = new JsonFactory();

			ObjectMapper mapper = new ObjectMapper(factory);
			JsonNode rootNode = mapper.readTree(json);

			Iterator<Map.Entry<String, JsonNode>> fieldsIterator = rootNode.fields();

			while (fieldsIterator.hasNext()) {
				Map.Entry<String, JsonNode> field = fieldsIterator.next();
				if (field.getKey().equals("status_code")) {
					statusCode = field.getValue().asText();
				}
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return statusCode;
	}
}
