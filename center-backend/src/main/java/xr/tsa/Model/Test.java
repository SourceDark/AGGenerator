package xr.tsa.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Test {
    
    private @Id Long instanceID;
    private Long label;
    private Long clickTime;
    private Long creativeID;
    private Long userID;
    private Long positionID;
    private Long connectionType;
    private Long telecomsOperator;
    public Test() {
        super();
    }
    public Test(String instanceID,String label,String clickTime,String creativeID,String userID,String positionID,String connectionType,String telecomsOperator) {
        this.instanceID = Long.parseLong(instanceID);
        this.label = Long.parseLong(label);
        this.clickTime = Long.parseLong(clickTime);
        this.creativeID = Long.parseLong(creativeID);
        this.userID = Long.parseLong(userID);
        this.positionID = Long.parseLong(positionID);
        this.connectionType = Long.parseLong(connectionType);
        this.telecomsOperator = Long.parseLong(telecomsOperator);
    }
    public Long getInstanceID() {
        return instanceID;
    }
    public void setInstanceID(Long instanceID) {
        this.instanceID = instanceID;
    }
    public Long getLabel() {
        return label;
    }
    public void setLabel(Long label) {
        this.label = label;
    }
    public Long getClickTime() {
        return clickTime;
    }
    public void setClickTime(Long clickTime) {
        this.clickTime = clickTime;
    }
    public Long getCreativeID() {
        return creativeID;
    }
    public void setCreativeID(Long creativeID) {
        this.creativeID = creativeID;
    }
    public Long getUserID() {
        return userID;
    }
    public void setUserID(Long userID) {
        this.userID = userID;
    }
    public Long getPositionID() {
        return positionID;
    }
    public void setPositionID(Long positionID) {
        this.positionID = positionID;
    }
    public Long getConnectionType() {
        return connectionType;
    }
    public void setConnectionType(Long connectionType) {
        this.connectionType = connectionType;
    }
    public Long getTelecomsOperator() {
        return telecomsOperator;
    }
    public void setTelecomsOperator(Long telecomsOperator) {
        this.telecomsOperator = telecomsOperator;
    }

}
