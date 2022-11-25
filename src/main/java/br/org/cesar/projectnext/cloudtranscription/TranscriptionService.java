package br.org.cesar.projectnext.cloudtranscription;

import java.io.IOException;
import java.util.List;

import com.google.cloud.speech.v1.RecognitionAudio;
import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.RecognizeResponse;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;

public class TranscriptionService {
    public static String transcript(byte[] file) throws IOException{

        String transcription = "";
        // Instantiates a client
        try (SpeechClient speechClient = SpeechClient.create()) {
    
        // The path to the audio file to transcribe
        String gcsUri = "gs://teste_audios_next/textosemingles100.flac";
    
        // Builds the sync recognize request
        RecognitionConfig config =
            RecognitionConfig.newBuilder()
                // .setEncoding(AudioEncoding.FLAC)
                // .setSampleRateHertz(44100)
                .setLanguageCode("en")
                //.setAudioChannelCount(2)
                .build();
        RecognitionAudio audio = RecognitionAudio.newBuilder().setUri(gcsUri).build();
    
        // Performs speech recognition on the audio file
        RecognizeResponse response = speechClient.recognize(config, audio);
        List<SpeechRecognitionResult> results = response.getResultsList();
    
        for (SpeechRecognitionResult result : results) {
            // There can be several alternative transcripts for a given chunk of speech. Just use the
            // first (most likely) one here.
            SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
            //System.out.printf("Transcription: %s%n", alternative.getTranscript());

            transcription = alternative.getTranscript();
        }
        System.out.println("\n\n" + transcription + "\n\n");
        }
        return transcription;
    }
}
