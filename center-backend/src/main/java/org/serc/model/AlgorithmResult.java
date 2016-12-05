package org.serc.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "algorithm_results")
public class AlgorithmResult extends AbstractEntity {
    
    @ManyToOne
    @JoinColumn(name = "algorithm_id")
    private Algorithm algorithm;
    
    private String content;

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
