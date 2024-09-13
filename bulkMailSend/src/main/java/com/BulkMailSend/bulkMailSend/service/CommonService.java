package com.BulkMailSend.bulkMailSend.service;

import com.BulkMailSend.bulkMailSend.domain.Organisation;
import com.BulkMailSend.bulkMailSend.repository.OrganisationRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class CommonService {

    @Autowired
    private OrganisationRepository organisationRepository;

    public void parseExcelAndFetchOrganisationEmails(MultipartFile file) throws IOException {
        Map<String, String> organisationEmails = new HashMap<>();

        try (InputStream is = file.getInputStream();
             Workbook workbook = WorkbookFactory.create(is)) {

            Sheet sheet = workbook.getSheetAt(0); // Get the first sheet

            boolean isFirstRow = true;
            for (Row row : sheet) {
                if (isFirstRow) {
                    isFirstRow = false;
                    continue;
                }

                //change index of organisationId column in getCell() method
                String organisationId = row.getCell(0).getStringCellValue();

                Organisation organisation = organisationRepository.findByOrganisationId(organisationId);
                if (organisation != null) {
                    organisationEmails.put(organisationId, organisation.getEmail());
                }
            }
        }

    }

}
