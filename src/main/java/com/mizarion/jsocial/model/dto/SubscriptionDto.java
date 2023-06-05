package com.mizarion.jsocial.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDto {

    @ApiModelProperty(value = "Subscriber's name")
    private String subscriber;

    @ApiModelProperty(value = "Publisher's name", required = true)
    private String publisher;

}
