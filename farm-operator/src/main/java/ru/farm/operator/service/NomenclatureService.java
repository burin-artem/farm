package ru.farm.operator.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.farm.common.dao.NomenclatureDao;
import ru.farm.common.entity.Nomenclature;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "nomenclatureService")
@ApplicationScoped
public class NomenclatureService {

    @Autowired
    NomenclatureDao nomenclatureDao;

    public NomenclatureDao getNomenclatureDao() {
        return nomenclatureDao;
    }

    public void addNomenclature(Nomenclature nomenclature) {
        nomenclatureDao.add(nomenclature);
    }

    public void editNomenclature(Nomenclature nomenclature) {
        nomenclatureDao.edit(nomenclature);
    }

    public void delNomenclature(Nomenclature nomenclature) {
        nomenclatureDao.del(nomenclature);
    }

}
