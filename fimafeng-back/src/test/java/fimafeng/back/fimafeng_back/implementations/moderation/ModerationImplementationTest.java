package fimafeng.back.fimafeng_back.implementations.moderation;

import fimafeng.back.fimafeng_back.implementations.moderation.detections.BadWordDetection;
import fimafeng.back.fimafeng_back.implementations.moderation.detections.HateDetection;
import fimafeng.back.fimafeng_back.implementations.moderation.detections.IntentionDetection;
import fimafeng.back.fimafeng_back.implementations.moderation.detections.SpamDetection;
import fimafeng.back.fimafeng_back.models.Moderation;
import fimafeng.back.fimafeng_back.models.ModerationAnalysis;
import fimafeng.back.fimafeng_back.models.enums.AnnounceStatus;
import junit.framework.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class ModerationImplementationTest {

    @Test
    void checkDetectionCallTest() {
        // Given
        ModerationImplementation implementation = new ModerationImplementation();
        Moderation moderation = Mockito.mock(Moderation.class);
        ModerationAnalysis analysis = Mockito.mock(ModerationAnalysis.class);
        String title = "title";
        String description = "description";

        BadWordDetection badWordDetection = Mockito.mock(BadWordDetection.class);
        HateDetection hateDetection = Mockito.mock(HateDetection.class);
        IntentionDetection intentionDetection = Mockito.mock(IntentionDetection.class);
        SpamDetection spamDetection = Mockito.mock(SpamDetection.class);
        List<iDetection> detectionList = Arrays.asList(badWordDetection, hateDetection, intentionDetection, spamDetection);

        // Found at https://www.baeldung.com/spring-reflection-test-utils
        ReflectionTestUtils.setField(implementation, "algorithmsDetection", detectionList);


        // When
        Mockito.when(moderation.getAnalysis()).thenReturn(analysis);
        Mockito.when(moderation.getAnnounceTitle()).thenReturn(title);
        Mockito.when(moderation.getAnnounceDescription()).thenReturn(description);

        implementation.analyseAnnounce(moderation);

        // Then
        Mockito.verify(badWordDetection, Mockito.times(1)).run(moderation);
        Mockito.verify(hateDetection, Mockito.times(1)).run(moderation);
        Mockito.verify(intentionDetection, Mockito.times(1)).run(moderation);
        Mockito.verify(spamDetection, Mockito.times(1)).run(moderation);
    }


    private static final Logger LOGGER = Logger.getLogger(ModerationImplementationTest.class.getName());
    private static final String testFile = "announces-sample.csv";
    private static final String separator = ";";

    List<Object[]> loadAnnouncesFromFile(String fileName) {
        InputStream is = null;
        BufferedReader br = null;
        // Tests data will be stored with the following pattern: [[Moderation1, result1], [Moderation2, result2]...]
        List<Object[]> result = new ArrayList<>();
        try {
            is = new ClassPathResource(fileName).getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                // We create an array of 2 element: [Moderation, result]
                String[] object = line.split(separator);
                Moderation moderation = new Moderation();
                moderation.setAnnounceTitle(object[0]);
                moderation.setAnnounceDescription(object[1]);
                moderation.setAnalysis(new ModerationAnalysis(moderation));
                Object[] test = new Object[2];
                test[0] = moderation;
                test[1] = object[2];
                result.add(test);
            }
            br.close();
            is.close();
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    @Test
    void checkModerationResultTest() {
        // Given
        ModerationImplementation implementation = new ModerationImplementation();
        // Warning: testFile content can be incorrect with false positives
        List<Object[]> out = loadAnnouncesFromFile(testFile);

        for (Object[] test : out) {
            Moderation moderation = (Moderation) test[0];

            // When
            implementation.analyseAnnounce(moderation);

            // Then
            LOGGER.info("Moderation: " + moderation.getAnnounceTitle());
            Assert.assertEquals(AnnounceStatus.valueOf((String) test[1]), moderation.getAnalysis().getModerationStatus());
        }
    }



}
