package xr.tsa.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Ad {

    private @Id Long creativeID;
    private Long adID;
    private Long camgaignID;
    private Long advertiserID;
    private Long appID;
    private Long appPlatform;
    
    public Ad() {
        super();
    }
    public Ad(String creativeID,String adID,String camgaignID,String advertiserID,String appID,String appPlatform) {
        this.creativeID = Long.parseLong(creativeID);
        this.adID = Long.parseLong(adID);
        this.camgaignID = Long.parseLong(camgaignID);
        this.advertiserID = Long.parseLong(advertiserID);
        this.appID = Long.parseLong(appID);
        this.appPlatform = Long.parseLong(appPlatform);
    }
    public Long getAdID() {
        return adID;
    }
    public void setAdID(Long adID) {
        this.adID = adID;
    }
    public Long getCreativeID() {
        return creativeID;
    }
    public void setCreativeID(Long creativeID) {
        this.creativeID = creativeID;
    }
    public Long getCamgaignID() {
        return camgaignID;
    }
    public void setCamgaignID(Long camgaignID) {
        this.camgaignID = camgaignID;
    }
    public Long getAdvertiserID() {
        return advertiserID;
    }
    public void setAdvertiserID(Long advertiserID) {
        this.advertiserID = advertiserID;
    }
    public Long getAppID() {
        return appID;
    }
    public void setAppID(Long appID) {
        this.appID = appID;
    }
    public Long getAppPlatform() {
        return appPlatform;
    }
    public void setAppPlatform(Long appPlatform) {
        this.appPlatform = appPlatform;
    }

}
