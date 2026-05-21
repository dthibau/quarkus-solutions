package org.formation.domain;


import lombok.Data;

import java.time.Instant;

@Data
public class Event {

    Long id;

    Instant date;

    String entityId;

    String eventName;
}
