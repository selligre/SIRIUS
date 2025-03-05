package fimafeng.back.fimafeng_back.implementations.moderation;

import fimafeng.back.fimafeng_back.models.enums.ModerationReason;

import java.util.ArrayList;

public class IntentionDetection {
    public ModerationReason detect(ArrayList<String> message) {
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
                    return ModerationReason.HATE;
                // Violence
                case "aller tuer":
                case "casser gueule":
                case "faire mal":
                case "aller détruire":
                case "faire payer":
                case "aller regretter":
                case "avoir problèmes":
                case "aller exploser":
                case "chercher arme":
                case "faire exploser":
                case "aller découper":
                case "aller tabasser":
                case "aller torturer":
                case "aller violer":
                case "aller noyer":
                case "aller brûler":
                case "passer action":
                case "préparer coup":
                    return ModerationReason.VIOLENCE;
                // Pornography
                case "contenu adultes":
                case "films x":
                case "vidéos chaudes":
                case "photos dénudées":
                case "cam girl":
                case "contenu exclusif":
                case "contenu coquin":
                case "images mineurs":
                case "photos jeunes":
                case "plan cul":
                case "rencontre coquine":
                case "chercher partenaire":
                case "soirée libertine":
                    return ModerationReason.PORNOGRAPHY;
            }
        }
        return ModerationReason.HATE;
    }
}
