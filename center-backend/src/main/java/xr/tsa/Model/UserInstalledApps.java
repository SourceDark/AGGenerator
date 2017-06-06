package xr.tsa.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UserInstalledApps {
    
    @Id
    @GeneratedValue
    private Long id;
    private Long userID;
    private Long appID;
    public UserInstalledApps() {
        super();
    }
    public UserInstalledApps(String userID,String appID) {
        this.userID = Long.parseLong(userID);
        this.appID = Long.parseLong(appID);
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getUserID() {
        return userID;
    }
    public void setUserID(Long userID) {
        this.userID = userID;
    }
    public Long getAppID() {
        return appID;
    }
    public void setAppID(Long appID) {
        this.appID = appID;
    }

}
