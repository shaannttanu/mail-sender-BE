package com.BulkMailSend.bulkMailSend.repository;

import com.BulkMailSend.bulkMailSend.domain.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganisationRepository extends JpaRepository<Organisation,Long> {
    Organisation findByOrganisationId(String organisationId);
}
