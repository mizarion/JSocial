package com.mizarion.jsocial.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDto {

    private Long id;

    private String subscriber;

    private String publisher;

    public SubscriptionDto(String subscriber, String publisher) {
        this.subscriber = subscriber;
        this.publisher = publisher;
    }
}
