package org.formation.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
public class Event {

    @Id @GeneratedValue
    Long id;

    Instant date;

    String entityId;

    String eventName;
}
