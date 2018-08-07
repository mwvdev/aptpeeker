package mwvdev.aptpeeker;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "notification.slack.endpoint=http://example.org")
public class AptpeekerApplicationTests {

	@Test
	public void contextLoads() {
	}

}
