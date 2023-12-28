package com.sippbox.beans;

public class Tool {
    private String name;
    private String description;
    private String link;
    private Category category;

    public Tool(String name, String description, String link, Category category) {
        this.name = name;
        this.description = description;
        this.link = link;
        this.category = category;
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

    public Category getCategory() { return category; }

    public void setCategory(Category category) { this.category = category; }
}
