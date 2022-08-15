package com.example.hw_5.dao.impl;

import com.example.hw_5.dao.RestaurantDao;
import com.example.hw_5.entity.Restaurant;
import com.example.hw_5.exception.entity.FoundationDateIsExpiredException;
import com.example.hw_5.exception.entity.PhoneNumberNotRuException;
import com.example.hw_5.util.Util;
import com.google.i18n.phonenumbers.NumberParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.example.hw_5.config.DatabaseConnectionProperties.getConnectionProps;
import static com.example.hw_5.config.DatabaseConnectionProperties.url;

@Repository
public class RestaurantDaoImpl implements RestaurantDao {

    private static final Logger log = LoggerFactory.getLogger(RestaurantDaoImpl.class);

    private static Connection connection;

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
    public List<Restaurant> getAllRestaurants() {
        return getAllRestaurantsFromDB();
    }

    @Override
    public String getDescriptionByName(String name) {
        return getDescriptionByRestaurantName(name);
    }

    @Override
    public String getFoundationDateById(Long id) {
        return getFoundationDate(id);
    }

    @Override
    public void addNewRestaurant(Restaurant restaurant) throws FoundationDateIsExpiredException {
        // LocalDate parse = LocalDate.parse(restaurant.getFoundation_date(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        if (restaurant.getFoundation_date() == null || LocalDate.now(ZoneId.systemDefault()).isBefore(restaurant.getFoundation_date())) {
            throw new FoundationDateIsExpiredException(restaurant.getName(), restaurant.getFoundation_date());
        }
        String query = "INSERT INTO restaurants (name, description, phone_number, email_address, foundation_date) VALUES(?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, restaurant.getName());
            preparedStatement.setObject(2, restaurant.getDescription());
            preparedStatement.setObject(3, restaurant.getPhone_number());
            preparedStatement.setObject(4, restaurant.getEmail_address());
            preparedStatement.setObject(5, restaurant.getFoundation_date());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeDescriptionByName(String restaurantName, String newDescription) {
        String query = "UPDATE restaurants SET description = ? WHERE name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newDescription);
            preparedStatement.setString(2, restaurantName);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setEmailById(Long id, String email) {
        setColumnById(id, "email_address", Util.validateEmailAddress(email));
    }


    @Override
    public void setPhoneNumberById(Long id, String number) throws NumberParseException, PhoneNumberNotRuException {
        setColumnById(id, "phone_number", Util.reformatRuTelephone(number));
    }

    @Override
    public void setFoundationDateById(Long id, String date) {
        LocalDate parse = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        setColumnById(id, "foundation_date", parse);
    }

    @Override
    public void deleteRestaurantByName(String name) {
        deleteRestaurant(name);
    }

    private static List<Restaurant> getAllRestaurantsFromDB() {
        String getQuery = "SELECT * FROM restaurants";
        List<Restaurant> restaurants = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(getQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Restaurant restaurant = new Restaurant(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        (LocalDate) resultSet.getObject(6)
                );
                restaurants.add(restaurant);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return restaurants;
    }

    private static String getDescriptionByRestaurantName(String restName) {
        String getQuery = "SELECT description FROM restaurants WHERE name = ?";
        String result = "";
        try (PreparedStatement preparedStatement = connection.prepareStatement(getQuery)) {
            preparedStatement.setString(1, restName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                result = resultSet.getString(1);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Wrong name!";
    }

    private String getFoundationDate(Long id) {
        String query = "SELECT foundation_date FROM restaurants WHERE id = ?";
        LocalDate date = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                date = resultSet.getDate(1).toLocalDate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert date != null;
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    private void deleteRestaurant(String name) {
        String query = "DELETE FROM restaurants WHERE name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setColumnById(Long id, String columnName, Object value) {
        String query = "UPDATE restaurants SET " + columnName + " = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, value);
            preparedStatement.setObject(2, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
