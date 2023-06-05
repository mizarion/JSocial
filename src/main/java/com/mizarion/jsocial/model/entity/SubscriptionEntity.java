package com.mizarion.jsocial.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "subscriptions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(SubscriptionEntity.class)
public class SubscriptionEntity implements Serializable {

    @Id
    @Column(name = "subscriber")
    private String subscriber;
    @Id
    @Column(name = "publisher")
    private String publisher;

}
