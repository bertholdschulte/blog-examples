package org.simple;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.camel.Body;
import org.apache.camel.Headers;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.stereotype.Component;

/**    
 * Sends events to a message-broker like Kafka if a messages is received at the endpoint 'direct:eventService'.
 * This is the place to handle various meta-data and technical stuff like headers, marshaling of the content etc.
 * 
 * Prepare your message broker with a topic of your choice and configure the URI (currently stubbed) in the endpoint below.
 */
@Component
public class EventProducer extends RouteBuilder {


	@Override
	public void configure() throws Exception {
		
		from("direct:eventService")
				.bean(new EventFactory())
				.marshal(new JacksonDataFormat(Event.class))
				.to("stub://kafka://192.168.99.100:9092?topic=event-channel");//temporally stubbed for dev. purposes
		
	}

	public class EventFactory{
		private final DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance();
		
		public Event create(@Body Object body, @Headers Map<String,String> headers){
			headers.put("event-created",dateFormat.format(new Date()));
			return new Event(headers,body);
		}
	}

	public class Event implements Serializable{

		private static final long serialVersionUID = 8548381377946319542L;
		private Map<String,String> headers;
		private Object body;

		public Event(Map<String,String> headers, Object body) {
			this.headers = headers;
			this.body = body;
		}
		
		public Object getBody() {
			return body;
		}

		public void setBody(Object body) {
			this.body = body;
		}

		public Map<String, String> getHeaders() {
			return headers;
		}

		public void setHeaders(Map<String, String> headers) {
			this.headers = headers;
		}

	}
}
