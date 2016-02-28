package ru.farm.common.entity;

/**
 * Created by Администратор on 23.02.2016.
 */
public class Nomenclature {

    private Long id;
    private String name;
    private String description;

    public Nomenclature(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
