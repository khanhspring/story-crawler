package com.storyscawler.application.api.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin")
public class AdminPingController {

    @GetMapping("ping")
    public String createStoriesCrawlingJobs() {
        return "pong";
    }

}
