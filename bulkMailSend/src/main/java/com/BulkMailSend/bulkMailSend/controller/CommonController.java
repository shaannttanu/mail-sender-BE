package com.BulkMailSend.bulkMailSend.controller;

import com.BulkMailSend.bulkMailSend.domain.Campaign;
import com.BulkMailSend.bulkMailSend.service.CommonService;
import com.BulkMailSend.bulkMailSend.util.RestResponse;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RequestMapping("/api/v1/emailer")
@RestController
public class CommonController {

    @Autowired
    CommonService commonService;

    @PostMapping("/createCampaign")
    public ResponseEntity<RestResponse<?>> submitCampaign(@RequestBody Campaign campaign,
                                                          HttpServletRequest request) {

        try{
            Campaign createdCampaign = commonService.submitCampaign(campaign);

            Map<String, Object> response = new HashMap<>();
            response.put("id", createdCampaign.getId());
            response.put("campaignName", createdCampaign.getCampaignName());

            return ResponseEntity.ok(new RestResponse<>(response
                    ,true,null
                    ,null,null));
        }catch (Exception e){
            return ResponseEntity.status(500)
                    .body(new RestResponse<>("Could not create campaign",
                            false,
                            e.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            request.getRequestURI()));
        }
    }

    @PostMapping("/submitCsv")
    public static void submitCsv() {
    }

    Logger logger = LoggerFactory.getLogger(CommonController.class);

    @PostMapping("/uploadExcel")
    public ResponseEntity<RestResponse<?>> submitExcel(@RequestParam("file") MultipartFile file) {
        try {
            Map<String,List<String>> organisationEmails = commonService.parseExcelAndFetchOrganisationEmails(file);
            return ResponseEntity.ok(new RestResponse<>(organisationEmails
                    ,true,null
                    ,null,null));
        } catch (Exception e) {
            logger.error("Failed for {}",e.getMessage());
            return ResponseEntity.status(500)
                .body(new RestResponse<>("Error processing Excel file ",
                        false,
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    null));
        }
    }

    @PostMapping("/sendEmail")
    public static void sendmail(){

    }

    @GetMapping("/search")
    public static void search(){

    }
}
