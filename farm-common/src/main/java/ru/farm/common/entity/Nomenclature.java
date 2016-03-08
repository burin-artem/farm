package ru.farm.common.entity;

/**
 * Created by Администратор on 23.02.2016.
 */
public class Nomenclature {

    private Long id;
    private String name;
    private String volumeUnit;
    private String parsingNames;
    private String comment;

    public Nomenclature(Long id, String name, String volumeUnit, String parsingNames, String comment) {
        this.id = id;
        this.name = name;
        this.volumeUnit = volumeUnit;
        this.parsingNames = parsingNames;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVolumeUnit() {
        return volumeUnit;
    }

    public void setVolumeUnit(String volumeUnit) {
        this.volumeUnit = volumeUnit;
    }

    public String getParsingNames() {
        return parsingNames;
    }

    public void setParsingNames(String parsingNames) {
        this.parsingNames = parsingNames;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
