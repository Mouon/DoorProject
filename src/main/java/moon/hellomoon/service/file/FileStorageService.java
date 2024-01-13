package moon.hellomoon.service.file;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {
    private final String uploadDir = "/Users/munhyeonjun/Desktop/hello-moon/src/main/resources/templates/public";

    public String storeFile(MultipartFile file) throws IOException {
        String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        File targetFile = new File(Paths.get(uploadDir).resolve(filename).toString());
        file.transferTo(targetFile);

        return filename;
    }
}

