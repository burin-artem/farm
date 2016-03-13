package ru.farm.operator.view;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import ru.farm.common.entity.Nomenclature;
import ru.farm.operator.service.NomenclatureService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name="dtNomenclatureView")
@ViewScoped
public class NomenclatureView implements Serializable {

    private List<Nomenclature> nomenclatures;
    private List<Nomenclature> filteredNomenclatures;
    private Nomenclature selectedNomenclature;
    private List<Nomenclature> selectedNomenclatures;

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

    public List<Nomenclature> getNomenclatures()
    {
        return nomenclatures;
    }

    public void refreshNomenclatures() {
        this.init();
    }

    public void setService(NomenclatureService service) {
        this.service = service;
    }

    public void addNomenclature() {
        Nomenclature nomenclature = new Nomenclature(null, getName(), getVolumeUnit(), getParsingNames(), getComment());
        service.addNomenclature(nomenclature);
        reset();
    }

    public void editNomenclature() {
        Nomenclature nomenclature = new Nomenclature(selectedNomenclature.getId(), selectedNomenclature.getName(), selectedNomenclature.getVolumeUnit(), selectedNomenclature.getParsingNames(), selectedNomenclature.getComment());
        service.editNomenclature(nomenclature);
        reset();
    }

    public void delNomenclature() {
        Nomenclature nomenclature = new Nomenclature(selectedNomenclature.getId(), selectedNomenclature.getName(), selectedNomenclature.getVolumeUnit(), selectedNomenclature.getParsingNames(), selectedNomenclature.getComment());
        service.delNomenclature(nomenclature);
        reset();
    }

    public Nomenclature getSelectedNomenclature() {
        return selectedNomenclature;
    }

    public void setSelectedNomenclature(Nomenclature selectedNomenclature) {
        this.selectedNomenclature = selectedNomenclature;
    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Nomenclature Selected", ((Nomenclature) event.getObject()).getId().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("Nomenclature Unselected", ((Nomenclature) event.getObject()).getId().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void reset() {
        //refreshNomenclatures();
        selectedNomenclature = null;
        refreshNomenclatures();
        RequestContext.getCurrentInstance().reset("form:nomenclature");
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
