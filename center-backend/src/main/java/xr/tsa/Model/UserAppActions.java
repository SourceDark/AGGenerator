package xr.tsa.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UserAppActions {
    
    @Id
    @GeneratedValue
    private Long id;
    private Long userID;
    private Long installTime;
    private Long appID;
    public UserAppActions() {
        super();
    }
    public UserAppActions(String userID,String installTime,String appID) {
        this.userID = Long.parseLong(userID);
        this.installTime = Long.parseLong(installTime);
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
    public Long getInstallTime() {
        return installTime;
    }
    public void setInstallTime(Long installTime) {
        this.installTime = installTime;
    }
    public Long getAppID() {
        return appID;
    }
    public void setAppID(Long appID) {
        this.appID = appID;
    }

}
