package fimafeng.back.fimafeng_back.implementations.profiles;

import java.util.List;

public class AnnounceProfile {
    private int id;
    private int districtId;
    private List<Integer> tagIds;

    public AnnounceProfile(int id, int district, List<Integer> tags) {
        this.id = id;
        this.districtId = district;
        this.tagIds = tags;
    }

    @Override
    public String toString() {
        return "AnnounceProfile{" +
                "id=" + id +
                ", districtId=" + districtId +
                ", tagIds=" + tagIds +
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
}
