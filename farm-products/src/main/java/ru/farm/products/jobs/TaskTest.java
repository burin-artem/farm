package ru.farm.products.jobs;

import org.apache.log4j.Logger;
import ru.farm.common.dao.SampleDao;

/**
 * Created by Администратор on 15.12.2015.
 */
public class TaskTest {

    private Logger logger = Logger.getLogger(TaskTest.class);

    SampleDao sampleDao;

    public void setSampleDao(SampleDao sampleDao) {
        this.sampleDao = sampleDao;
    }

    //@Value( "${abc}" )
    String str;

    public void runTask() {
        System.out.print("ss1");
        logger.info("tick job = " + str);
        sampleDao.insert();
        logger.info("insert is ok! job = " + str);
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
