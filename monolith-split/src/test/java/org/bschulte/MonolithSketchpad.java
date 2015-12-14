package org.bschulte;

import org.apache.camel.Consume;
import org.apache.camel.Produce;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.apache.camel.test.spring.CamelSpringDelegatingTestContextLoader;
import org.apache.camel.test.spring.CamelSpringJUnit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

/**
 * Test to simulate the communication between a monolith and an extracted
 * code-snippet of the monolith's legacy code. The extracted part, in this
 * example a method to create a PDF-file, is executed in something like a
 * spring-boot service. The test-case plays the role of the legacy application
 * which calls the extracted interface. The service is started in the background
 * via {@link CamelSpringJUnit4ClassRunner}. The data during the test-run is
 * exchanged via a file (URI: file:/...) as the 'transport channel' to simplify
 * the example.
 */
@ContextConfiguration(classes = {
		MonolithSketchpad.ContextConfig.class }, loader = CamelSpringDelegatingTestContextLoader.class)
@RunWith(CamelSpringJUnit4ClassRunner.class)
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class })
public class MonolithSketchpad {

	private static final int A_FEW_SECONDS = 10000;

	@Produce(uri = "file://{{user.home}}/transport-data")
	protected ExtractedLegacyPartService service;

	public interface ExtractedLegacyPartService {
		void createPDF(String string);
	}

	/**
	 * the old monolith's class/method which now calls the service
	 */
	public class ExtractedLegacyPart {

		public void createPDF(String string) {
			service.createPDF(string);
		}

	}

	/**
	 * Consider the test-case as the legacy application, which calls the
	 * service. In this case the communication is asynchronous
	 */
	@Test
	public void testExtractedPartIntegration() {

		ExtractedLegacyPart legacy = new ExtractedLegacyPart();
		legacy.createPDF("some data in a document");

		try {
			Thread.sleep(A_FEW_SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Implements the extracted legacy part as a service. It consumes the files
	 * respectively your chosen transport channel and executes some logic.
	 * Consumed files are deleted.
	 */
	public class ServiceImpl {

		@Consume(uri = "file://{{user.home}}/transport-data")
		public void createPDF(String input) {
			System.out.println("Service created: " + input);
		}
	}

	@Configuration
	public static class ContextConfig extends CamelConfiguration {

		@Bean
		public ServiceImpl serivceImpl() {
			return new MonolithSketchpad().new ServiceImpl();
		}

	}

}
