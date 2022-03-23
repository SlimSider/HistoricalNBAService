package com.kszamosy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NbaMatchComment {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long gameId;
    private String comment;
    private Date timestamp;

    @PrePersist
    protected void onCreate() {
        timestamp = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        timestamp = new Date();
    }
}
