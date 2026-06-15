package com.springboot.Service.Common.EmailService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public  Boolean sendEmail(String toEmailId, String subject, String body,Boolean isHtml, HttpServletRequest request){
        return true;
    }
}
