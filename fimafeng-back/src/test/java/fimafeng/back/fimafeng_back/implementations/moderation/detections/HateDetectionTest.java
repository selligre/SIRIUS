package fimafeng.back.fimafeng_back.implementations.moderation.detections;

import junit.framework.Assert;
import org.junit.jupiter.api.Test;

public class HateDetectionTest {

    @Test
    public void countRegularMessageTest() {
        // Given
        HateDetection hateDetection = new HateDetection();
        String message = "hello tout va bien";

        // When
        int capitalCount = hateDetection.countConsecutiveCapitals(message);

        // Then
        Assert.assertEquals(0, capitalCount);
    }

    @Test
    public void countSingleCapitalMessageTest() {
        // Given
        HateDetection hateDetection = new HateDetection();
        String message = "Hello Tout va bien par Ici";

        // When
        int capitalCount = hateDetection.countConsecutiveCapitals(message);

        // Then
        Assert.assertEquals(0, capitalCount);
    }

    @Test
    public void countMultiCapitalPerWordMessageTest() {
        // Given
        HateDetection hateDetection = new HateDetection();
        String message = "HelLo TouT vA bIEN par ICi";

        // When
        int capitalCount = hateDetection.countConsecutiveCapitals(message);

        // Then
        Assert.assertEquals(9, capitalCount);
    }

    @Test
    public void countEmptyMessageTest() {
        // Given
        HateDetection hateDetection = new HateDetection();
        String message = "";

        // When
        int capitalCount = hateDetection.countConsecutiveCapitals(message);

        // Then
        Assert.assertEquals(0, capitalCount);
    }


    @Test
    public void countBadWordEmptyTest() {
        // Given
        HateDetection hateDetection = new HateDetection();
        String message = "";

        // When
        int badWordCount = hateDetection.countBadWords(message);

        // Then
        Assert.assertEquals(0, badWordCount);
    }

    @Test
    public void countBadWordSingleWordTest() {
        // Given
        HateDetection hateDetection = new HateDetection();
        String message = "Hello";

        // When
        int badWordCount = hateDetection.countBadWords(message);

        // Then
        Assert.assertEquals(0, badWordCount);
    }

    @Test
    public void countBadWordSingleBadWordTest() {
        // Given
        HateDetection hateDetection = new HateDetection();
        String message = "Con";

        // When
        int badWordCount = hateDetection.countBadWords(message);

        // Then
        Assert.assertEquals(1, badWordCount);
    }

    @Test
    public void countBadWordComplexeTest() {
        // Given
        HateDetection hateDetection = new HateDetection();
        String message = "Bonjour 35p1ngo1n roh con";

        // When
        int badWordCount = hateDetection.countBadWords(message);

        // Then
        Assert.assertEquals(2, badWordCount);
    }


    @Test
    public void calculateUpperLetterRatioZerosTest() {
        // Given
        HateDetection hateDetection = new HateDetection();
        int consecutiveCapitals = 0;
        int numberOfWords = 0;

        // When
        int ratio = hateDetection.calculateUpperLettersRatio(consecutiveCapitals, numberOfWords);

        // Then
        Assert.assertEquals(0, ratio);
    }

    @Test
    public void calculateUpperLetterRatio10Test() {
        // Given
        HateDetection hateDetection = new HateDetection();
        int consecutiveCapitals = 1;
        int numberOfWords = 0;

        // When
        int ratio = hateDetection.calculateUpperLettersRatio(consecutiveCapitals, numberOfWords);

        // Then
        Assert.assertEquals(1, ratio);
    }

    @Test
    public void calculateUpperLetterRatio01Test() {
        // Given
        HateDetection hateDetection = new HateDetection();
        int consecutiveCapitals = 0;
        int numberOfWords = 1;

        // When
        int ratio = hateDetection.calculateUpperLettersRatio(consecutiveCapitals, numberOfWords);

        // Then
        Assert.assertEquals(0, ratio);
    }

    @Test
    public void calculateUpperLetterRatio42Test() {
        // Given
        HateDetection hateDetection = new HateDetection();
        int consecutiveCapitals = 4;
        int numberOfWords = 2;

        // When
        int ratio = hateDetection.calculateUpperLettersRatio(consecutiveCapitals, numberOfWords);

        // Then
        Assert.assertEquals(2, ratio);
    }

    @Test
    public void calculateUpperLetterRatio24Test() {
        // Given
        HateDetection hateDetection = new HateDetection();
        int consecutiveCapitals = 2;
        int numberOfWords = 4;

        // When
        int ratio = hateDetection.calculateUpperLettersRatio(consecutiveCapitals, numberOfWords);

        // Then
        Assert.assertEquals(0, ratio);
    }
}
