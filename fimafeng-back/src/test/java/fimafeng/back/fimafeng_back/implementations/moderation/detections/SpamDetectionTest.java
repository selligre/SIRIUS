package fimafeng.back.fimafeng_back.implementations.moderation.detections;

import fimafeng.back.fimafeng_back.models.enums.ModerationReason;
import junit.framework.Assert;
import org.junit.jupiter.api.Test;

public class SpamDetectionTest {

    @Test
    public void emptyStringTest() {
        // Given
        SpamDetection spamDetection = new SpamDetection();
        String message = "";

        // When
        ModerationReason result = spamDetection.detect(message);

        // Then
        Assert.assertEquals(ModerationReason.INTENTION_OK, result);
    }


    @Test
    public void spamStringTest() {
        // Given
        SpamDetection spamDetection = new SpamDetection();
        String message = "azeeeazeeeazezezezeeez";

        // When
        ModerationReason result = spamDetection.detect(message);

        // Then
        Assert.assertEquals(ModerationReason.SPAM, result);
    }

    @Test
    public void okStringTest() {
        // Given
        SpamDetection spamDetection = new SpamDetection();
        String message = "hello tout va bien hihi";

        // When
        ModerationReason result = spamDetection.detect(message);

        // Then
        Assert.assertEquals(ModerationReason.INTENTION_OK, result);
    }

    @Test
    public void trickyStringTest() {
        // Given
        SpamDetection spamDetection = new SpamDetection();
        String message = "hello tout va bien hihihih";

        // When
        ModerationReason result = spamDetection.detect(message);

        // Then
        Assert.assertEquals(ModerationReason.INTENTION_OK, result);
    }


}
