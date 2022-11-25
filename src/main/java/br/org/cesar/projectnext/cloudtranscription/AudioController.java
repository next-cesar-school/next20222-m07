package br.org.cesar.projectnext.cloudtranscription;

import com.google.cloud.translate.v3.LocationName;
import com.google.cloud.translate.v3.TranslateTextRequest;
import com.google.cloud.translate.v3.TranslateTextResponse;
import com.google.cloud.translate.v3.Translation;
import com.google.cloud.translate.v3.TranslationServiceClient;
import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

public class AudioController {
   
@RestController
class TranslateTextController {
    static TranscriptionService transcriptionService;
    
    static String textoTraduzido = "";

    @PostMapping("/EnToPt")
    public ResponseEntity<String> translateEnToPt(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        String transcriptedAudio = transcriptionService.transcript(multipartFile.getBytes());
        String translatedText = translateTextEn(transcriptedAudio);
        return new ResponseEntity<String>(translatedText, HttpStatus.OK);
    }

    @PostMapping("/PtToEn")
    public ResponseEntity<String> translatePtToEn(@RequestBody String text) throws IOException {
        String translatedText = translateTextPt(text);
        return new ResponseEntity<String>(translatedText, HttpStatus.OK);
    }

    public static String translateTextEn(String texto) throws IOException {
        String projectId = "project-next-369319";
        String targetLanguage = "pt";
        String text = texto;
        //translateText(projectId, targetLanguage, text);
        return translateText(projectId, targetLanguage, text);
    }

    public static String translateTextPt(String texto) throws IOException {
        String projectId = "project-next-369319";
        String targetLanguage = "en";
        String text = texto;
        //translateText(projectId, targetLanguage, text);
        return translateText(projectId, targetLanguage, text);
    }

    public static String translateText(String projectId, String targetLanguage, String text)
            throws IOException {

        try (TranslationServiceClient client = TranslationServiceClient.create()) {
            LocationName parent = LocationName.of(projectId, "global");

            TranslateTextRequest request = TranslateTextRequest.newBuilder()
                    .setParent(parent.toString())
                    .setMimeType("text/plain")
                    .setTargetLanguageCode(targetLanguage)
                    .addContents(text)
                    .build();

            TranslateTextResponse response = client.translateText(request);

            // Display the translation for each input text provided
            for (Translation translation : response.getTranslationsList()) {
                System.out.printf("\n\nTranslated text: %s\n", translation.getTranslatedText());
                System.out.println("\n\n");
                textoTraduzido = translation.getTranslatedText();
            }
        }
        return textoTraduzido;
    }

}
    
}

