package ua.edu.sumdu.nc.dao;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
@Getter
@Setter
public class DAO {

    @Value(value = "${url}")
    private String url;
    @Value(value = "${user}")
    private String user;
    @Value(value = "${password}")
    private String password;

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
