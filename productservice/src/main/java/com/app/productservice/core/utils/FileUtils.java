package com.itsol.cryp.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);

    public static String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }

    public static String getFileToString(String filePath) {
        try {
            Resource resource = new ClassPathResource(filePath);
            InputStream input = resource.getInputStream();
            return readFromInputStream(input);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    public static InputStream getFile(String filePath) {
        try {
            Resource resource = new ClassPathResource(filePath);
            return resource.getInputStream();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    public static void safeClose(InputStream inputStream) {
        try {
            inputStream.close();
        } catch (IOException e) {
            //
        }
    }
}
