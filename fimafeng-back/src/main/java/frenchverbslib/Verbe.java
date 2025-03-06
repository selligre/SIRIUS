package frenchverbslib;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.logging.Logger;

import fimafeng.back.fimafeng_back.implementations.moderation.ModerationConfiguration;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class Verbe
{
	private static List<Element> verbes;
	private static List<Element> conjugaison;

	private static Map<String,String> templates;
	private static Map<String,Element> conjugaisonTemplates;

	private static final Logger LOGGER = Logger.getLogger(Verbe.class.getName());

	// Took ~3min05s that way
	public Verbe () {
		try {
			verbes = ChargerFichier("/"+ModerationConfiguration.VERBS_FILE);
			conjugaison = ChargerFichier("/"+ModerationConfiguration.CONJUGAISONS_FILE);
			templates = new HashMap<>();
			conjugaisonTemplates = new HashMap<>();
			generateTemplates(verbes);
			generatesConjugaisonsTemplates(conjugaison);
		} catch(Exception e) {
			LOGGER.severe("Exception: "+e.getLocalizedMessage());
		}
	}

	// Took ~2min35s that way (best so far)
	public Verbe(InputStream isVerbs, InputStream isConjugaison) {
		verbes = chargerStream(isVerbs);
		conjugaison = chargerStream(isConjugaison);
		templates = new HashMap<>();
		conjugaisonTemplates = new HashMap<>();
		generateTemplates(verbes);
		generatesConjugaisonsTemplates(conjugaison);
	}

	private List<Element> chargerStream(InputStream stream) {
		List<Element> verbes = null;
		SAXBuilder sxb = new SAXBuilder();
        try {
            Document doc = sxb.build(stream);
			Element root = doc.getRootElement();
			verbes = root.getChildren();
        } catch (JDOMException e) {
			LOGGER.severe("JDOMException: "+e.getLocalizedMessage());
        } catch (IOException e) {
			LOGGER.severe("IOException: "+e.getLocalizedMessage());
        }
		return verbes;
    }

	@SuppressWarnings("unchecked")
	private List<Element> ChargerFichier(String fichier) {
		List<Element> res = null;
		try {
			SAXBuilder sxb = new SAXBuilder();
			Document document = sxb.build(getClass().getResourceAsStream(fichier));
			Element racine = document.getRootElement();
			res = racine.getChildren();
		} catch (FileNotFoundException e) {
			LOGGER.severe("FileNotFoundException: "+e.getLocalizedMessage());
		} catch (JDOMException e) {
			LOGGER.severe("JDOMException: "+e.getLocalizedMessage());
		} catch (IOException e) {
			LOGGER.severe("IOException: "+e.getLocalizedMessage());
		}
		if(res == null) {
			LOGGER.severe("Root is null! File probably not found.");
		}
		return res;
	}

	private void generateTemplates(List<Element> v) {
		for (Element verbe : v) {
			String verbeinf = verbe.getChildText("i");
			String groupe = verbe.getChildText("t");
			templates.put(verbeinf, groupe);
		}
	}

	private void generatesConjugaisonsTemplates(List<Element> c) {
		for (Element template : c) {
			String att = template.getAttributeValue("name");
			if (att != null) {
				conjugaisonTemplates.put(att, template);
			}
		}
	}

	/**
	 * Conjugue un verbe à tous les temps disponibles dans le fichier de conjugaison, et à toutes les personnes
	 * @param verb le verbe à conjuguer
	 * @return la liste du verbe conjugué ou null si le verbe n'existe pas dans le dictionnaire
	 */
	public ArrayList<String> conjuguerToutMode(final String verb) {
		if (!templates.containsKey(verb)) {
			return null;
		}
		String templateName = templates.get(verb);
		if (!conjugaisonTemplates.containsKey(templateName)) {
			return null;
		}

		// Creating verb's radical form
		Element templateElement = conjugaisonTemplates.get(templateName);
		String terminaison = templateElement.getChild(ModeEnum.INFINITIF.getValue()).getChild(TempsEnum.PRESENT.getValue())
				.getChild("p").getChild("i").getText();
		int radpos = verb.lastIndexOf(terminaison);
		LOGGER.fine("verb: "+verb+", terminaison: "+terminaison+", radpos: "+radpos);
		String radicalVerb = verb.substring(0, radpos);

		ArrayList<String> conjugaisons = new ArrayList<>();
		List<Element> modes = conjugaisonTemplates.get(templateName).getChildren();
		for (Element mode : modes) {
			List<Element> temps = mode.getChildren();
			for (Element temp : temps) {
				List<Element> conjs = temp.getChildren();
				for (Element conj : conjs) {
					String term = conj.getChildText("i");
					conjugaisons.add(radicalVerb+term);
				}
			}
		}
		return conjugaisons;
	}

}
