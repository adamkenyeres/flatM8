package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import model.tenant.User;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import service.StorageService;
import service.UserService;
import sun.nio.ch.IOUtil;

import javax.servlet.ServletContext;

@RestController
@RequestMapping("/")
public class UploadController {

    private final StorageService storageService;

    private final UserService userService;

    private List<String> files = new ArrayList<String>();

    private ServletContext servletContext;

    @Autowired
    public UploadController(StorageService storageService, UserService userService, ServletContext servletContext) {
        this.storageService = storageService;
        this.userService = userService;
        this.servletContext = servletContext;
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            storageService.store(file);
            files.add(file.getOriginalFilename());

            message = "You successfully uploaded " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "FAIL to upload " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @RequestMapping(value = "/getAvatar", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getAvatar() {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User u = userService.findByUsername(email);

            if (u.getAvatarPath() == null) {
                return ResponseEntity.notFound().build();
            }

            File f = new File("src/main/upload-dir/" + u.getAvatarPath());

            String encodeImage = Base64.getEncoder().withoutPadding().encodeToString(Files.readAllBytes(f.toPath()));
            Map<String, String> jsonMap = new HashMap<>();
            jsonMap.put("content", encodeImage);

            return ResponseEntity.ok(jsonMap);
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/getAvatarByUser", method = RequestMethod.POST)
    public ResponseEntity getAvatarByUser(@RequestBody User u) {
        try {
            if (u.getAvatarPath() == null) {
                return ResponseEntity.notFound().build();
            }

            File f = new File("src/main/upload-dir/" + u.getAvatarPath());

            String encodeImage = Base64.getEncoder().withoutPadding().encodeToString(Files.readAllBytes(f.toPath()));
            Map<String, String> jsonMap = new HashMap<>();
            jsonMap.put("content", encodeImage);

            return ResponseEntity.ok(jsonMap);
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
