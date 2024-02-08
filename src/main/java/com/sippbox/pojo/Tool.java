package com.sippbox.pojo;

import com.sippbox.enums.ToolCategory;

public class Tool {
    private String name;
    private String description;
    private String link;
    private ToolCategory toolCategory;

    public Tool(String name, String description, String link, ToolCategory toolCategory) {
        this.name = name;
        this.description = description;
        this.link = link;
        this.toolCategory = toolCategory;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public ToolCategory getCategory() { return toolCategory; }

    public void setCategory(ToolCategory toolCategory) { this.toolCategory = toolCategory; }
}
