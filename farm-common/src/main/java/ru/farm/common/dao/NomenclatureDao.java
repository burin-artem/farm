package ru.farm.common.dao;

import org.springframework.beans.factory.annotation.Autowired;
import ru.farm.common.entity.Nomenclature;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class NomenclatureDao {

    @Autowired
    private DataSource dataSource;

    public void insert(){

        String sql = "INSERT INTO nomenclature " +
                "(name, volume_unit, parsing_names, comment) VALUES (?, ?, ?, ?)";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            //ps.setInt(1, 1);
            ps.setString(1, "aaa " + Math.random());
            ps.setString(2, "кг");
            ps.setString(3, "bbb " + Math.random());
            ps.setString(3, "ccc " + Math.random());
            ps.setString(3, "ddd " + Math.random());
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
                "(name, volume_unit, parsing_names, comment) VALUES (?, ?, ?, ?)";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nomenclature.getName());
            ps.setString(2, nomenclature.getVolumeUnit());
            ps.setString(3, nomenclature.getParsingNames());
            ps.setString(4, nomenclature.getComment());
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
    public List<Nomenclature> getNomenclatureList() {
        List<Nomenclature> res = new ArrayList<Nomenclature>();
        String query = "SELECT id, name, volume_unit, parsing_names, comment FROM nomenclature";
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
        }
        return res;
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
