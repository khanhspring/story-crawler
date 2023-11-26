package com.storyscawler.service.storage;

import com.storyscawler.infrastructure.integration.bunny.BunnyStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {

    private final BunnyStorage bunnyStorage;

    @Override
    public void uploadStoryThumbnail(byte[] content, String fileName) {
        bunnyStorage.uploadThumbnail(content, fileName);
    }
}
