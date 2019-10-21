package mwvdev.aptpeeker;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "notification.slack.endpoint=http://example.org")
public class AptpeekerApplicationTests {

	@Test
	public void contextLoads() {
	}

}
