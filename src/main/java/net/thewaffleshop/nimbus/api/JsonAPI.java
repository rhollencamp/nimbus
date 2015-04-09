package net.thewaffleshop.nimbus.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.springframework.stereotype.Component;


/**
 *
 * @author rhollencamp
 */
@Component
public class JsonAPI
{
	private final ObjectMapper om = new ObjectMapper();

	public byte[] serialize(Object obj)
	{
		try {
			return om.writeValueAsBytes(obj);
		} catch (JsonProcessingException ex) {
			throw new RuntimeException(ex);
		}
	}

	public <T> T deserialize(byte[] data, Class<T> clazz) {
		try {
			return om.readValue(data, clazz);
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}
}
