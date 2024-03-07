package edu.cgl.sirius.business.dto.AnnounceTypes;

import java.util.Date;

import edu.cgl.sirius.business.dto.Announce;

public class Activity extends Announce {

    private int slotsNumber;
    private int slotAvailable;
    private float price;

    public Activity(String status, String title, String desc, Date start, float duration, Date end,
            Boolean recc, int slots, float price) {
        super(status, "activity", title, desc, start, duration, end, recc);
        this.slotsNumber = slots;
        this.slotAvailable = 0;
        this.price = price;
    }

    @Override
    public String toString() {
        return super.toString() + ", sN: " + this.slotsNumber + ", sA: " + this.slotAvailable + ", p: " + this.price;
    }

    public String getInsertRequest() {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "INSERT INTO announce (ref_author_id, publication_date, status, type, title, description, date_time_start, duration, date_time_end, is_recurrent) VALUES");
        sb.append(" (1,");
        sb.append("'" + super.getPublicationDateString() + "',");
        sb.append("'" + super.status + "',");
        sb.append("'" + super.type + "',");
        sb.append("'" + super.title + "',");
        sb.append("'" + super.description + "',");
        sb.append("'" + super.getDateTimeStartString() + "',");
        sb.append("'" + super.duration + "',");
        sb.append("'" + super.getDateTimeEndString() + "',");
        sb.append(super.isRecurrent.toString().toUpperCase() + "); \n");

        sb.append("INSERT INTO activity (ref_announce_id, number_of_slots, slots_available, price) VALUES");
        sb.append(" (?" + ",");
        sb.append(this.slotsNumber + ",");
        sb.append(this.slotAvailable + ",");
        sb.append(this.price + ");");

        return sb.toString();
    }
}
