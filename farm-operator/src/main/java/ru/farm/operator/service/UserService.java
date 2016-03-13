package ru.farm.operator.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.farm.common.dao.NomenclatureDao;
import ru.farm.common.dao.UserDao;
import ru.farm.common.entity.Nomenclature;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "userService")
@ApplicationScoped
public class UserService {

    @Autowired
    UserDao userDao;

    public Boolean login(String username, String password) {
        return userDao.login(username, password);
    }

    public UserDao getUserDao() {
        return userDao;
    }
}
