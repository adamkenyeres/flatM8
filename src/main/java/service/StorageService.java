package service;

import annotation.ImplicitNullCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StorageService {

    private final Path rootLocation = Paths.get("src/main/upload-dir");

    @ImplicitNullCheck
    public void store(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException("FAIL!");
        }
    }

    public List<String> getFiles() {
        try {
            File folder = new File(rootLocation.toUri());
            File[] listOfFiles = folder.listFiles();

            return Arrays.stream(listOfFiles)
                    .map(File::getName)
                    .collect(Collectors.toList());
        }catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
