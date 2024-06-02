package edu.cgl.sirius.business;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import edu.cgl.sirius.business.dto.Locations;

public class AnnounceParser {

    private Map<String, String> locations;

    public void updateLocations(Locations loc) {
        this.locations = loc.getLocationsMap();
    }

    public String parseLocation(String location) {
        return locations.get(location);
    }

    public Map<String, String> getParsedLocations() {
        return locations;
    }

    public String parseDateTime(String dateTime) {
        if (dateTime.equals(" "))
            return "Indéfini";
        LocalDateTime ldt = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
        DateTimeFormatter dft = DateTimeFormatter.ofPattern("'Le' dd/MM/yyyy 'à' HH:mm");
        return dft.format(ldt);
    }

    public String parseDuration(String duration) {
        if (duration.equals(" "))
            return "Indéfini";
        double valueDouble = Double.parseDouble(duration);
        valueDouble = valueDouble * 60;
        long valueMinuts = (long) valueDouble;
        LocalTime ltime = LocalTime.of(0, 0, 0).plusMinutes(valueMinuts);

        DateTimeFormatter dtf;
        if (ltime.getHour() > 9) {
            dtf = DateTimeFormatter.ofPattern("kk'h'mm");
        } else if (ltime.getHour() > 0) {
            dtf = DateTimeFormatter.ofPattern("k'h'mm");
        } else {
            dtf = DateTimeFormatter.ofPattern("mm'min'");
        }

        return dtf.format(ltime);
    }

    public String parsePrice(String price) {
        if (price.equals(" ")) {
            return "Indéfini";
        }
        double amount = Double.valueOf(price);
        if (amount == 0.0) {
            return "Gratuit";
        } else {
            DecimalFormat df = new DecimalFormat("0.00'€'");
            return df.format(amount);
        }
    }

    public String parseSlots(String slots) {
        if (slots.equals(" ")) {
            return "Indéfini";
        }
        int amount = Integer.valueOf(slots);
        if (amount == 0) {
            return "Gratuit";
        } else {
            return String.valueOf(amount);
        }
    }

}
