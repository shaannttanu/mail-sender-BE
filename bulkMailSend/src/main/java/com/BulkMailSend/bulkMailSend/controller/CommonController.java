package com.BulkMailSend.bulkMailSend.controller;

import com.BulkMailSend.bulkMailSend.service.CommonService;
import com.BulkMailSend.bulkMailSend.util.RestResponse;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
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

@CrossOrigin
@RequestMapping("/api/v1/emailer")
@RestController
public class CommonController {

    @Autowired
    CommonService commonService;

    @PostMapping("/createCampaign")
    public  void submitCampaign(@RequestParam String campaignName){
        commonService.submitCampaign(campaignName);
    }

    @PostMapping("/submitCsv")
    public static void submitCsv() {
    }


    @PostMapping("/uploadExcel")
    public ResponseEntity<RestResponse<String>> submitExcel(@RequestParam("file") MultipartFile file) {
        try {
            commonService.parseExcelAndFetchOrganisationEmails(file);
            return ResponseEntity.ok(new RestResponse<>("Success",true,null,null,null));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new RestResponse<>("Error processing Excel file ",false, e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value(),null));
        }
    }

    @PostMapping("/sendEmail")
    public static void sendmail(){

    }

    @GetMapping("/search")
    public static void search(){

    }
}
