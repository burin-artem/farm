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

    private List<Nomenclature> nomenclatureList;

    private Long id;
    private String name;
    private String description;


    @ManagedProperty("#{nomenclatureService}")
    private NomenclatureService service;

    @PostConstruct
    public void init() {
        if (service.getNomenclatureDao()!=null) {
            nomenclatureList = service.getNomenclatureDao().getNomenclatureList();
        }
    }

    public List<Nomenclature> getNomenclatureList() {
        return nomenclatureList;
    }

    public void setService(NomenclatureService service) {
        this.service = service;
    }

    public void addNomenclature() {
        Nomenclature nomenclature = new Nomenclature(null, getName(), getDescription());
        service.addNomenclature(nomenclature);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }



}
