package org.formation.service;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationDto {
    public String to;
    public String subject;
    public String text;
    public String status;
}
