package ru.farm.operator.view;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import ru.farm.common.entity.Nomenclature;
import ru.farm.operator.service.NomenclatureService;

@ManagedBean(name="dtNomenclatureSelectionView")
@ViewScoped
public class NomenclatureSelectionView implements Serializable {

    private List<Nomenclature> nomenclatures;
    private Nomenclature selectedNomenclature;
    private List<Nomenclature> selectedNomenclatures;

    @ManagedProperty("#{nomenclatureService}")
    private NomenclatureService service;

    @PostConstruct
    public void init() {
    }

    public void editNomenclature() {
        Nomenclature nomenclature = new Nomenclature(selectedNomenclature.getId(), selectedNomenclature.getName(), selectedNomenclature.getVolumeUnit(), selectedNomenclature.getParsingNames(), selectedNomenclature.getComment());
        service.editNomenclature(nomenclature);
    }

    public void delNomenclature() {
        Nomenclature nomenclature = new Nomenclature(selectedNomenclature.getId(), selectedNomenclature.getName(), selectedNomenclature.getVolumeUnit(), selectedNomenclature.getParsingNames(), selectedNomenclature.getComment());
        service.delNomenclature(nomenclature);
    }

    public List<Nomenclature> getNomenclatures() {
        return nomenclatures;
    }

    public void setService(NomenclatureService service) {
        this.service = service;
    }

    public Nomenclature getSelectedNomenclature() {
        return selectedNomenclature;
    }

    public void setSelectedNomenclature(Nomenclature selectedNomenclature) {
        this.selectedNomenclature = selectedNomenclature;
    }

    public List<Nomenclature> getSelectedNomenclatures() {
        return selectedNomenclatures;
    }

    public void setSelectedCars(List<Nomenclature> selectedNomenclatures) {
        this.selectedNomenclatures = selectedNomenclatures;
    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Nomenclature Selected", ((Nomenclature) event.getObject()).getId().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("Nomenclature Unselected", ((Nomenclature) event.getObject()).getId().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}