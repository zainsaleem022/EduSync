package com.example.fxproject;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
import javafx.scene.control.Button;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.collections.FXCollections;
import java.util.ResourceBundle;
import java.net.URL;
import javafx.scene.control.TextArea;
import javafx.stage.Window;
import java.util.ResourceBundle;
import java.net.URL;


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
												//hassan's code












public class Admin implements Initializable {
	
	    @FXML
	    private TextField courseNameField;
	    @FXML
	    private TextField teacherIdField;
	    @FXML
	    private TextField courseIdField;
	    @FXML
	    private TextField classIdField;
	    @FXML
	    private TextField userIdField;
	    @FXML
	    private TextArea descriptionArea;
	    @FXML
	    private Button submitButton;
	    @FXML
	    private Label adminScreen;
	    @FXML
	    private Label courseRemovedSuccessfully;
	    @FXML
	    private Label userRemovedSuccessfully;
	    @FXML
	    private Label classRemovedSuccessfully;
	    @FXML
	    private Button mainMenuButton;
	    @FXML
	    private Label messageLabel;  // You can add a label to show messages or errors
	    @FXML
	    private Label messageLabel1;
	    @FXML
	    private Button showCreateClassScreenButton;
	    @FXML
	    private ChoiceBox<String> roleChoiceBox;
		@FXML
	    private TextField usernameField;
	    @FXML
	    private PasswordField passwordField;
	    @FXML
	    private TextField emailField;
	    @FXML
	    private TextField roleField;
	    @FXML
	    private Label userRegisteredSuccessfully;
	    
	    private String[] options = {"Admin", "Teacher", "Student", "Parent"};
	    
	    @FXML
	    public void adminshowLoginWindow(ActionEvent event)
	    {
	    	User u = new User();
	    	u.showLoginWindow(event);
	    }
	    
	    
	    @Override
	    public void initialize(URL arg0, ResourceBundle arg1) {
	        // Initialize the ChoiceBox with role options
	    	if (roleChoiceBox != null) {
	            roleChoiceBox.getItems().addAll(options);
	        }
	    }
	    
	    
	    @FXML
	    public void admin_add_user()
	    {
	    	String username = usernameField.getText();
	        String password = passwordField.getText();
	        String email = emailField.getText();
	        String role = roleChoiceBox.getValue();
	        
	        User u = new User();
	        
	        if(u.register_user(username, password, email, role))
	        {
	        	userRegisteredSuccessfully.setText("User registered successfully.");
	        }
	        else
	        {
	        	userRegisteredSuccessfully.setText("User cannot be registered.");
	        }
	    }
	    
	    
	    @FXML
	    public void show_admin_remove_user_screen(ActionEvent event) {
	    	
	        try {
	            // Load the FXML file for the login window
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("admin_remove_user.fxml"));
	        	
	        	// Set the controller for the login window (create a LoginController class)
	            Admin adminController = new Admin();
	            loader.setController(adminController);

	            // Load the FXML file with the specified controller
	            Main.root = loader.load();
	            Main.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	            
	            // Create a scene with the loaded FXML file
	            Main.scene = new Scene(Main.root);
	            
	            Main.stage.setScene(Main.scene);
	            Main.stage.setTitle("Remove User");
	            
	            // Show the login window
	            Main.stage.show();
	            
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    
	    
	    
	    @FXML
	    public void admin_remove_user()
	    {
	    	int userId = Integer.parseInt(userIdField.getText());
	    	
	    	User u = new User();
	    	
	    	if (u.remove_user(userId))
	    	{
	    		userRemovedSuccessfully.setText("User Removed Successfully");
	    	}
	    	else
	    	{
	    		userRemovedSuccessfully.setText("Invalid User ID");
	    	}
	    }
	    
	    
	    
	    
	    @FXML
	    public void show_admin_add_user_screen(ActionEvent event) {
	    	
	        try {
	            // Load the FXML file for the login window
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("admin_register_user.fxml"));
	        	
	        	// Set the controller for the login window (create a LoginController class)
	            Admin adminController = new Admin();
	            loader.setController(adminController);

	            // Load the FXML file with the specified controller
	            Main.root = loader.load();
	            Main.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	            
	            // Create a scene with the loaded FXML file
	            Main.scene = new Scene(Main.root);
	            
	            Main.stage.setScene(Main.scene);
	            Main.stage.setTitle("Register New User");
	            
	            // Show the login window
	            Main.stage.show();
	            
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    
	    
	    
	    @FXML
	    public void admin_add_course()
	    {
	    	String courseName = courseNameField.getText();
	        String description = descriptionArea.getText();
	        int courseId = 0;
	        
	        
	     // SQL query to get the max course_id from the course table
	        String getMaxCourseIdQuery = "SELECT MAX(course_id) FROM course";

	        try (PreparedStatement getMaxCourseIdStatement = Main.con.prepareStatement(getMaxCourseIdQuery);
	             ResultSet resultSet = getMaxCourseIdStatement.executeQuery()) {
	            if (resultSet.next()) {
	                courseId = resultSet.getInt(1);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        
	        courseId += 1;
	        
	        Course course = new Course(courseId, courseName, description);
	        course.add_course();
	        messageLabel1.setText("Course Added Successfully");
	    }
	    
	    
	    @FXML
	    public void show_admin_remove_class_screen(ActionEvent event) {
	    	
	        try {
	            // Load the FXML file for the login window
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("admin_remove_class_screen.fxml"));
	        	
	        	// Set the controller for the login window (create a LoginController class)
	            Admin adminController = new Admin();
	            loader.setController(adminController);

	            // Load the FXML file with the specified controller
	            Main.root = loader.load();
	            Main.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	            
	            // Create a scene with the loaded FXML file
	            Main.scene = new Scene(Main.root);
	            
	            Main.stage.setScene(Main.scene);
	            Main.stage.setTitle("Remove Course");
	            
	            // Show the login window
	            Main.stage.show();
	            
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    
	    
	    
	    @FXML
	    public void adminshowRemoveCourseWindow(ActionEvent event) {
	    	
	        try {
	            // Load the FXML file for the login window
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("remove_course_screen.fxml"));
	        	
	        	// Set the controller for the login window (create a LoginController class)
	            Admin adminController = new Admin();
	            loader.setController(adminController);

	            // Load the FXML file with the specified controller
	            Main.root = loader.load();
	            Main.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	            
	            // Create a scene with the loaded FXML file
	            Main.scene = new Scene(Main.root);
	            
	            Main.stage.setScene(Main.scene);
	            Main.stage.setTitle("Remove Course");
	            
	            // Show the login window
	            Main.stage.show();
	            
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }	    
	    
	    
	    
	    
	    @FXML
	    public void admin_remove_class()
	    {
	    	int classId = Integer.parseInt(classIdField.getText());
	    	
	    	Classroom cl = new Classroom(classId, 0, 0, "");
	    	if (cl.remove_classroom())
	    	{
	    		classRemovedSuccessfully.setText("Class Removed Successfully");
	    	}
	    	else
	    	{
	    		classRemovedSuccessfully.setText("Invalid Class ID");
	    	}
	    }
	    
	    
	    
	    
	    @FXML
	    public void admin_remove_course()
	    {
	    	int courseId = Integer.parseInt(courseIdField.getText());
	    	
	    	Course c = new Course(courseId, "", "");
	    	if (c.remove_course())
	    	{
	    		courseRemovedSuccessfully.setText("Course Removed Successfully");
	    	}
	    	else
	    	{
	    		courseRemovedSuccessfully.setText("Invalid Course ID");
	    	}
	    }
	    
	    
	    
	    @FXML
	    public void admin_add_class()
	    {
	    	int teacherId = Integer.parseInt(teacherIdField.getText());
	        int courseId = Integer.parseInt(courseIdField.getText());
	        String description = descriptionArea.getText();
	        
	        System.out.print(description);
	        
	        int class_id = 0;
	        
	        try {
	            // SQL query to get the max class_id from the classroom table
	            String getMaxClassroomIdQuery = "SELECT MAX(class_id) FROM classroom";

	            try (PreparedStatement getMaxClassroomIdStatement = Main.con.prepareStatement(getMaxClassroomIdQuery);
	                 ResultSet resultSet = getMaxClassroomIdStatement.executeQuery()) {

	                if (resultSet.next()) {
	                    class_id = resultSet.getInt(1);
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace(); // Handle the exception according to your application's needs
	        }
	        
	        class_id += 1;
	        
	        Classroom cl = new Classroom (class_id, courseId, teacherId, description);
	        
	        if(cl.add_classroom())
	        {
	        	messageLabel.setText("Classroom Added Successfully");	
	        }
	        else
	        {
	        	messageLabel.setText("This teacher already has a classroom with this course");
	        }

	    }
	    
	    
	    
	    
	    @FXML
	    public void show_admin_add_course_screen(ActionEvent event) {
	    	
	        try {
	            // Load the FXML file for the login window
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("admin_add_course_screen.fxml"));
	        	
	        	// Set the controller for the login window (create a LoginController class)
	            Admin adminController = new Admin();
	            loader.setController(adminController);

	            // Load the FXML file with the specified controller
	            Main.root = loader.load();
	            Main.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	            
	            // Create a scene with the loaded FXML file
	            Main.scene = new Scene(Main.root);
	            
	            Main.stage.setScene(Main.scene);
	            Main.stage.setTitle("Add Course");
	            
	            // Show the login window
	            Main.stage.show();
	            
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    
	    
	    
	    
	    @FXML
	    public void show_admin_add_class_screen(ActionEvent event) {
	    	
	        try {
	            // Load the FXML file for the login window
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("admin_create_class_screen.fxml"));
	        	
	        	// Set the controller for the login window (create a LoginController class)
	            Admin adminController = new Admin();
	            loader.setController(adminController);

	            // Load the FXML file with the specified controller
	            Main.root = loader.load();
	            Main.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	            
	            // Create a scene with the loaded FXML file
	            Main.scene = new Scene(Main.root);
	            
	            Main.stage.setScene(Main.scene);
	            Main.stage.setTitle("Create Classroom");
	            
	            // Show the login window
	            Main.stage.show();
	            
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    
	    @FXML
	    public void show_admin_menu_screen(ActionEvent event) {
	    
	    try {
            // Load the FXML file for the login window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("admin_menu.fxml"));
        	
        	// Set the controller for the login window (create a LoginController class)
            Admin adminController = new Admin();
            loader.setController(adminController);

            // Load the FXML file with the specified controller
            Main.root = loader.load();
            Main.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            
            // Create a scene with the loaded FXML file
            Main.scene = new Scene(Main.root);
            
            Main.stage.setScene(Main.scene);
            Main.stage.setTitle("Admin Menu");
            
            // Show the login window
            Main.stage.show();
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//hassan's code
	    
	    
}




