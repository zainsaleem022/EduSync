package com.example.fxproject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;











////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//hassan's code







public class Main extends Application {
    
	public static Stage stage;
	public static Scene scene;
	public static Parent root;
    public static Scanner myObj = new Scanner(System.in);
	public static Connection con=null;
	
	@Override
    public void start(Stage primaryStage) {
        try {
            // Load the FXML file
            Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));

            // Create a scene with the loaded FXML file
            Scene scene = new Scene(root);

            // Set the scene to the stage
            primaryStage.setScene(scene);

            // Set the title for the stage
            primaryStage.setTitle("EduSync");

            // Show the stage
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
    	
    	String url = "jdbc:mysql://localhost:3306/edusync";
        String username = "root";
        String password = "zain.mysql.123";
		
        
        try {
            // 2. Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 3. Establish a connection to the database
            con = (Connection) DriverManager.getConnection(url, username, password);
            if (con != null) {
                System.out.println("database is connected successfully");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // Handle the exception
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the SQLException if it occurs
        } finally {
            System.out.println("Program ran successfully");
        }
        
        launch(args);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//hassan's code
    
    
    
}
    
    
