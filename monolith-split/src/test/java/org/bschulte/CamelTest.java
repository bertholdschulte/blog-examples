package org.bschulte;

import org.apache.camel.Body;
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

@ContextConfiguration(classes = { CamelTest.ContextConfig.class }, loader = CamelSpringDelegatingTestContextLoader.class)
@RunWith(CamelSpringJUnit4ClassRunner.class)
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class })
public class CamelTest {

	@Produce(uri = "file:/testdata")
	protected ExtractedLegacyPartService service;

	public interface ExtractedLegacyPartService {
		void createPDF(String string);
	}

	public class ExtractedLegacyPart {

		public void createPDF(String string) {
			service.createPDF(string);
		}

	}

	/**
	 * consider the test as the legacy method which calls the service
	 */
	@Test
	public void test() {
		ExtractedLegacyPart legacy = new ExtractedLegacyPart();
		legacy.createPDF("test");
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Configuration
	public static class ContextConfig extends CamelConfiguration {

		@Bean
		public ServiceImpl serivceImpl() {
			return new CamelTest().new ServiceImpl();
		}

	}

	public class ServiceImpl {

		@Consume(uri = "file:/testdata")
		public void onService (@Body String body) {
			System.out.println("created " + body);
		}
	}

}
