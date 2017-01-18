package org.serc.algorithm.controller.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;

public class AbstractDto {
    
    protected Long id;
    protected Date createdTime;
    protected Date updatedTime;
    
    public AbstractDto() {}
    
    public AbstractDto(Object object) {
        BeanUtils.copyProperties(object, this);
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
