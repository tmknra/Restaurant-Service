package com.example.hw_5.dao.impl;

import com.example.hw_5.dao.FeedbackDao;
import com.example.hw_5.entity.Feedback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    // @Autowired
    // private DatabaseConnectionProperties databaseConnectionProperties;

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
    public List<Feedback> getAllByRestaurantID(Long id) {
        return getRestaurantFeedbacks(id);
    }

    @Override
    public Double getAverageRatingByRestaurantID(Long restaurantID) {
        return calculateRestaurantRating(restaurantID);
    }

    @Override
    public String getFeedbackTextByID(Long id) {
        return getFeedbackText(id);
    }

    @Override
    public void addNewFeedback(Feedback feedback) {
        addNewInstance(feedback);
    }

    @Override
    public void changeFeedbackByID(Long feedbackID, String newFeedback, Integer newRating) {
        changeFeedback(feedbackID, newFeedback, newRating);
    }

    @Override
    public void deleteFeedbackByRestaurantId(Long id) {
        deleteFeedback(id);
    }

    private List<Feedback> getRestaurantFeedbacks(Long id) {
        List<Feedback> feedbacks = new ArrayList<>();
        String query = "SELECT * FROM feedbacks WHERE restaurant_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Feedback feedback = new Feedback(
                        resultSet.getLong(1),
                        resultSet.getLong(2),
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
                        "VALUES(?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, feedback.getRestaurantid());
            preparedStatement.setObject(2, feedback.getFeedback());
            preparedStatement.setObject(3, feedback.getRating());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeFeedback(Long feedbackID, String newFeedback, Integer newRating) {
        String query = "UPDATE feedbacks SET feedback = ?, rating = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, newFeedback);
            preparedStatement.setObject(2, newRating);
            preparedStatement.setObject(3, feedbackID);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Double calculateRestaurantRating(Long restaurantID) {
        List<Integer> marks = new ArrayList<>();
        String query = "SELECT rating FROM feedbacks WHERE restaurant_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, restaurantID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                marks.add(resultSet.getInt(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return marks.stream().mapToInt(value -> value).average().getAsDouble();
    }

    private String getFeedbackText(Long id) {
        String query = "SELECT feedback FROM feedbacks WHERE id = ?";
        String result = "";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getString(1);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Wrong ID!";
    }

    private void deleteFeedback(Long id) {
        String query = "DELETE FROM feedbacks WHERE restaurant_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
