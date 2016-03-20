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

    public enum SortOrder {
        ASCENDING,
        DESCENDING,
        UNSORTED;

        private SortOrder() {
        }
    }

    public static boolean isInteger(String s) {
        return isInteger(s,10);
    }

    public static boolean isInteger(String s, int radix) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i),radix) < 0) return false;
        }
        return true;
    }
}
