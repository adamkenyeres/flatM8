package controller;

import model.tenant.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import service.StorageService;
import service.UserService;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/")
public class UploadController {

    private final StorageService storageService;
    private final UserService userService;

    @Autowired
    public UploadController(StorageService storageService, UserService userService, ServletContext servletContext) {
        this.storageService = storageService;
        this.userService = userService;
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            storageService.storeFile(file);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/getAvatar", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getAvatar() throws IOException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User u = userService.getUserByEmail(email);
        return getAvatarByUser(u);
    }

    @RequestMapping(value = "/getAvatarByUser", method = RequestMethod.POST)
    public ResponseEntity getAvatarByUser(@RequestBody User u) throws IOException {
        File f = new File("src/main/upload-dir/placeholder.jpg");
        try {
            if (u.getAvatarPath() == null) {
                return ResponseEntity.ok(storageService.assemblePicturePayload(f));
            }
            return ResponseEntity.ok(storageService.assemblePicturePayloadForUser(u));
        } catch (Exception ex) {
            return ResponseEntity.ok(storageService.assemblePicturePayload(f));
        }
    }

    @RequestMapping(value = "/getFileByName", method = RequestMethod.GET)
    public ResponseEntity getAvatarByUser(@RequestParam String file) throws IOException {
        boolean exists = storageService.getFiles().stream()
                .anyMatch(f -> f.equals(file));

        if (exists) {
            File f = new File("src/main/upload-dir/" + file);
            return ResponseEntity.ok(storageService.assemblePicturePayload(f));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
