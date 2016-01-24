package org.simple;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.stereotype.Component;

/**    
 * Sends events to a message-broker like Kafka if a messages is received at the endpoint 'direct:eventService'.
 * This is the place to set various technical stuff like headers, marshaling of the content etc.
 * 
 */
@Component
public class EventProducer extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		JacksonDataFormat jsonFormat = new JacksonDataFormat();
		from("direct:eventService")
				.marshal(jsonFormat)
				.setHeader("event-created").simple("${headers." + Exchange.CREATED_TIMESTAMP + "}")
				.to("kafka://...");
	}

}
