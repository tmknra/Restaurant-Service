package com.example.hw_5.dao.impl;

import com.example.hw_5.config.DatabaseConnectionProperties;
import com.example.hw_5.dao.FeedbackDao;
import com.example.hw_5.entity.Feedback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.hw_5.config.DatabaseConnectionProperties.getConnectionProps;
import static com.example.hw_5.config.DatabaseConnectionProperties.url;

@Repository
public class FeedbackDaoImpl implements FeedbackDao {

    private static final Logger log = LoggerFactory.getLogger(FeedbackDaoImpl.class);

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
    public List<Feedback> getAllByRestaurantID(Integer id) {
        return getRestaurantFeedbacks(id);
    }

    @Override
    public Double getRating(Integer restaurantID) {
        return calculateRestaurantRating(restaurantID);
    }

    @Override
    public void addFeedback(Feedback feedback) {
        addNewInstance(feedback);
    }

    @Override
    public void changeFeedbackByID(Integer feedbackID, String newFeedback, Integer newRating) {
        changeFeedback(feedbackID, newFeedback, newRating);
    }

    private List<Feedback> getRestaurantFeedbacks(Integer id) {
        List<Feedback> feedbacks = new ArrayList<>();
        String query = "SELECT * FROM feedbacks WHERE restaurant_id = " + id;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Feedback feedback = new Feedback(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getString(3),
                        resultSet.getInt(4)
                );
                feedbacks.add(feedback);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return feedbacks;
    }

    private void addNewInstance(Feedback feedback) {
        String query = "INSERT INTO feedbacks (restaurant_id, feedback, rating)" +
                "VALUES(" + feedback.getRestaurantID() + ", '" + feedback.getFeedback() + "', " + feedback.getRating() + ")";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeFeedback(Integer feedbackID, String newFeedback, Integer newRating) {
        String query = "UPDATE feedbacks SET feedback = '" + newFeedback + "', rating = " + newRating + " WHERE id = " + feedbackID;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Double calculateRestaurantRating(Integer restaurantID) {
        List<Integer> marks = new ArrayList<>();
        String query = "SELECT rating FROM feedbacks WHERE restaurant_id = " + restaurantID;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                marks.add(resultSet.getInt(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return marks.stream().mapToInt(value -> value).average().getAsDouble();
    }
}
