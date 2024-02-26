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
}
