package de.leanix.persistance.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Subtask {

    private String name;
    private String description;

    public Subtask() {
    }

    public Subtask(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
