package fimafeng.back.fimafeng_back.implementations.profiles;

import java.util.List;

public class ClientProfile {
    private int id;
    private int districtId;
    private List<Integer> tagIds;
    private List<Integer> consultationIds;

    public ClientProfile(int id, int district, List<Integer> tags, List<Integer> consultations) {
        this.id = id;
        this.districtId = district;
        this.tagIds = tags;
        this.consultationIds = consultations;
    }

    @Override
    public String toString() {
        return "ClientProfile{" +
                "id=" + id +
                ", districtId=" + districtId +
                ", tagIds=" + tagIds +
                ", consultationIds=" + consultationIds +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public List<Integer> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<Integer> tagIds) {
        this.tagIds = tagIds;
    }

    public List<Integer> getConsultationIds() {
        return consultationIds;
    }

    public void setConsultationIds(List<Integer> consultationIds) {
        this.consultationIds = consultationIds;
    }
}
