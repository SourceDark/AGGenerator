package xr.tsa.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Position {
    private @Id Long positionID;
    private Long sitesetID;
    private Long positionType;
    public Position() {
        super();
    }
    public Position(String positionID, String sitesetID, String positionType) {
        super();
        this.positionID = Long.parseLong(positionID);
        this.sitesetID = Long.parseLong(sitesetID);
        this.positionType = Long.parseLong(positionType);
    }
    public Long getPositionID() {
        return positionID;
    }
    public void setPositionID(Long positionID) {
        this.positionID = positionID;
    }
    public Long getSitesetID() {
        return sitesetID;
    }
    public void setSitesetID(Long sitesetID) {
        this.sitesetID = sitesetID;
    }
    public Long getPositionType() {
        return positionType;
    }
    public void setPositionType(Long positionType) {
        this.positionType = positionType;
    }
}
