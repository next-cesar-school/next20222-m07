package br.org.cesar.projectnext.cloudtranscription;

import java.util.List;

import com.google.cloud.speech.v1p1beta1.RecognitionAudio;
import com.google.cloud.speech.v1p1beta1.RecognitionConfig;
import com.google.cloud.speech.v1p1beta1.RecognizeResponse;
import com.google.cloud.speech.v1p1beta1.SpeechClient;
import com.google.cloud.speech.v1p1beta1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1p1beta1.SpeechRecognitionResult;

public class QuickstartSample {
  public static void main(String... args) throws Exception {
      String transcription = "";

    try (SpeechClient speechClient = SpeechClient.create()) {
  
      String gcsUri = "gs://bucket-quickstart_project-next-369319/textosemingles100.flac";
  
      RecognitionConfig config =
          RecognitionConfig.newBuilder()
              // .setEncoding(AudioEncoding.FLAC)
              // .setSampleRateHertz(44100)
              .setLanguageCode("en")
              //.setAudioChannelCount(2)
              .build();
      RecognitionAudio audio = RecognitionAudio.newBuilder().setUri(gcsUri).build();

      RecognizeResponse response = speechClient.recognize(config, audio);
      List<SpeechRecognitionResult> results = response.getResultsList();
  
      for (SpeechRecognitionResult result : results) {
        SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);

        transcription = alternative.getTranscript();
      }
      System.out.println("\n\n" + transcription + "\n\n");
    }
  }
}