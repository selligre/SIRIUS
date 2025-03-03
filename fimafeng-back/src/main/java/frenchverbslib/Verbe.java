package frenchverbslib;

import java.io.FileNotFoundException;
import java.io.IOException;
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
	private Iterator<Element> verbesIterator;
	private static List<Element> conjugaison;
	private Iterator<Element> conjugaisonIterator;

	private Element VerbEle;
	private String radical;

	private static Map<String,String> templates;
	private static Map<String,Element> conjugaisonTemplates;


	private static Logger LOGGER = Logger.getLogger(Verbe.class.getName());

	public Verbe () {
		try {
			verbes = ChargerFichier("/"+ModerationConfiguration.VERBS_FILE);
			conjugaison = ChargerFichier("/"+ModerationConfiguration.CONJUGAISONS_FILE);
			templates = new HashMap<>();
			conjugaisonTemplates = new HashMap<>();
			generateTemplates(verbes);
			generatesConjugaisonsTemplates(conjugaison);
		} catch(Exception e) {
			e.printStackTrace();
		}
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
		conjugaisonIterator = conjugaison.listIterator();
		while (conjugaisonIterator.hasNext()) {
			Element verbe = conjugaisonIterator.next();
			String att = verbe.getAttributeValue("name");
			if(att != null) {
				conjugaisonTemplates.put(att, verbe);
			}
		}

	}

	private Element getElemConjugaison(final String verb)
	{
		LOGGER.info("getElemConjugaison: "+verb);
		//String template = getTemplate(verb);
		String template = templates.get(verb);
		LOGGER.info("verb: "+verb+", template: "+template);
		if(template != null)
		{
			Element result = null;

			while(conjugaisonIterator.hasNext() && result == null)
			{
				Element verbe = conjugaisonIterator.next();
				String att = verbe.getAttributeValue("name");
				if(att.equals(template))
				{
					result = verbe;
				}
			}
			LOGGER.info("getElemConjugaison END: "+verb+", res: "+result);
			return result;
		}
		else
		{
			LOGGER.info("getElemConjugaison END: null");
			return null;
		}
	}

	/**
	 * Fonction cherchant la forme <t> du verbe <i> donné
	 * @param infinitivVerb verbe à l'infinitif, semblable à "abaisser" dans verbes.xml
	 * @return forme du verbe, semblable à aim:er
	 */
	@SuppressWarnings("unused")
	private String getTemplate(final String infinitivVerb)
	{
		LOGGER.info("Looking for: "+infinitivVerb);
		String groupe = null;
		while(verbesIterator.hasNext() && groupe == null)
		{
			Element verbe = verbesIterator.next();
			String verbeinf = verbe.getChildText("i");
			if(verbeinf.equals(infinitivVerb))
			{
				groupe = verbe.getChildText("t");
			}
		}
		if(groupe == null) {
			LOGGER.warning("NOTHING FOUND!");
		} else {
			LOGGER.warning("FOUND: "+groupe);
		}
		return groupe;
	}

	private String initConjugue(String verbe)
	{
		LOGGER.info("initConjugue: "+verbe);
		verbesIterator = verbes.listIterator();
		conjugaisonIterator = conjugaison.listIterator();
		VerbEle = getElemConjugaison(verbe);
		if(VerbEle != null)
		{
			Element term = VerbEle.getChild("infinitif");
			String EndOfVerb = term.getChild("present").getChild("p").getChild("i").getText();
			int radpos = verbe.lastIndexOf(EndOfVerb);
			radical = verbe.substring(0, radpos);
			LOGGER.info("initConjugue END: "+verbe);
			return "parfait";
		}
		else
		{
			LOGGER.info("initConjugue END: null");
			return null;
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
		LOGGER.info("verb: "+verb+", terminaison: "+terminaison+", radpos: "+radpos);
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

	/**
	 * Conjuguer un verbe
	 * @param verbe le verbe à conjuguer
	 * @param mode le mode de la conjugaison
	 * @param temps le temps de la conjugaison
	 * @return la liste du verbe conjugué ou null si le verbe n'existe pas dans le dictionnaire
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<String> conjuguer(final String verbe, final ModeEnum mode, final TempsEnum temps)
	{
		if(initConjugue(verbe) != null)
		{
			String template = templates.get(verbe);
			LOGGER.info("Template de ["+verbe+"]: "+template);

			ArrayList<String> verbeConjugue = new ArrayList<String>();
			// TODO : pb ici (GetChildren)
			LOGGER.info("Does "+template+" in map? (expected true): "+conjugaisonTemplates.containsKey(template));
			Element templateElem = conjugaisonTemplates.get(template);
			LOGGER.info("templateElem: "+templateElem);
			Element modeElem = templateElem.getChild(mode.getValue());
			LOGGER.info("modeElem: "+modeElem);
			for (Object attribut : modeElem.getChildren()) {
				LOGGER.info("attribut: "+attribut);
			}
			Element tempsElem = modeElem.getChild(temps.getValue());
			LOGGER.info("tempsElem: "+tempsElem);
			List<Element> conjigaison = tempsElem.getChildren();
			//List<Element> conjigaison = conjugaisonTemplates.get(verbe).getChild(mode.getValue()).getChild(temps.getValue()).getChildren();
			//List<Element> conjigaison = VerbEle.getChild(mode.getValue()).getChild(temps.getValue()).getChildren();
			LOGGER.info("conjigaison: "+ conjigaison);
			// Element elMode = VerbEle.getChild(mode.getValue());
			// LOGGER.info("mode: "+elMode.toString());
			// Element elTemps = elMode.getChild(temps.getValue());
			// LOGGER.info("temps: "+elTemps.toString());

			Iterator<Element> it = conjigaison.iterator();
			while(it.hasNext())
			{
				Element item = (Element)it.next();
				String ter = item.getChildText("i");
				verbeConjugue.add(radical + ter);
			}
			return verbeConjugue;
		}
		else
		{
			return null;
		}
	}

	/**
	 * Conjuguer un verbe
	 * @param verbe le verbe � conjuguer
	 * @param mode le mode de la conjugaison
	 * @param temps le temps de la conjugaison
	 * @param pronom la personne � conjuguer
	 * @return le verbe conjugu� ou null si le verbe n'existe pas dans le dictionnaire
	 */
	@SuppressWarnings("unchecked")
	public String conjuguer(final String verbe, final ModeEnum mode, final TempsEnum temps, final PronomEnum pronom)
	{
		if(initConjugue(verbe) != null)
		{
			String verbeConjugue = null;
			List<Element> conjigaison = VerbEle.getChild(mode.getValue()).getChild(temps.getValue()).getChildren();
			Iterator<Element> it = conjigaison.iterator();
			int i = 0;
			while(it.hasNext() && verbeConjugue == null)
			{
				Element item = (Element)it.next();
				if(pronom.getValue() == i)
				{
					String ter = item.getChildText("i");
					verbeConjugue = radical + ter;
				}
				i++;
			}
			return verbeConjugue;
		}
		else
		{
			return null;
		}
	}

	public ArrayList<String> getListeVerbes()
	{
		verbesIterator = verbes.listIterator();
		ArrayList<String> listeVerbes = new ArrayList<String>();
		while(verbesIterator.hasNext())
		{
			listeVerbes.add(verbesIterator.next().getChildText("i"));
		}
		return listeVerbes;
	}
}
