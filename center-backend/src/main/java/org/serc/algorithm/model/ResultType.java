package org.serc.algorithm.model;

import javax.persistence.Entity;

import org.serc.model.AbstractEntity;

@Entity
public class ResultType extends AbstractEntity {
    
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    

}
