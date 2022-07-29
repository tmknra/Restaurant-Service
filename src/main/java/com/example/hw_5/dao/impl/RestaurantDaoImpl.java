package com.example.hw_5.dao.impl;

import com.example.hw_5.config.DatabaseConnectionProperties;
import com.example.hw_5.dao.RestaurantDao;
import com.example.hw_5.entity.Restaurant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.hw_5.config.DatabaseConnectionProperties.*;
import static com.example.hw_5.config.DatabaseConnectionProperties.url;

@Repository
public class RestaurantDaoImpl implements RestaurantDao {

    private static final Logger log = LoggerFactory.getLogger(RestaurantDaoImpl.class);

    private static Connection connection;

    @Autowired
    private DatabaseConnectionProperties databaseConnectionProperties;

    @PostConstruct
    public void initialize() {
        try {
            connection = DriverManager.getConnection(url, getConnectionProps());
        } catch (SQLException e) {
            log.debug("Incorrect database URL '{}' or connection props '{}'", url, getConnectionProps());
        }
    }

    @PreDestroy
    public void destroy() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Restaurant> getAll() {
        return getAllRestaurants();
    }

    @Override
    public String getDescription(String name) {
        return getDescriptionByName(name);
    }

    @Override
    public void addRestaurant(Restaurant restaurant) {
        String query = "INSERT INTO restaurants (name, description)" +
                "VALUES('" + restaurant.getName() + "', '" + restaurant.getDescription() +
                "')";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeRestaurantDescription(String restaurantName, String newDescription) {
        String query = "UPDATE restaurants SET description = '" + newDescription + "' WHERE name = '" + restaurantName + "'";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<Restaurant> getAllRestaurants() {
        String getQuery = "SELECT * FROM restaurants";
        List<Restaurant> restaurants = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(getQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Restaurant restaurant = new Restaurant(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3));
                restaurants.add(restaurant);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return restaurants;
    }

    private static String getDescriptionByName(String restName) {
        String getQuery = "SELECT description FROM restaurants WHERE name = '" + restName + "'";
        String result = "";
        try (PreparedStatement preparedStatement = connection.prepareStatement(getQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                result = resultSet.getString(1);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Wrong name!";
    }
}
