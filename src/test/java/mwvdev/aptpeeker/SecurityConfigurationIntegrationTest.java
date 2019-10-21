package mwvdev.aptpeeker;

import mwvdev.aptpeeker.model.NotificationResult;
import mwvdev.aptpeeker.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
                properties = "notification.slack.endpoint=http://example.org")
public class SecurityConfigurationIntegrationTest
{

    @LocalServerPort
    private int port;

    @Autowired
    private SecurityProperties securityProperties;

    @MockBean
    private NotificationService notificationService;

    @Test
    public void canHandlePackageUpdatesWhenAuthenticated() throws IllegalStateException {
        List<String> packages = PackageTestData.getPackages();

        TestRestTemplate restTemplate = getRestTemplateWithValidCredentials();
        HttpEntity<Collection<String>> entity = new HttpEntity<>(packages);

        when(notificationService.sendNotification("server-name", packages)).thenReturn(new NotificationResult(true));

        ResponseEntity<String> response = restTemplate
                .withBasicAuth(securityProperties.getUser().getName(), securityProperties.getUser().getPassword())
                .postForEntity(getUri("api/package/updates/server-name"), entity, String.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void canGetHealthAnynomously() throws IllegalStateException {
        TestRestTemplate restTemplate = getAnonymousRestTemplate();

        ResponseEntity<String> response = restTemplate.getForEntity(getUri("actuator/health"), String.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void getMetricsRefusesAcccessWhenAnonymous() throws IllegalStateException {
        TestRestTemplate restTemplate = getAnonymousRestTemplate();

        ResponseEntity<String> response = restTemplate.getForEntity(getUri("actuator/metrics"), String.class);

        assertThat(response.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
    }

    @Test
    public void getMetricsRefusesAcccessWhenCredentialsInvalid() throws IllegalStateException {
        TestRestTemplate restTemplate = getRestTemplateWithInvalidCredentials();

        ResponseEntity<String> response = restTemplate.getForEntity(getUri("actuator/metrics"), String.class);

        assertThat(response.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
    }

    @Test
    public void canGetMetricsWhenAuthenticated() throws IllegalStateException {
        TestRestTemplate restTemplate = getRestTemplateWithValidCredentials();

        ResponseEntity<String> response = restTemplate.getForEntity(getUri("actuator/metrics"), String.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    private TestRestTemplate getAnonymousRestTemplate() {
        return new TestRestTemplate();
    }

    private TestRestTemplate getRestTemplateWithValidCredentials() {
        SecurityProperties.User user = securityProperties.getUser();
        return new TestRestTemplate(user.getName(), user.getPassword());
    }

    private TestRestTemplate getRestTemplateWithInvalidCredentials() {
        return new TestRestTemplate("invalid", "invalid");
    }

    private URI getUri(String path) {
        return UriComponentsBuilder
                .fromHttpUrl("http://localhost:{port}/{path}")
                .buildAndExpand(port, path)
                .toUri();
    }

}
