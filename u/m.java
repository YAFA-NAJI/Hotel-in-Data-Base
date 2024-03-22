import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class m extends Application {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/hotelmanagment?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USERNAME = "root"; 
    private static final String PASSWORD = "1200284"; 

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = FXMLLoader.load(getClass().getResource("0.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
/* 
    @Override
    public void init() throws Exception {
        super.init();
        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            LocalDate currentDate = LocalDate.now();
            String date = currentDate.toString();

            // Calculate profit and loss for the day
            double profitLossDay = calculateProfitLossDay(connection, date);
            System.out.println("Profit/Loss for " + date + ": " + profitLossDay);

            // Calculate profit and loss for the month
            double profitLossMonth = calculateProfitLossMonth(connection, currentDate.getMonthValue());
            System.out.println("Profit/Loss for Month " + currentDate.getMonthValue() + ": " + profitLossMonth);

            // Calculate profit and loss for the year
            double profitLossYear = calculateProfitLossYear(connection, currentDate.getYear());
            System.out.println("Profit/Loss for Year " + currentDate.getYear() + ": " + profitLossYear);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static double calculateProfitLossDay(Connection connection, String date) throws SQLException {
        double totalRevenue = 0;
        double totalPayments = 0;

        // Calculate total revenue for the day
        String revenueQuery = "SELECT SUM(Room.Salary) AS TotalRevenue FROM Room " +
                "JOIN Room_Cus ON Room.IDR = Room_Cus.IDR " +
                "WHERE Room_Cus.Date_in = ?";
        try (PreparedStatement revenueStatement = connection.prepareStatement(revenueQuery)) {
            revenueStatement.setString(1, date);

            ResultSet revenueResultSet = revenueStatement.executeQuery();
            if (revenueResultSet.next()) {
                totalRevenue = revenueResultSet.getDouble("TotalRevenue");
            }
        }

        // Calculate total payments for the day
        String paymentsQuery = "SELECT COALESCE(SUM(Room_type.Amount), 0) AS TotalPayments FROM Room " +
                "JOIN Room_Cus ON Room.IDR = Room_Cus.IDR " +
                "JOIN Room_type ON Room.Room_type_id = Room_type.id " +
                "WHERE Room_Cus.Date_in = ?";
        try (PreparedStatement paymentsStatement = connection.prepareStatement(paymentsQuery)) {
            paymentsStatement.setString(1, date);

            ResultSet paymentsResultSet = paymentsStatement.executeQuery();
            if (paymentsResultSet.next()) {
                totalPayments = paymentsResultSet.getDouble("TotalPayments");
            }
        }

        return totalRevenue - totalPayments;
    }

    private static double calculateProfitLossMonth(Connection connection, int month) throws SQLException {
        double totalRevenue = 0;
        double totalPayments = 0;

        // Calculate total revenue for the month
        String revenueQuery = "SELECT SUM(Room.Salary) AS TotalRevenue FROM Room " +
                "JOIN Room_Cus ON Room.IDR = Room_Cus.IDR " +
                "WHERE MONTH(Room_Cus.Date_in) = ?";
        try (PreparedStatement revenueStatement = connection.prepareStatement(revenueQuery)) {
            revenueStatement.setInt(1, month);

            ResultSet revenueResultSet = revenueStatement.executeQuery();
            if (revenueResultSet.next()) {
                totalRevenue = revenueResultSet.getDouble("TotalRevenue");
            }
        }

        // Calculate total payments for the month
        String paymentsQuery = "SELECT COALESCE(SUM(Room_type.Amount), 0) AS TotalPayments FROM Room " +
                "JOIN Room_Cus ON Room.IDR = Room_Cus.IDR " +
                "JOIN Room_type ON Room.Room_type_id = Room_type.id " +
                "WHERE MONTH(Room_Cus.Date_in) = ?";
        try (PreparedStatement paymentsStatement = connection.prepareStatement(paymentsQuery)) {
            paymentsStatement.setInt(1, month);

            ResultSet paymentsResultSet = paymentsStatement.executeQuery();
            if (paymentsResultSet.next()) {
                totalPayments = paymentsResultSet.getDouble("TotalPayments");
            }
        }

        return totalRevenue - totalPayments;
    }

    private static double calculateProfitLossYear(Connection connection, int year) throws SQLException {
        double totalRevenue = 0;
        double totalPayments = 0;

        // Calculate total revenue for the year
        String revenueQuery = "SELECT SUM(Room.Salary) AS TotalRevenue FROM Room " +
                "JOIN Room_Cus ON Room.IDR = Room_Cus.IDR " +
                "WHERE YEAR(Room_Cus.Date_in) = ?";
        try (PreparedStatement revenueStatement = connection.prepareStatement(revenueQuery)) {
            revenueStatement.setInt(1, year);

            ResultSet revenueResultSet = revenueStatement.executeQuery();
            if (revenueResultSet.next()) {
                totalRevenue = revenueResultSet.getDouble("TotalRevenue");
            }
        }

        // Calculate total payments for the year
        String paymentsQuery = "SELECT COALESCE(SUM(Room_type.Amount), 0) AS TotalPayments FROM Room " +
                "JOIN Room_Cus ON Room.IDR = Room_Cus.IDR " +
                "JOIN Room_type ON Room.Room_type_id = Room_type.id " +
                "WHERE YEAR(Room_Cus.Date_in) = ?";
        try (PreparedStatement paymentsStatement = connection.prepareStatement(paymentsQuery)) {
            paymentsStatement.setInt(1, year);

            ResultSet paymentsResultSet = paymentsStatement.executeQuery();
            if (paymentsResultSet.next()) {
                totalPayments = paymentsResultSet.getDouble("TotalPayments");
            }
        }

        return totalRevenue - totalPayments;
    }
}
/* 
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class m extends Application {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/hotelmanagment?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1200284";

    public static void main(String[] args) {
       
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Pane root = FXMLLoader.load(getClass().getResource("0.fxml"));
        Scene scene5 = new Scene(root);
        primaryStage.setScene(scene5);
        primaryStage.show();
        Label resultLabel = new Label();

        VBox vbox = new VBox(resultLabel);
        Scene scene = new Scene(vbox, 400,300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("DAY");
        primaryStage.show();

        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            LocalDate currentDate = LocalDate.now();

            // Calculate profit and loss for the day
            double profitLossDay = calculateProfitLoss(connection, currentDate, currentDate);
            resultLabel.setText("Profit/Loss for " + currentDate + ": " + profitLossDay);

            // Calculate profit and loss for the month
            LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);
            LocalDate lastDayOfMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth());
            double profitLossMonth = calculateProfitLoss(connection, firstDayOfMonth, lastDayOfMonth);
            showBarChart("Profit/Loss for Month", profitLossMonth);

            // Calculate profit and loss for the year
            LocalDate firstDayOfYear = currentDate.withDayOfYear(1);
            LocalDate lastDayOfYear = currentDate.withDayOfYear(currentDate.lengthOfYear());
            double profitLossYear = calculateProfitLoss(connection, firstDayOfYear, lastDayOfYear);
            showBarChart("Profit/Loss for Year", profitLossYear);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static double calculateProfitLoss(Connection connection, LocalDate startDate, LocalDate endDate) throws SQLException {
        double totalRevenue = 0;
        double totalPayments = 0;

        // Calculate total revenue
        String revenueQuery = "SELECT SUM(Room.Salary) AS TotalRevenue " +
                "FROM Room " +
                "JOIN Room_Cus ON Room.IDR = Room_Cus.IDR " +
                "WHERE Room_Cus.Date_in BETWEEN ? AND ?";
        try (PreparedStatement revenueStatement = connection.prepareStatement(revenueQuery)) {
            revenueStatement.setDate(1, Date.valueOf(startDate));
            revenueStatement.setDate(2, Date.valueOf(endDate));

            ResultSet revenueResultSet = revenueStatement.executeQuery();
            if (revenueResultSet.next()) {
                totalRevenue = revenueResultSet.getDouble("TotalRevenue");
            }
        }

        // Calculate total payments
        String paymentsQuery = "SELECT COALESCE(SUM(Room_type.Amount), 0) AS TotalPayments " +
                "FROM Room " +
                "JOIN Room_Cus ON Room.IDR = Room_Cus.IDR " +
                "JOIN Room_type ON Room.Room_type = Room_type.Room_type " +
                "WHERE Room_Cus.Date_in BETWEEN ? AND ?";
        try (PreparedStatement paymentsStatement = connection.prepareStatement(paymentsQuery)) {
            paymentsStatement.setDate(1, Date.valueOf(startDate));
            paymentsStatement.setDate(2, Date.valueOf(endDate));

            ResultSet paymentsResultSet = paymentsStatement.executeQuery();
            if (paymentsResultSet.next()) {
                totalPayments = paymentsResultSet.getDouble("TotalPayments");
            }
        }

        return totalRevenue - totalPayments;
    }

    private void showBarChart(String title, double profitLoss) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle(title);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Profit/Loss", profitLoss));

        barChart.getData().add(series);

        VBox vbox = new VBox(barChart);

        Scene scene = new Scene(vbox, 400, 300);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Profit/Loss");
        stage.show();
    }
}
*/