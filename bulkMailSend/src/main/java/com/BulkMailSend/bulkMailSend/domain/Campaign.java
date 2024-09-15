package com.BulkMailSend.bulkMailSend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "campaign")
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "campaignId" , unique = true)
    private String campaignId;

    @Column(name = "campaignName", nullable = false)
    private String campaignName;

    @ManyToMany(mappedBy = "campaigns")
    private Set<Organisation> organisations = new HashSet<>();

}