package mwvdev.aptpeeker.controller;

import mwvdev.aptpeeker.model.NotificationResult;
import mwvdev.aptpeeker.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/package")
public class PackageController {

    private final NotificationService notificationService;

    @Autowired
    public PackageController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RequestMapping("/updates/{serverName}")
    @PostMapping
    public ResponseEntity handleUpdates(@PathVariable String serverName, @RequestBody Collection<String> packages) {
        if (packages.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        NotificationResult notificationResult = notificationService.sendNotification(serverName, packages);

        if (notificationResult.isSuccess()) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
