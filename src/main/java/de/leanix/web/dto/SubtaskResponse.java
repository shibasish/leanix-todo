package de.leanix.web.dto;

import java.util.Optional;

public class SubtaskResponse {

    private String name;
    private Optional<String> description;

    public SubtaskResponse() {
    }

    public SubtaskResponse(String name, Optional<String> description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Optional<String> getDescription() {
        return description;
    }

    public void setDescription(Optional<String> description) {
        this.description = description;
    }
}
