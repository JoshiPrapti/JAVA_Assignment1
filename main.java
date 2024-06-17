package com.example.assignment1;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// This is the main class of our application, which shows movie genres in a pie chart and table
public class main extends Application{
    private Scene chartScene, tableScene;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Most Watched Movie Genres");

        // Creating a pie chart and load it with data from the database
        PieChart pieChart = new PieChart();
        pieChart.setData(getChartData());

        // Button to switch from the pie chart to the table view
        Button toTableViewButton = new Button("Switch to Table View");
        toTableViewButton.setOnAction(e -> primaryStage.setScene(tableScene));

        // Layout for the pie chart scene, includes the chart and the button
        VBox chartLayout = new VBox(10, pieChart, toTableViewButton);
        chartScene = new Scene(chartLayout, 800, 600);

        // Create a table view and load it with data from the database
        TableView<Genre> tableView = new TableView<>(getGenreData());
        TableColumn<Genre, String> genreColumn = new TableColumn<>("Genre");
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        TableColumn<Genre, Integer> countColumn = new TableColumn<>("Watch Count");
        countColumn.setCellValueFactory(new PropertyValueFactory<>("watchCount"));
        tableView.getColumns().addAll(genreColumn, countColumn);

        // Button to switch from the table view back to the pie chart
        Button toChartViewButton = new Button("Switch to Chart View");
        toChartViewButton.setOnAction(e -> primaryStage.setScene(chartScene));

        // Layout for the table view scene, includes the table and the button
        VBox tableLayout = new VBox(10, tableView, toChartViewButton);
        tableScene = new Scene(tableLayout, 800, 600);

        // Set the initial scene to the pie chart
        primaryStage.setScene(chartScene);
        primaryStage.show();
    }

    // This method gets data for the pie chart from the database
    private ObservableList<PieChart.Data> getChartData() {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT genre, watch_count FROM MostWatchedGenres");
            while (resultSet.next()) {
                data.add(new PieChart.Data(resultSet.getString("genre"), resultSet.getInt("watch_count")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    // This method gets data for the table view from the database
    private ObservableList<Genre> getGenreData() {
        ObservableList<Genre> data = FXCollections.observableArrayList();
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT genre, watch_count FROM MostWatchedGenres");
            while (resultSet.next()) {
                data.add(new Genre(resultSet.getString("genre"), resultSet.getInt("watch_count")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    // Main method to launch the application
    public static void main(String[] args) {
        launch(args);
    }

    // Simple class to represent a movie genre and its watch count
    public static class Genre {
        private final String genre;
        private final int watchCount;

        public Genre(String genre, int watchCount) {
            this.genre = genre;
            this.watchCount = watchCount;
        }

        public String getGenre() {
            return genre;
        }

        public int getWatchCount() {
            return watchCount;
        }
    }
}

