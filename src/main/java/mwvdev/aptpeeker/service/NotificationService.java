package mwvdev.aptpeeker.service;

import mwvdev.aptpeeker.model.NotificationResult;

import java.util.Collection;

public interface NotificationService {

    NotificationResult sendNotification(String serverName, Collection<String> packages);
}
