package org.formation.web;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDto {

    public int status;
    public String severity;
    public String message;
}
