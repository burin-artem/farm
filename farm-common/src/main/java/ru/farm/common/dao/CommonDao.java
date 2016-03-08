package ru.farm.common.dao;

import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Администратор on 24.02.2016.
 */
public class CommonDao {

    private final static String contsDB_MAIN_SEQ_NAME = "main_seq";

    @Autowired
    private DataSource dataSource;

    public Long getSequence(String seqName) {
        Long res = null;
        String sql = "select nextval('" + seqName + "')";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                res = rs.getLong(1);
            }
            rs.close();
            ps.close();
            return res;

        } catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
    }

    public Long getMainSeq() {
        return getSequence(contsDB_MAIN_SEQ_NAME);
    }

}
