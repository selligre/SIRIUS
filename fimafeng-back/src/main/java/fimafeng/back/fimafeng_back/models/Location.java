package fimafeng.back.fimafeng_back.models;

import jakarta.persistence.*;

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

    public void setIdLocation(Long idLocation) {this.idLocation = idLocation;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public double getLongitude() {return longitude;}

    public void setLongitude(double longitude) {this.longitude = longitude;}

    public double getLatitude() {return latitude;}

    public void setLatitude(double latitude) {this.latitude = latitude;}

    public void setRef_district(int ref_district) {this.ref_district = ref_district;}

    public int getRef_district() {return ref_district;}
}