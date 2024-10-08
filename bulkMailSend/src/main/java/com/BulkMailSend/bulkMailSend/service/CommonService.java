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

public interface CommonService {

    void submitCampaign(String campaignName);
    public void parseExcelAndFetchOrganisationEmails(MultipartFile file) throws IOException;

}
