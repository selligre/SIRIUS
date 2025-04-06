package fimafeng.back.fimafeng_back.implementations.moderation.detections;

import junit.framework.Assert;
import org.junit.jupiter.api.Test;

public class BadWordDetectionTest {

    @Test
    public void isThereBanWordsEmptyMessageTest() {
        //Given
        BadWordDetection badWordDetection = new BadWordDetection();
        String message = "";
        String[] words = message.split(" ");

        // When
        String firstBanWord = badWordDetection.isThereBanWord(words);

        // Then
        Assert.assertEquals(null, firstBanWord);
    }

    @Test
    public void isThereBanWordsOneWordCleanMessageTest() {
        //Given
        BadWordDetection badWordDetection = new BadWordDetection();
        String message = "Bonjour";
        String[] words = message.split(" ");

        // When
        String firstBanWord = badWordDetection.isThereBanWord(words);

        // Then
        Assert.assertEquals(null, firstBanWord);
    }

    @Test
    public void isThereBanWordsMultipleWordCleanMessageTest() {
        //Given
        BadWordDetection badWordDetection = new BadWordDetection();
        String message = "Bonjour comment ça va";
        String[] words = message.split(" ");

        // When
        String firstBanWord = badWordDetection.isThereBanWord(words);

        // Then
        Assert.assertEquals(null, firstBanWord);
    }

    @Test
    public void isThereBanWordsOneWordBadMessageTest() {
        //Given
        BadWordDetection badWordDetection = new BadWordDetection();
        String message = "connard";
        String[] words = message.split(" ");

        // When
        String firstBanWord = badWordDetection.isThereBanWord(words);

        // Then
        Assert.assertEquals("connard", firstBanWord);
    }

    @Test
    public void isThereBanWordsMultipleWordsWithOneBadMessageTest() {
        //Given
        BadWordDetection badWordDetection = new BadWordDetection();
        String message = "Bonjour connard";
        String[] words = message.split(" ");

        // When
        String firstBanWord = badWordDetection.isThereBanWord(words);

        // Then
        Assert.assertEquals("connard", firstBanWord);
    }

    @Test
    public void isThereBanWordsMultipleWordsWithMultipleBadMessageTest() {
        //Given
        BadWordDetection badWordDetection = new BadWordDetection();
        String message = "Bonjour vieux con";
        String[] words = message.split(" ");

        // When
        String firstBanWord = badWordDetection.isThereBanWord(words);

        // Then
        Assert.assertEquals("vieux", firstBanWord);
    }

}
