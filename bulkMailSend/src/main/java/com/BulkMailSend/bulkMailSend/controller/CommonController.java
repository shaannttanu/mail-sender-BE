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

@RestController
public class CommonController {

    @Autowired
    CommonService commonService;

    @PostMapping
    public static void submitCampaign(@RequestParam String campaignName){

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

    @PostMapping
    public static void sendmail(){

    }

    @GetMapping
    public static void search(){

    }
}
