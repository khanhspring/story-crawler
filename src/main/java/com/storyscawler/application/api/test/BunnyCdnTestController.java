package com.storyscawler.application.api.test;

import com.storyscawler.infrastructure.integration.bunny.BunnyStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;

@Slf4j
@Profile("local")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "test/bunny")
public class BunnyCdnTestController {
    private final BunnyStorage bunnyStorage;

    @GetMapping("upload")
    public void upload() throws Exception {
        var file = new File("D:\\\\Workspace\\Projects\\codedee\\codedee-webapp\\public\\images\\avatar.png");
        log.info("{}", file.exists());
        bunnyStorage.upload(new FileInputStream(file).readAllBytes(), "avatar.png");
    }
}
