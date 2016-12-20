package org.serc.algorithm.model;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.serc.model.AbstractEntity;

@Entity
@Table(name = "algorithms")
public class Algorithm extends AbstractEntity {
    
    private String name;
    private String image;
    @Lob String description;
    @ManyToOne ResultType inputType;
    @ManyToOne ResultType outputType;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ResultType getInputType() {
        return inputType;
    }

    public void setInputType(ResultType inputType) {
        this.inputType = inputType;
    }

    public ResultType getOutputType() {
        return outputType;
    }

    public void setOutputType(ResultType outputType) {
        this.outputType = outputType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
