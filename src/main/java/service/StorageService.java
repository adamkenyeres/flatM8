package service;

import annotation.ImplicitNullCheck;
import com.google.common.collect.ImmutableMap;
import model.tenant.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StorageService {

    private final Path rootLocation = Paths.get("src/main/upload-dir");
    private List<String> files;

    public StorageService() {
        this.files = initFiles();
    }

    @ImplicitNullCheck
    private void store(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException("FAIL!");
        }
    }

    private List<String> initFiles() {
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

    public void storeFile(MultipartFile file) {
        store(file);
        files.add(file.getOriginalFilename());
    }

    public List<String> getFiles() {
        return files;
    }

    public Map<String, String> assemblePicturePayload(File f) throws IOException {
        return ImmutableMap.of("content", encodeImage(f));
    }

    public Map<String, String> assemblePicturePayloadForUser(User u) throws IOException {
        File f = new File("src/main/upload-dir/" + u.getAvatarPath());
        return ImmutableMap.of("content", encodeImage(f));
    }

    public String encodeImage(File f) throws IOException {
        return Base64.getEncoder().withoutPadding().encodeToString(Files.readAllBytes(f.toPath()));
    }
}
