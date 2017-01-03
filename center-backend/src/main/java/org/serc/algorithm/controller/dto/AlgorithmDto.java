package org.serc.algorithm.controller.dto;

import org.serc.algorithm.model.Algorithm;
import org.springframework.beans.BeanUtils;


public class AlgorithmDto extends AbstractDto {
    
    private String name;
    private String image;
    private Boolean imageExist = false;
    private String inputType;
    private String outputType;
    private String description;
    
    public AlgorithmDto() {
    }
    
    public AlgorithmDto(Algorithm algorithm) {
        BeanUtils.copyProperties(algorithm, this);
        this.inputType = algorithm.getInputType().getName();
        this.outputType = algorithm.getOutputType().getName();
    }

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

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public String getOutputType() {
        return outputType;
    }

    public void setOutputType(String outputType) {
        this.outputType = outputType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getImageExist() {
        return imageExist;
    }

    public void setImageExist(Boolean imageExist) {
        this.imageExist = imageExist;
    }

}
