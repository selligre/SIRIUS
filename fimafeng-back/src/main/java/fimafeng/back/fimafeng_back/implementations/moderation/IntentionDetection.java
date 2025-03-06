package fimafeng.back.fimafeng_back.implementations.moderation;

import fimafeng.back.fimafeng_back.models.enums.ModerationReason;

import java.util.ArrayList;
import java.util.logging.Logger;

public class IntentionDetection {

    static Logger LOGGER = Logger.getLogger(IntentionDetection.class.getName());

    public static ModerationReason detect(ArrayList<String> message) {
        ModerationReason reason = ModerationReason.INTENTION_OK;
        for (int i = 0; i < message.size()-1; i++) {
            String expression = message.get(i)+" "+message.get(i+1);
            expression = expression.toLowerCase();

            switch (expression) {
                // hate
                case "retourner pays":
                case "sale envahisseur":
                case "sales envahisseurs":
                case "race inferieure":
                case "races inferieures":
                case "sale blanc":
                case "sales blancs":
                case "sale merde":
                case "sales merdes":
                case "sales normands":
                case "vouloir convertir":
                case "guerre sainte":
                case "contre nature":
                case "changer sexe":
                case "faut exterminer":
                case "devoir bruler":
                case "etre voleurs":
                case "etre violents":
                    LOGGER.info(expression+": "+ModerationReason.HATE);
                    reason = ModerationReason.HATE;
                    break;
                // Violence
                case "aller tuer":
                case "casser gueule":
                case "faire mal":
                case "aller detruire":
                case "faire payer":
                case "aller regretter":
                case "avoir problemes":
                case "aller exploser":
                case "chercher arme":
                case "faire exploser":
                case "aller decouper":
                case "aller tabasser":
                case "aller torturer":
                case "aller violer":
                case "aller noyer":
                case "aller bruler":
                case "passer action":
                case "preparer coup":
                    LOGGER.info(expression+": "+ModerationReason.VIOLENCE);
                    reason = ModerationReason.VIOLENCE;
                    break;
                // Pornography
                case "contenu adultes":
                case "films x":
                case "videos chaudes":
                case "photos denudees":
                case "cam girl":
                case "contenu exclusif":
                case "contenu coquin":
                case "images mineurs":
                case "photos jeunes":
                case "plan cul":
                case "rencontre coquine":
                case "chercher partenaire":
                case "soiree libertine":
                    LOGGER.info(expression+": "+ModerationReason.PORNOGRAPHY);
                    reason = ModerationReason.PORNOGRAPHY;
                    break;
            }
        }
        return reason;
    }
}
