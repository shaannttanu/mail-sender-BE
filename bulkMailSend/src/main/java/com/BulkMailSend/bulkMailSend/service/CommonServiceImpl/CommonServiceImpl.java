package com.BulkMailSend.bulkMailSend.service.CommonServiceImpl;

import com.BulkMailSend.bulkMailSend.domain.Campaign;
import com.BulkMailSend.bulkMailSend.domain.Organisation;
import com.BulkMailSend.bulkMailSend.repository.CampaignRepository;
import com.BulkMailSend.bulkMailSend.repository.OrganisationRepository;
import com.BulkMailSend.bulkMailSend.service.CommonService;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    CampaignRepository commonRepository;

    @Autowired
    OrganisationRepository organisationRepository;

    Logger logger = LoggerFactory.getLogger(CommonServiceImpl.class);

    @Override
    public void submitCampaign(String campaignName) {
        Campaign campaign= new Campaign();
        campaign.setCampaignId("test1");
        campaign.setCampaignName(campaignName);

        try {
            commonRepository.save(campaign);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }


    @Override
    public Map<String, List<String>> parseExcelAndFetchOrganisationEmails(MultipartFile file) throws IOException {
        Map<String, List<String>> organisationEmails = new HashMap<>();

        try (InputStream is = file.getInputStream();
             Workbook workbook = WorkbookFactory.create(is)) {

            Sheet sheet = workbook.getSheetAt(0);

            boolean isFirstRow = true;
            for (Row row : sheet) {
                if (isFirstRow) {
                    isFirstRow = false;
                    continue;
                }

                // Change index of organisationId column in getCell() method
                String organisationId = row.getCell(0).getStringCellValue();
                logger.info("Parsing for row: {}", row.getRowNum());

                Organisation organisation = organisationRepository.findByOrganisationId(organisationId);
                if (organisation != null) {
                    organisationEmails.put(organisation.getOrganisationId(), organisation.getEmail());
                }
            }
        }

        return organisationEmails;
    }

}
