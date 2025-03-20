package com.ikitama.management_of_schools.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class APIException {
    String message;
    HttpStatus httpStatus;
    LocalDateTime localDateTime;
}
