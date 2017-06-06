package xr.tsa.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AppCategories {
    
    private @Id Long appID;
    private Long appCategory;
    
    public AppCategories() {
        super();
    }
    public AppCategories(String appID, String appCategory) {
        super();
        this.appID = Long.parseLong(appID);
        this.appCategory = Long.parseLong(appCategory);
    }
    public Long getAppID() {
        return appID;
    }
    public void setAppID(Long appID) {
        this.appID = appID;
    }
    public Long getAppCategory() {
        return appCategory;
    }
    public void setAppCategory(Long appCategory) {
        this.appCategory = appCategory;
    }

}
