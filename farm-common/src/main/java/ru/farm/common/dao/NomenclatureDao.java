package ru.farm.common.dao;

import org.springframework.beans.factory.annotation.Autowired;
import ru.farm.common.entity.Nomenclature;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

public class NomenclatureDao {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private CommonDao commonDao;

    public void insert(){

        String sql = "INSERT INTO nomenclature " +
                "(id, name, volume_unit, parsing_names, comment) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, commonDao.getMainSeq());
            ps.setString(2, "aaa " + Math.random());
            ps.setString(3, "кг");
            ps.setString(4, "bbb " + Math.random());
            ps.setString(5, "ccc " + Math.random());
            ps.executeUpdate();
            ps.close();

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

    public void add(Nomenclature nomenclature){

        String sql = "INSERT INTO nomenclature " +
                "(id, name, volume_unit, parsing_names, comment) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, commonDao.getMainSeq());
            ps.setString(2, nomenclature.getName());
            ps.setString(3, nomenclature.getVolumeUnit());
            ps.setString(4, nomenclature.getParsingNames());
            ps.setString(5, nomenclature.getComment());
            ps.executeUpdate();
            ps.close();

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

    public void edit(Nomenclature nomenclature){

        String sql = "UPDATE nomenclature" +
                " SET name=?, volume_unit=?, parsing_names=?, comment=?" +
                " WHERE id=?;";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nomenclature.getName());
            ps.setString(2, nomenclature.getVolumeUnit());
            ps.setString(3, nomenclature.getParsingNames());
            ps.setString(4, nomenclature.getComment());
            ps.setLong(5, nomenclature.getId());
            ps.executeUpdate();
            ps.close();

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

    public void del(Nomenclature nomenclature){

        String sql = "DELETE FROM nomenclature" +
                " WHERE id=?;";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, nomenclature.getId());
            ps.executeUpdate();
            ps.close();

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

    public List<Nomenclature> getNomenclatureList(int first, int pageSize,
                                                       String sortField,
                                                  CommonDao.SortOrder sortOrder,
                                                       Map<String, Object> filters) {
        List<Nomenclature> res = new ArrayList<Nomenclature>();
        String query = "SELECT id, name, volume_unit, parsing_names, comment FROM nomenclature where 1=1 ";
        query = processFilters(filters, query);

        if (sortField != null) {
            query += " order by "+sortField+" "
                    +(sortOrder.equals(CommonDao.SortOrder.ASCENDING) ?
                    "ASC" :
                    "DESC");
        }

        if (first > 0) {
            query += " offset " + first + " ";
        }

        if (pageSize > 0) {
            query += " limit " + pageSize + " ";
        }

        System.out.println(query);

        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            //preparedStatement.setInt(1, 0);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                res.add(new Nomenclature(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5)));
            }
            resultSet.close();


        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
        return res;
    }

    public Integer getNomenclatureCount(Map<String, Object> filters) {
        Integer res = null;
        String query = "SELECT count(1) FROM nomenclature where 1=1 ";
        query = processFilters(filters, query);

        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            //preparedStatement.setInt(1, 0);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                res = resultSet.getInt(1);
            }
            resultSet.close();


        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
        return res;
    }

    private String processFilters(Map<String, Object> filters, String query) {
        String idFilter = (String) filters.get("id");
        String nameFilter = (String) filters.get("name");
        String volumeUnitFilter = (String) filters.get("volume_unit");
        String parsingNamesFilter = (String) filters.get("parsing_names");
        String commentFilter = (String) filters.get("comment");
        String globalFilter = (String) filters.get("globalFilter");

        if (idFilter != null) {
            if (CommonDao.isInteger(idFilter)) {
                query += " and id = '" + idFilter + "' ";
            } else {
                query += " and 0=1 ";
            }
        }
        if (nameFilter != null) {
            query += " and name like '%" + nameFilter + "%' ";
        }
        if (volumeUnitFilter != null) {
            query += " and volume_unit like '%" + volumeUnitFilter + "%' ";
        }
        if (parsingNamesFilter != null) {
            query += " and parsing_names like '%" + parsingNamesFilter + "%' ";
        }
        if (commentFilter != null) {
            query += " and comment like '%" + idFilter + "%' ";
        }

        if (globalFilter != null && !globalFilter.trim().isEmpty()) {
            query += " and (";

            if (CommonDao.isInteger(globalFilter)) {
                query += " id = '" + globalFilter + "' or ";
            }

            query += " name like '%" + globalFilter + "%' " +
                     " or volume_unit like '%" + globalFilter + "%' " +
                     " or parsing_names like '%" + globalFilter + "%' " +
                     " or comment like '%" + globalFilter + "%' " +
                     ") ";
        }

        return query;
    }

 /*   public Customer findByCustomerId(int custId){

        String sql = "SELECT * FROM CUSTOMER WHERE CUST_ID = ?";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, custId);
            Customer customer = null;
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                customer = new Customer(
                        rs.getInt("CUST_ID"),
                        rs.getString("NAME"),
                        rs.getInt("Age")
                );
            }
            rs.close();
            ps.close();
            return customer;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
    }*/
}
