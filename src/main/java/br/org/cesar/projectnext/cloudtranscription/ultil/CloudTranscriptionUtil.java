package br.org.cesar.projectnext.cloudtranscription.ultil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

public class CloudTranscriptionUtil {
    public static void saveFile(String fileName, MultipartFile multipartFile) throws IOException{
        Path uploadDirectory = Paths.get("Files-Upload");

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadDirectory.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            
        } catch (IOException ioe) {
            throw new IOException("Error saving upload file: " + fileName, ioe);
        }
    }
    
}
