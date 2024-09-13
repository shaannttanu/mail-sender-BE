package com.BulkMailSend.bulkMailSend.controller;

import com.BulkMailSend.bulkMailSend.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {

    @Autowired
    CommonService commonService;

    @PostMapping
    public static void submitCampaign(@RequestParam String campaignName){

    }

    @PostMapping
    public static void submitCsv(){

    }

    @PostMapping
    public static void sendmail(){

    }

    @GetMapping
    public static void search(){

    }
}
