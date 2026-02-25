package org.formation.service.dto;

public record NotificationDto(
        String to,
        String subject,
        String text,
        String status
) {
}
