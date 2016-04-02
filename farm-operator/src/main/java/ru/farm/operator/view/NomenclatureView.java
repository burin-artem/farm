package ru.farm.operator.view;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import ru.farm.common.dao.CommonDao;
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
import java.util.Map;

@ManagedBean(name="dtNomenclatureView")
@ViewScoped
public class NomenclatureView implements Serializable {

    private LazyDataModel<Nomenclature> nomenclatures;
    private Nomenclature selectedNomenclature;

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
            nomenclatures = new LazyDataModel<Nomenclature>() {
                private static final long serialVersionUID = 1L;
                @Override
                public List<Nomenclature> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                    CommonDao.SortOrder sortOrderOwn = CommonDao.SortOrder.values()[sortOrder.ordinal()];
                    List<Nomenclature> result = service.getNomenclatureDao().getNomenclatureList(first, pageSize, sortField, sortOrderOwn, filters);
                    nomenclatures.setRowCount(service.getNomenclatureDao().getNomenclatureCount(filters));
                    return result;
                }

                @Override
                public Nomenclature getRowData(String rowKey) {
                    for(Nomenclature nomenclature : nomenclatures) {
                        if(nomenclature.getId().toString().equals(rowKey))
                            return nomenclature;
                    }
                    return null;
                }

                @Override
                public Object getRowKey(Nomenclature nomenclature) {
                    return nomenclature.getId();
                }
            };
        }
    }

    public LazyDataModel<Nomenclature> getNomenclatures() {
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
}
