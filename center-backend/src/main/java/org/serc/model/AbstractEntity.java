package org.serc.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

@MappedSuperclass
public class AbstractEntity {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @Column(name = "created_at")
    private Date createdTime;
    
    @Column(name = "updated_at")
    private Date updatedTime;
    
    @PrePersist
    public void init() {
        if(createdTime == null) {
            createdTime = new Date();
        }
        updatedTime = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }
    

}
