package fimafeng.back.fimafeng_back.implementations.moderation.detections;

import junit.framework.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class IntentionDetectionTest {

    @Test
    public void cleanTextEmptyTextTest() {
        // Given
        IntentionDetection intentionDetection = new IntentionDetection();
        String text = "";
        ArrayList<String> message = new ArrayList<>(Arrays.asList(text.split(" ")));

        // When
        ArrayList<String> result = intentionDetection.cleanText(message);

        // Then
        String cleanString = "";
        ArrayList<String> cleanArray = new ArrayList<>(Arrays.asList(cleanString.split(" ")));
        Assert.assertEquals(cleanArray, result);
    }

    @Test
    public void cleanTextSingleWordTest() {
        // Given
        IntentionDetection intentionDetection = new IntentionDetection();
        String text = "Bonjour";
        ArrayList<String> message = new ArrayList<>(Arrays.asList(text.split(" ")));

        // When
        ArrayList<String> result = intentionDetection.cleanText(message);

        // Then
        String cleanString = "Bonjour";
        ArrayList<String> cleanArray = new ArrayList<>(Arrays.asList(cleanString.split(" ")));
        Assert.assertEquals(cleanArray, result);
    }

    @Test
    public void cleanTextSingleIrrelevantTextTest() {
        // Given
        IntentionDetection intentionDetection = new IntentionDetection();
        String text = "en";
        ArrayList<String> message = new ArrayList<>(Arrays.asList(text.split(" ")));

        // When
        ArrayList<String> result = intentionDetection.cleanText(message);

        // Then
        ArrayList<String> cleanArray = new ArrayList<>();
        Assert.assertEquals(cleanArray, result);
    }


    @Test
    public void cleanTextNormalSentenceTest() {
        // Given
        IntentionDetection intentionDetection = new IntentionDetection();
        String text = "je suis un mot de passe";
        ArrayList<String> message = new ArrayList<>(Arrays.asList(text.split(" ")));

        // When
        ArrayList<String> result = intentionDetection.cleanText(message);

        // Then
        String cleanString = "suis mot passe";
        ArrayList<String> cleanArray = new ArrayList<>(Arrays.asList(cleanString.split(" ")));
        Assert.assertEquals(cleanArray, result);
    }


    @Test
    public void simplifyTextEmptyTextTest() {
        // Given
        IntentionDetection intentionDetection = new IntentionDetection();
        String text = "";
        ArrayList<String> message = new ArrayList<>(Arrays.asList(text.split(" ")));

        // When
        ArrayList<String> result = intentionDetection.simplifyText(message);

        // Then
        String cleanString = "";
        ArrayList<String> cleanArray = new ArrayList<>(Arrays.asList(cleanString.split(" ")));
        Assert.assertEquals(cleanArray, result);
    }

    @Test
    public void simplifyTextSimpleWordTest() {
        // Given
        IntentionDetection intentionDetection = new IntentionDetection();
        String text = "bonjour";
        ArrayList<String> message = new ArrayList<>(Arrays.asList(text.split(" ")));

        // When
        ArrayList<String> result = intentionDetection.simplifyText(message);

        // Then
        String cleanString = "bonjour";
        ArrayList<String> cleanArray = new ArrayList<>(Arrays.asList(cleanString.split(" ")));
        Assert.assertEquals(cleanArray, result);
    }

    @Test
    public void simplifyTextSimpleConjugaisonWordTest() {
        // Given
        IntentionDetection intentionDetection = new IntentionDetection();
        String text = "avons";
        ArrayList<String> message = new ArrayList<>(Arrays.asList(text.split(" ")));

        // When
        ArrayList<String> result = intentionDetection.simplifyText(message);

        // Then
        String cleanString = "avoir";
        ArrayList<String> cleanArray = new ArrayList<>(Arrays.asList(cleanString.split(" ")));
        Assert.assertEquals(cleanArray, result);
    }

    @Test
    public void simplifyTextNormalMessageWithoutConjugaisonWordTest() {
        // Given
        IntentionDetection intentionDetection = new IntentionDetection();
        String text = "coucou de st simon";
        ArrayList<String> message = new ArrayList<>(Arrays.asList(text.split(" ")));

        // When
        ArrayList<String> result = intentionDetection.simplifyText(message);

        // Then
        String cleanString = "coucou de st simon";
        ArrayList<String> cleanArray = new ArrayList<>(Arrays.asList(cleanString.split(" ")));
        Assert.assertEquals(cleanArray, result);
    }

    @Test
    public void cleanTextNormalMessageTest() {
        // Given
        IntentionDetection intentionDetection = new IntentionDetection();
        String text = "je vais aller dormir";
        ArrayList<String> message = new ArrayList<>(Arrays.asList(text.split(" ")));

        // When
        ArrayList<String> result = intentionDetection.simplifyText(message);

        // Then
        String cleanString = "je aller aller dormir";
        ArrayList<String> cleanArray = new ArrayList<>(Arrays.asList(cleanString.split(" ")));
        Assert.assertEquals(cleanArray, result);
    }





}
