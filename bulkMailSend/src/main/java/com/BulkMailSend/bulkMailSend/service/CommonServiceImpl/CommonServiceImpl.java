package com.BulkMailSend.bulkMailSend.service.CommonServiceImpl;

import com.BulkMailSend.bulkMailSend.domain.Campaign;
import com.BulkMailSend.bulkMailSend.domain.Organisation;
import com.BulkMailSend.bulkMailSend.repository.CampaignRepository;
import com.BulkMailSend.bulkMailSend.repository.OrganisationRepository;
import com.BulkMailSend.bulkMailSend.service.CommonService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import org.apache.poi.ss.usermodel.*;
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

        Campaign newCampaign = new Campaign();
        newCampaign.setCampaignId(UUID.randomUUID().toString());
        newCampaign.setCampaignName(campaign.getCampaignName());

        try {
            return campaignRepository.save(newCampaign);
        } catch (Exception e) {
            logger.error("Error creating campaign: {}", e.getMessage());
            throw new RuntimeException("Could not create campaign: " + e.getMessage(), e);
        }
    }


    @Override
    public Map<String, List<String>> parseExcelAndFetchOrganisationEmails(MultipartFile file) throws IOException {
        Map<String, List<String>> organisationEmails = new HashMap<>();

        final String REQUIRED_COLUMN_NAME = "organisationId";

        try (InputStream is = file.getInputStream(); Workbook workbook = WorkbookFactory.create(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null || sheet.getPhysicalNumberOfRows() < 2) {
                throw new IllegalArgumentException("incorrect format of the file");
            }

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new IllegalArgumentException("incorrect format of the file");
            }

            int organisationIdColumnIndex = -1;
            for (int i = 0; i < headerRow.getPhysicalNumberOfCells(); i++) {
                Cell cell = headerRow.getCell(i);
                if (cell != null && REQUIRED_COLUMN_NAME.equalsIgnoreCase(cell.getStringCellValue())) {
                    organisationIdColumnIndex = i;
                    break;
                }
            }

            if (organisationIdColumnIndex == -1) {
                throw new IllegalArgumentException("incorrect format of file ('organisationId' not present)");
            }

            for (int rowIndex = 1; rowIndex < sheet.getPhysicalNumberOfRows(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null) {
                    continue;
                }

                Cell organisationIdCell = row.getCell(organisationIdColumnIndex);
                if (organisationIdCell == null) {
                    continue;
                }

                String organisationId = organisationIdCell.getStringCellValue();
                logger.info("Parsing for row: {}", rowIndex);

                Organisation organisation = organisationRepository.findByOrganisationId(organisationId);
                if (organisation != null) {
                    organisationEmails.computeIfAbsent(organisation.getOrganisationId(), k -> new ArrayList<>())
                            .add(String.valueOf(organisation.getEmail()));
                }
            }
        }

        return organisationEmails;
    }


    @Override
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
