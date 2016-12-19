package org.serc.algorithm.controller.form;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class AlgorithmForm {
    
    @NotEmpty
    private String name;
    
    @NotEmpty
    private String image;
    
    @NotNull
    private Long inputType;
    
    @NotNull
    private Long outputType;
    
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

    public Long getInputType() {
        return inputType;
    }

    public void setInputType(Long inputType) {
        this.inputType = inputType;
    }

    public Long getOutputType() {
        return outputType;
    }

    public void setOutputType(Long outputType) {
        this.outputType = outputType;
    }

}
