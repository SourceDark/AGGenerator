package org.serc.workbench.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.serc.model.AbstractEntity;

@Entity
public class Workbench extends AbstractEntity {
    
    @ManyToMany
    @JoinTable(name="workbench_sensors")
    List<Sensor> sensors;
    
    @ManyToMany
    @JoinTable(name="workbench_hacls")
    List<Hacl> hacls;

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    public List<Hacl> getHacls() {
        return hacls;
    }

    public void setHacls(List<Hacl> hacls) {
        this.hacls = hacls;
    }

}
