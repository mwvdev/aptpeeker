package mwvdev.aptpeeker.service;

import mwvdev.aptpeeker.configuration.SlackNotificationProperties;
import mwvdev.aptpeeker.model.NotificationError;
import mwvdev.aptpeeker.model.NotificationResult;
import mwvdev.aptpeeker.model.SlackNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

@Service
public class SlackNotificationService implements NotificationService {

    private final RestTemplate restTemplate;
    private final SlackNotificationProperties slackNotificationProperties;

    @Autowired
    public SlackNotificationService(RestTemplateBuilder restTemplateBuilder,
                                    SlackNotificationProperties slackNotificationProperties) {
        this.restTemplate = restTemplateBuilder.build();
        this.slackNotificationProperties = slackNotificationProperties;
    }

    @Override
    public NotificationResult sendNotification(Collection<String> packages) {
        SlackNotification slackNotification = new SlackNotification(composeMessage(packages));
        HttpEntity<SlackNotification> entity = new HttpEntity<>(slackNotification);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(slackNotificationProperties.getEndpoint(), entity, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return new NotificationResult(true);
        }

        return new NotificationResult(new NotificationError(responseEntity.getBody()));
    }

    private String composeMessage(Collection<String> packages) {
        return "Updates are available for the following packages:\n\n" +
                String.join("\n", packages);
    }

}
