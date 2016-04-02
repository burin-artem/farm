package ru.farm.common.dao;

import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

	@Autowired
	private DataSource dataSource;

	public boolean login(String user, String password) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement("SELECT name, password FROM public.\"user\" WHERE name = ? AND password = ?");
			ps.setString(1, user);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return true;
			}
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
			return false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
		return false;
	}
}