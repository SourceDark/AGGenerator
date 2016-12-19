package org.serc.algorithm.controller.dto;

import org.serc.model.AbstractEntity;
import org.springframework.beans.BeanUtils;

public class SimpleDto {
    
    private Long id;
    private String name;
    private String description;
    
    public SimpleDto() {
    }
    
    public SimpleDto(AbstractEntity entity) {
        BeanUtils.copyProperties(entity, this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
