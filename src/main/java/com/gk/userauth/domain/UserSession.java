package com.gk.userauth.domain;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class UserSession {
    @Id
    @GeneratedValue
    String id;
    String authToken;
    String userID;
    @CreationTimestamp
    Date createdAt;
    @UpdateTimestamp
    Date lastUpdated;
    Date expiresAt;
}
