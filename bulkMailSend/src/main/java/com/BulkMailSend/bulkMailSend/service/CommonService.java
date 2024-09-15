package com.BulkMailSend.bulkMailSend.service;

import com.BulkMailSend.bulkMailSend.domain.Campaign;
import com.BulkMailSend.bulkMailSend.domain.Organisation;
import com.BulkMailSend.bulkMailSend.dto.MailDto;
import com.BulkMailSend.bulkMailSend.repository.OrganisationRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service

public interface CommonService {

    Campaign submitCampaign(Campaign campaignName);
    List<String> parseExcelAndFetchOrganisationEmails(MultipartFile file) throws IOException;
    ByteArrayResource generateTemplate() throws IOException;

    void sendMail(MailDto mailDto);
}
