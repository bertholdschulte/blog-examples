package org.simple;

import org.apache.camel.Body;
import org.apache.camel.Header;
import org.apache.camel.InOnly;
import org.apache.camel.Produce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Application to start
 * 
 */
@SpringBootApplication
public class SimpleApplication {

	/**
	 * Defines an event-service as needed.
	 */
	public interface EventService {
		@InOnly
		void fireEvent(@Header("event-type") String eventType, @Body Object payload);
	}

	/**
	 * Creates a proxy used by camel to send events to the endpoint 'seda:eventService'.
	 * Automatically called if the method fireEvent is executed on the interface {@link EventService}
	 */
	@Produce(uri = "seda:eventService")
	public EventService eventService;

	/**
	 * Used in the business related source code to fire events.
	 * @return instance of {@link EventService}
	 */
	@Bean
	public EventService eventService() {
		return eventService;
	}

	/**
	 * Starts the application
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(SimpleApplication.class, args);
	}
}
