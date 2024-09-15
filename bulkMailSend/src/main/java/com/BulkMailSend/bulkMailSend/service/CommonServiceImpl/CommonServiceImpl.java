package com.BulkMailSend.bulkMailSend.service.CommonServiceImpl;

import com.BulkMailSend.bulkMailSend.domain.Campaign;
import com.BulkMailSend.bulkMailSend.domain.Organisation;
import com.BulkMailSend.bulkMailSend.repository.CampaignRepository;
import com.BulkMailSend.bulkMailSend.repository.OrganisationRepository;
import com.BulkMailSend.bulkMailSend.service.CommonService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    CampaignRepository campaignRepository;

    @Autowired
    OrganisationRepository organisationRepository;

    Logger logger = LoggerFactory.getLogger(CommonServiceImpl.class);


    @Override
    public Campaign submitCampaign(Campaign campaign) {
        // Create a new Campaign object
        Campaign newCampaign = new Campaign();
        newCampaign.setCampaignId(UUID.randomUUID().toString());
        newCampaign.setCampaignName(campaign.getCampaignName());

        try {
            // Save the campaign and return the saved object
            return campaignRepository.save(newCampaign);
        } catch (Exception e) {
            logger.error("Error creating campaign: {}", e.getMessage());
            throw new RuntimeException("Could not create campaign: " + e.getMessage(), e);
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

    public ByteArrayResource generateTemplate() throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Template");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("organisationId");
            headerRow.createCell(1).setCellValue("organisationName");

            workbook.write(outputStream);

            return new ByteArrayResource(outputStream.toByteArray());
        }
    }

}
