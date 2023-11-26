package com.storyscawler.infrastructure.util;

import com.storyscawler.infrastructure.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Slf4j
public class ImageUtils {

    public static byte[] downloadImageFromUrl(String imgSrc) {
        if (ObjectUtils.isEmpty(imgSrc)) {
            return null;
        }
        try {
            var url = new URL(imgSrc);
            InputStream in = new BufferedInputStream(url.openStream());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n = 0;
            while (-1!=(n=in.read(buf)))
            {
                out.write(buf, 0, n);
            }
            out.close();
            in.close();
            return out.toByteArray();
        } catch (IOException e) {
            log.error("Can not download image from URL {}", imgSrc);
            throw new ApplicationException("Can not download image from URL " + imgSrc);
        }
    }
}
