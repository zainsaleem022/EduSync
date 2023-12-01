package com.example.fxproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.sql.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
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






public class Student {

	@FXML
    private TextField classIdField;
	@FXML
    private Label classJoinedSuccessfully;
	@FXML
    private Label studentUnenrolledClassSuccessfully;
    @FXML
    private ListView<String> classListView;
	
	private int student_id;
	
    @FXML
    private Label classUnenrolledSuccessfully;
	

	public Student()
	{
		
	}
    
	public Student(int id)
	{
		this.student_id = id;
	}
	
	// Initialize the controller with data (you can fetch data from the database)
	// Manually initialize the controller
	public void initializeController() {
	    List<String> classes = get_student_classes();
	    classListView.getItems().addAll(classes);
	}
    
	
	
	private int extractClassId(String classInfo) {
	    // Example classInfo: "ClassID 123. \nCourse: Math. \nTeacher: John Doe. \nDescription: ABC"

	    // Define a regular expression to match the class ID pattern
	    String regex = "ClassID\\s+(\\d+)\\.";

	    // Use a Pattern and Matcher to find the match
	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(classInfo);

	    // Check if a match is found
	    if (matcher.find()) {
	        // Extract and return the class ID from the matched group
	        return Integer.parseInt(matcher.group(1));
	    } else {
	        // If no match is found, handle the error or return a default value
	        System.out.println("No valid class ID found in the string: " + classInfo);
	        return -1; // Return a default value or handle the error as needed
	    }
	}
	
	
    
	private List<String> get_student_classes() {
	    List<String> classes = new ArrayList<>();

	    // SQL query to retrieve classes with course details and teacher's name based on student ID
	    String selectClassesQuery = "SELECT classroom.class_id, classroom.description, course.course_name, teacher.username " +
	            "FROM classroom " +
	            "JOIN course ON classroom.course_id = course.course_id " +
	            "JOIN teacher ON classroom.teacher_id = teacher.id " +
	            "JOIN student_classroom ON classroom.class_id = student_classroom.class_id " +
	            "WHERE student_classroom.student_id = ?";

	    try (PreparedStatement preparedStatement = Main.con.prepareStatement(selectClassesQuery)) {
	        preparedStatement.setInt(1, this.student_id);

	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            while (resultSet.next()) {
	                int classId = resultSet.getInt("class_id");
	                String description = resultSet.getString("description");
	                String courseName = resultSet.getString("course_name");
	                String teacherName = resultSet.getString("username");

	                // Format the data as needed
	                String classInfo = "ClassID " + classId + ". " + "\n" + 
	                        "Course: " + courseName + ". " + "\n" + 
	                        "Teacher: " + teacherName + ". " + "\n" + 
	                        "Description: " + description;

	                System.out.print("classInfo");

	                classes.add(classInfo);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return classes;
	}


    
    
    
    @FXML
    public void show_student_view_class_screen(ActionEvent event) {
    	
        try {
            // Load the FXML file for the login window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("student_view_classrooms.fxml"));
        	
        	// Set the controller for the login window (create a LoginController class)
            Student studentController = new Student(this.student_id);
            loader.setController(studentController);

            // Load the FXML file with the specified controller
            Main.root = loader.load();
            
            studentController.initializeController();
            
            Main.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            
            // Create a scene with the loaded FXML file
            Main.scene = new Scene(Main.root);
            
            Main.stage.setScene(Main.scene);
            Main.stage.setTitle("View Classrooms");
            
            // Show the login window
            Main.stage.show();
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
	
	@FXML
	public void show_student_main_menu(ActionEvent event)
	{
		try {
            // Load the FXML file for the login window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("student_menu.fxml"));
        	
        	// Set the controller for the login window (create a LoginController class)
            Student studentController = new Student(this.student_id);
            loader.setController(studentController);

            // Load the FXML file with the specified controller
            Main.root = loader.load();
            Main.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            
            // Create a scene with the loaded FXML file
            Main.scene = new Scene(Main.root);
            
            Main.stage.setScene(Main.scene);
            Main.stage.setTitle("Student Menu");
            
            // Show the login window
            Main.stage.show();
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	
	
	@FXML
	public void show_student_join_class_screen(ActionEvent event)
	{
		try {
            // Load the FXML file for the login window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("student_join_class_screen.fxml"));
        	
        	// Set the controller for the login window (create a LoginController class)
            Student studentController = new Student(this.student_id);
            loader.setController(studentController);

            // Load the FXML file with the specified controller
            Main.root = loader.load();
            Main.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            
            // Create a scene with the loaded FXML file
            Main.scene = new Scene(Main.root);
            
            Main.stage.setScene(Main.scene);
            Main.stage.setTitle("Join Class");
            
            // Show the login window
            Main.stage.show();
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	
	
	public void student_join_class()
	{
		int classId = Integer.parseInt(classIdField.getText());
		
		try {
	        Connection con = Main.con;

	        // Assuming you have a PreparedStatement preparedStmt
	        String insertStudentClassQuery = "INSERT INTO student_classroom (student_id, class_id) VALUES (?, ?)";
	        PreparedStatement preparedStmt = con.prepareStatement(insertStudentClassQuery);

	        // Set the values for the placeholder in the query
	        preparedStmt.setInt(1, this.student_id);
	        preparedStmt.setInt(2, classId);

	        // Execute the insert statement
	        preparedStmt.executeUpdate();

	        System.out.println("Student joined class successfully.");
	        classJoinedSuccessfully.setText("Class Joined Successfully");
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("Error joining class.");
	        classJoinedSuccessfully.setText("Invalid ClassID / You have already joined this class");
	    }
	}
	
	@FXML
	public void student_logout(ActionEvent event)
	{
	    User u = new User();
	    u.showLoginWindow(event);
	}
	
	
	
	
	public void student_unenroll_class()
	{
		// Get the selected item from the ListView
        String selectedClass = classListView.getSelectionModel().getSelectedItem();

        System.out.print(selectedClass);
        // Extract class_id from the selected item (you may need to parse it depending on your formatting)
        int classId = extractClassId(selectedClass);
        
        
     // SQL query to remove the row from the student_classroom table
        String removeEnrollmentQuery = "DELETE FROM student_classroom WHERE student_id = ? AND class_id = ?";

        try (PreparedStatement preparedStatement = Main.con.prepareStatement(removeEnrollmentQuery)) {
            preparedStatement.setInt(1, this.student_id);
            preparedStatement.setInt(2, classId);

            // Execute the delete statement
            int rowsAffected = preparedStatement.executeUpdate();

            studentUnenrolledClassSuccessfully.setText("Successfully Unenrolled from Class");
        } catch (SQLException e) {
            e.printStackTrace();
            studentUnenrolledClassSuccessfully.setText("Invalid Class ID");
        }
	}
	
	
	
	public void student_submit_assignment()
	{
		
	}
	
	
	
	public void student_view_missed_assignments()
	{
		
	}
	
	public void student_view_all_assignments()
	{
		
	}
	
	public void student_view_to_do_assignments()
	{
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//hassan's code
	
	
	
	
	
	
	
	
}
