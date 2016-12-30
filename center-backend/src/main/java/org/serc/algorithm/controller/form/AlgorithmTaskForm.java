package org.serc.algorithm.controller.form;

import javax.validation.constraints.NotNull;

public class AlgorithmTaskForm {
    
    @NotNull
    public String algorithm;
    
    @NotNull
    public String input;
    
    public Long parentTask;

}
