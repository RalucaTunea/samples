package com.conduit.sample.services.entity;

import java.util.List;
import java.util.Map;

public class ExploreRequest {
    String name;
    String description;
    List<Map<String, Object>> tables;

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

    public List<Map<String, Object>> getTables() {
        return tables;
    }

    public void setTables(List<Map<String, Object>> tables) {
        this.tables = tables;
    }

    @Override
    public String toString() {
        return "ExploreRequest{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", tables=" + tables +
                '}';
    }
}
