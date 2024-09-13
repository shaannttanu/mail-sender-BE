package com.BulkMailSend.bulkMailSend.domain;

import com.BulkMailSend.bulkMailSend.converter.EmailAttributeConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "organisation")
public class Organisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id" , nullable = false)
    private long id;

    @Column(name = "organisationId" , nullable = false ,unique = true)
    private String organisationId;

    @Column(name = "organisationName" , nullable = false)
    private String organisationName ;

    @Convert(converter = EmailAttributeConverter.class)
    @Column(name = "email", columnDefinition = "TEXT")
    private List<String> email;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "organisationCampaignMapping",
            joinColumns = { @JoinColumn(name = "organisationId") },
            inverseJoinColumns = { @JoinColumn(name = "campaignId") }
    )
    private Set<Campaign> campaigns = new HashSet<>();

}
