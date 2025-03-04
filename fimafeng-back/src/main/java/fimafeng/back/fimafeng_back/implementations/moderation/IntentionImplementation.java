package fimafeng.back.fimafeng_back.implementations.moderation;

import fimafeng.back.fimafeng_back.models.ModerationAnalysis;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import frenchverbslib.Verbe;
import frenchverbslib.ModeEnum;
import frenchverbslib.TempsEnum;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;
import java.util.logging.Logger;

@Service
public class IntentionImplementation {

    Logger LOGGER = Logger.getLogger(IntentionImplementation.class.getName());

    /**
     * We want to implement an algorithm that detect intention in text section
     * For that, we'll segment the algorithm into few steps
     *
     * 1. Text cleaning
     * A simple step that resume as removing any irrelevant words such as "le", "la", "les", etc.
     *
     * 2. Text simplifying
     * A more complex step to transform each recognized verb to their infinitive form, in order to reduce cases into more common ones.
     * For example, we want to transform "couru", "courront" into "courir"
     *
     * 3. Intention detection
     * Then, we'll 'simply' concatenate remaining words into intention:
     * "acheter" + "billet" will be recognized as "acheter un billet"
     *
     * 4. Moderation action
     * Finally, if the previous detected intention is 'negative',
     * we'll call our moderation service to moderate announce.
     * Otherwise (i.e. text section isn't recognized as 'negative'), we call our MS to authorized announce.
     */

    private static List<String> listIrrelevantWords = null;
    private static Map<String,String> conjugaisonMap = new HashMap<>();

    public IntentionImplementation() {
        // Load data from files if not already existing
        if (listIrrelevantWords == null) {
            LOGGER.info("Initializing listIrrelevantWords");
            try {
                listIrrelevantWords = new ArrayList<>();
                ClassPathResource resource = new ClassPathResource(ModerationConfiguration.CLEAR_IRRELEVANT_WORDS_FILE);
                File file = resource.getFile();
                listIrrelevantWords = Files.readAllLines(file.toPath());
            } catch (IOException e) {
                LOGGER.severe(e.getMessage());
            }
        }
        if (conjugaisonMap.isEmpty()) {
            LOGGER.info("Initializing conjugaisonMap");
            List<String> infinitivVerbsList = extractInfinitivVerbs(ModerationConfiguration.VERBS_FILE);
            generateConjugaisons(infinitivVerbsList);
        }

    }

    private List<String> extractInfinitivVerbs(String filePath){
        List<String> infinitivList = new ArrayList<>();
        // Duration calculation: https://docs.vultr.com/java/examples/calculate-difference-between-two-time-periods
        LocalTime start = LocalTime.now();
        // XML loading: https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder docBuilder = dbf.newDocumentBuilder();
            ClassPathResource resource = new ClassPathResource(filePath);
            File xmlFile = resource.getFile();
            Document xmlDoc = docBuilder.parse(xmlFile);

            Element root = xmlDoc.getDocumentElement();
            NodeList nl = root.getChildNodes();
            LOGGER.info("Loading " + nl.getLength() + " elements (~half verbs)");
            for (int i = 0; i < nl.getLength(); i++) {
                Node v = nl.item(i);
                if(v.getNodeType() == Node.ELEMENT_NODE) {
                    Element e = (Element) v;
                    infinitivList.add(e.getElementsByTagName("i").item(0).getTextContent());
                }
            }

        } catch (ParserConfigurationException | IOException | SAXException e) {
            LOGGER.severe(e.getMessage());
            throw new RuntimeException(e);
        }
        LOGGER.info("Final list size: "+infinitivList.size());
        LocalTime end = LocalTime.now();
        Duration duration = Duration.between(start, end);
        LOGGER.info("Took: "+duration.toHours()+"h"+duration.toMinutes()%60+"m"+duration.toSeconds()%60+"s"+duration.toMillis()%1000+"ms");
        return infinitivList;
    }

     private void generateConjugaisons(List<String> infinitivVerbsList) {
         ModeEnum[] modesList = ModeEnum.values();
         LOGGER.info("Modes: " + Arrays.toString(modesList));
         TempsEnum[] tempsList = TempsEnum.values();
         LOGGER.info("Temps: " + Arrays.toString(tempsList));

         ClassPathResource verbesResource = new ClassPathResource(ModerationConfiguration.VERBS_FILE);
         ClassPathResource conjugaisonResource = new ClassPathResource(ModerationConfiguration.CONJUGAISONS_FILE);

         LOGGER.info("Generating conjugaisons");
         LocalTime start = LocalTime.now();
         try {
             Verbe conjugueur = new Verbe(verbesResource.getInputStream(), conjugaisonResource.getInputStream());
             for (String verbe : infinitivVerbsList) {
                 List<String> conjugaisons = conjugueur.conjuguerToutMode(verbe);
                 if (conjugaisons != null) {
                     for (String conjugaison : conjugaisons) {
                         conjugaisonMap.put(conjugaison, verbe);
                     }
                 }
             }
         } catch (Exception e) {
             LOGGER.severe(e.getLocalizedMessage());
         }
         LocalTime end = LocalTime.now();
         Duration duration = Duration.between(start, end);
         LOGGER.info(conjugaisonMap.size() + " conjugaisons generated");
         LOGGER.info("Took: "+duration.toHours()+"h"+duration.toMinutes()%60+"m"+duration.toSeconds()%60+"s"+duration.toMillis()%1000+"ms");

     }

    /**
     * #1 Text cleaning:
     * Retrieving irrelevant words from given array if present in listIrrelevantWords
     * @param message list of words
     * @return the same words list but without its irrelevant words if found
     */
    private ArrayList<String> cleanText(ArrayList<String> message) {
        LOGGER.info("From: " + message);
        for (String word : message) {
            if(listIrrelevantWords.contains(word)) {
                message.remove(word);
            }
        }
        LOGGER.info("To: " + message);
        return message;
    }

    /**
     * #2 Text simplifying
     * Switching conjugated verbs to their infinitive form if conjugated form present in conjugaisonMap
     * @param message list of words
     * @return the same words list but with verbs to their infinitive form
     */
    private ArrayList<String> simplifyText(ArrayList<String> message) {
        LOGGER.info("From: " + message);
        for (int i = 0; i < message.size(); i++) {
            String word = message.get(i);
            if(conjugaisonMap.containsKey(word)) {
                message.set(i, conjugaisonMap.get(word));
            }
        }
        LOGGER.info("To: " + message);
        return message;
    }


    public void prepareAnalysis(ModerationAnalysis analysis) {
        LOGGER.info("Preparing analysis");
        LOGGER.info("Clearing...");
        analysis.setTitle(cleanText(analysis.getTitle()));
        analysis.setDescription(cleanText(analysis.getDescription()));
        LOGGER.info("Simplifying...");
        analysis.setTitle(simplifyText(analysis.getTitle()));
        analysis.setDescription(simplifyText(analysis.getDescription()));
    }

    public void detectIntention(ModerationAnalysis analysis) {
        LOGGER.info("Detecting intention");
    }



}
