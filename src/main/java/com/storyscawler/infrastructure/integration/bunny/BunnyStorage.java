package com.storyscawler.infrastructure.integration.bunny;

import com.storyscawler.infrastructure.exception.ApplicationException;
import com.storyscawler.infrastructure.property.BunnyProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class BunnyStorage {

    private static final String VERSION = "1.0.4";
    private final BunnyProperties properties;

    public void uploadThumbnail(byte[] content, String fileName) {
        upload(content, properties.getStoryThumbnailFolder() + "/" + fileName);
    }

    public void upload(byte[] content, String filePath) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(List.of(MediaType.ALL));
            headers.add("User-Agent", "Java-BCDN-Client-" + VERSION);
            headers.add("AccessKey", properties.getApiKey());

            Resource resource = new ByteArrayResource(content);
            HttpEntity<Object> requestEntity
                    = new HttpEntity<>(resource, headers);

            var url = properties.getBaseUrl()
                    + "/"
                    + properties.getStorageZoneName()
                    + "/"
                    + filePath;

            var restTemplate = new RestTemplate();
            restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
        } catch (RestClientResponseException e) {
            log.error("Error when upload image to Bunny, response [{}]", e.getResponseBodyAsString(), e);
            throw new ApplicationException("Error when upload image to Bunny");
        } catch (Exception e) {
            log.error("Error when upload image to Bunny", e);
            throw new ApplicationException("Error when upload image to Bunny");
        }
    }
}
