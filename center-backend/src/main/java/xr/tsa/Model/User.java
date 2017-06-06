package xr.tsa.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
    
    private @Id Long userID;
    private Long age;
    private Long gender;
    private Long education;
    private Long marriageStatus;
    private Long haveBaby;
    private Long hometown;
    private Long residence;
    private Integer falseCount;
    private Integer trueCount;
    public User() {
        super();
    }
    public User(String userID,String age,String gender,String education,String marriageStatus,String haveBaby,String hometown,String residence) {
        this.userID = Long.parseLong(userID);
        this.age = Long.parseLong(age);
        this.gender = Long.parseLong(gender);
        this.education = Long.parseLong(education);
        this.marriageStatus = Long.parseLong(marriageStatus);
        this.haveBaby = Long.parseLong(haveBaby);
        this.hometown = Long.parseLong(hometown);
        this.residence = Long.parseLong(residence);
    }
    public Long getUserID() {
        return userID;
    }
    public void setUserID(Long userID) {
        this.userID = userID;
    }
    public Long getAge() {
        return age;
    }
    public void setAge(Long age) {
        this.age = age;
    }
    public Long getGender() {
        return gender;
    }
    public void setGender(Long gender) {
        this.gender = gender;
    }
    public Long getEducation() {
        return education;
    }
    public void setEducation(Long education) {
        this.education = education;
    }
    public Long getMarriageStatus() {
        return marriageStatus;
    }
    public void setMarriageStatus(Long marriageStatus) {
        this.marriageStatus = marriageStatus;
    }
    public Long getHaveBaby() {
        return haveBaby;
    }
    public void setHaveBaby(Long haveBaby) {
        this.haveBaby = haveBaby;
    }
    public Long getHometown() {
        return hometown;
    }
    public void setHometown(Long hometown) {
        this.hometown = hometown;
    }
    public Long getResidence() {
        return residence;
    }
    public void setResidence(Long residence) {
        this.residence = residence;
    }
    public Integer getFalseCount() {
        return falseCount;
    }
    public void setFalseCount(Integer falseCount) {
        this.falseCount = falseCount;
    }
    public Integer getTrueCount() {
        return trueCount;
    }
    public void setTrueCount(Integer trueCount) {
        this.trueCount = trueCount;
    }

}
