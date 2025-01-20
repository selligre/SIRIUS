package fimafeng.back.proto_back.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "location")
public class Location {

    @Id
    @Column(name="location_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLocation;

    @Column(name = "name")
    private String name;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "ref_district")
    private int ref_district;

    public Long getIdLocation() {return idLocation;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public double getLongitude() {return longitude;}

    public void setLongitude(double longitude) {this.longitude = longitude;}

    public double getLatitude() {return latitude;}

    public void setLatitude(double latitude) {this.latitude = latitude;}

    public int getRef_district() {return ref_district;}
}