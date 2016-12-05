package org.serc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "algorithms")
public class Algorithm extends AbstractEntity {
    
    private String name;
    private Long type;
    private String image;
    
    @Column(name = "control_image")
    private String controlImage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getControlImage() {
        return controlImage;
    }

    public void setControlImage(String controlImage) {
        this.controlImage = controlImage;
    }

}
