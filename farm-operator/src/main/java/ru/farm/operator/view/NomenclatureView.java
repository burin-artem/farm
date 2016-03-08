package ru.farm.operator.view;

import ru.farm.common.entity.Nomenclature;
import ru.farm.operator.service.NomenclatureService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name="dtNomenclatureView")
@ViewScoped
public class NomenclatureView implements Serializable {

    private List<Nomenclature> nomenclatures;
    private List<Nomenclature> filteredNomenclatures;

    private Long id;
    private String name;
    private String volumeUnit;
    private String parsingNames;
    private String comment;

    @ManagedProperty("#{nomenclatureService}")
    private NomenclatureService service;

    @PostConstruct
    public void init() {
        if (service.getNomenclatureDao()!=null) {
            nomenclatures = service.getNomenclatureDao().getNomenclatureList();
        }
    }

    public List<Nomenclature> getNomenclatures() {
        return nomenclatures;
    }

    public void setService(NomenclatureService service) {
        this.service = service;
    }

    public void addNomenclature() {
        Nomenclature nomenclature = new Nomenclature(null, getName(), getVolumeUnit(), getParsingNames(), getComment());
        service.addNomenclature(nomenclature);
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

    public List<Nomenclature> getFilteredNomenclatures() {
        return filteredNomenclatures;
    }

    public void setFilteredNomenclatures(List<Nomenclature> filteredNomenclatures) {
        this.filteredNomenclatures = filteredNomenclatures;
    }
}
