package com.spring.example.common.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FieldErrorDetail {

    private String field;
    private String message;

    public static FieldErrorDetail getInstance(String field, String message) {
        return FieldErrorDetail.builder()
                .field(field)
                .message(message)
                .build();
    }
}
