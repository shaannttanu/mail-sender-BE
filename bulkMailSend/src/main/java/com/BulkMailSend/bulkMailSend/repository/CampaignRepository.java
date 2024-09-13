package com.BulkMailSend.bulkMailSend.repository;

import com.BulkMailSend.bulkMailSend.domain.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign,Long> {
}
