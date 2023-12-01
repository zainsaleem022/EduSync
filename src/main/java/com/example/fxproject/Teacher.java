package com.example.fxproject;

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
import javafx.scene.control.DatePicker;
import java.sql.Date;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
													//hassan's code





public class Teacher extends User {
	
		@FXML
		private Label teacherRemovedClassSuccessfully;
		@FXML
		private TextArea descriptionArea;
		@FXML
		private TextField courseIdField;
		@FXML
		private TextArea announcementDescription;
		@FXML
		private TextArea assignmentDescription;
		@FXML
	    private DatePicker datePicker;
		@FXML
		private TextField announcementTitle;
		@FXML
		private TextField assignmentTitle;
	    @FXML
	    private ListView<String> classListView;
		@FXML
		private ListView<String> courseListView;
	    @FXML
	    private Button goBackButton;
	    @FXML
	    private Label teacherCreatedClassSuccessfully;
	    @FXML
	    private Label assignmentAddedSuccessfully;
	    @FXML
	    private Label announcementAddedSuccessfully;
	    
	    private int teacher_id;
	    private List<String> classes;
		private List<String> courses;
	    private int teacher_class_id;
		private int teacher_course_id;
	    
	    
	    @FXML
	    public void teacher_Show_Login_Window(ActionEvent event)
	    {
	    	User u = new User();
	    	u.showLoginWindow(event);
	    }
	    
	    public Date getSelectedDate() {
	        if (datePicker.getValue() != null) {
	            java.util.Date utilDate = Date.valueOf(datePicker.getValue());
	            return new Date(utilDate.getTime());
	        }
	        return null;
	    }
	    
	    
	    public Teacher(int userid, int teacher_class_id, int teacher_course_id)
	    {
	    	this.teacher_id = userid;
	    	this.teacher_class_id = teacher_class_id;
			this.teacher_course_id = teacher_course_id;
	    }
	    
	    public Teacher(int userid)
	    {
	    	this.teacher_id = userid;
	    }

	@FXML
	public void show_teacher_select_course_for_classroom_screen(ActionEvent event) {

		try {
			// Load the FXML file for the login window
			FXMLLoader loader = new FXMLLoader(getClass().getResource("teacher_select_course_for_classroom.fxml"));


			// Set the controller for the login window (create a LoginController class)
			Teacher teacherController = new Teacher(this.teacher_id, 0 , 0);
			loader.setController(teacherController);




			// Load the FXML file with the specified controller
			Main.root = loader.load();



			Main.stage = (Stage)((Node)event.getSource()).getScene().getWindow();

			// Create a scene with the loaded FXML file
			Main.scene = new Scene(Main.root);

			Main.stage.setScene(Main.scene);
			Main.stage.setTitle("View Courses");

			teacherController.populateCourseListView();

			// Show the login window
			Main.stage.show();


		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	    public void teacher_add_assignment()
	    {
	        
	        String description = assignmentDescription.getText();
	        String title = assignmentTitle.getText();
	        
	        String insertAnnouncementQuery = "INSERT INTO announcement (class_id, teacher_id, title, description, time_posted) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = Main.con.prepareStatement(insertAnnouncementQuery)) {
                // Set the values for the placeholder in the query
                preparedStatement.setInt(1, this.teacher_class_id);
                preparedStatement.setInt(2, this.teacher_id);
                preparedStatement.setString(3, title);
                preparedStatement.setString(4, description);
                
                // Use the current timestamp for time_posted
                preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));

                // Execute the insert statement for the announcement table
                preparedStatement.executeUpdate();

                announcementAddedSuccessfully.setText("Announcement Added Successfully");
            } catch (SQLException e) {
                e.printStackTrace();
                announcementAddedSuccessfully.setText("Announcement Added Successfully");
            }

	    }
	    
	    
	    
	    @FXML
	    public void show_teacher_add_assignment_screen(ActionEvent event) {
	    	
	        try {
	            // Load the FXML file for the login window
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("teacher_add_assignment.fxml"));
	        	
	            
	            
	            // Get the selected item from the ListView before entering to add announcement screen
		        String selectedClass = classListView.getSelectionModel().getSelectedItem();
		        // Extract class_id from the selected item (you may need to parse it depending on your formatting)
		        int classId = extractClassId(selectedClass);
	            
	        	
		        
		        // Set the controller for the login window (create a LoginController class)
	            Teacher teacherController = new Teacher(this.teacher_id, classId, 0);
	            loader.setController(teacherController);

	            // Load the FXML file with the specified controller
	            Main.root = loader.load();
	            
	            Main.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	            
	            // Create a scene with the loaded FXML file
	            Main.scene = new Scene(Main.root);
	            
	            Main.stage.setScene(Main.scene);
	            Main.stage.setTitle("Add Assignment");
	            
	            // Show the login window
	            Main.stage.show();
	            
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    
	    
	    
	    @FXML
	    public void show_teacher_add_announcement_screen(ActionEvent event) {
	    	
	        try {
	            // Load the FXML file for the login window
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("teacher_add_announcement.fxml"));
	        	
	            
	            
	            // Get the selected item from the ListView before entering to add announcement screen
		        String selectedClass = classListView.getSelectionModel().getSelectedItem();
		        // Extract class_id from the selected item (you may need to parse it depending on your formatting)
		        int classId = extractClassId(selectedClass);
	            
	        	
		        
		        // Set the controller for the login window (create a LoginController class)
	            Teacher teacherController = new Teacher(this.teacher_id, classId, 0);
	            loader.setController(teacherController);

	            // Load the FXML file with the specified controller
	            Main.root = loader.load();
	            
	            Main.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	            
	            // Create a scene with the loaded FXML file
	            Main.scene = new Scene(Main.root);
	            
	            Main.stage.setScene(Main.scene);
	            Main.stage.setTitle("Add Announcement");
	            
	            // Show the login window
	            Main.stage.show();
	            
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    
	    
	    public void teacher_add_announcement()
	    {
	        
	        String description = announcementDescription.getText();
	        String title = announcementTitle.getText();
	        
	        String insertAnnouncementQuery = "INSERT INTO announcement (class_id, teacher_id, title, description, time_posted) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = Main.con.prepareStatement(insertAnnouncementQuery)) {
                // Set the values for the placeholder in the query
                preparedStatement.setInt(1, this.teacher_class_id);
                preparedStatement.setInt(2, this.teacher_id);
                preparedStatement.setString(3, title);
                preparedStatement.setString(4, description);
                
                // Use the current timestamp for time_posted
                preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));

                // Execute the insert statement for the announcement table
                preparedStatement.executeUpdate();

                announcementAddedSuccessfully.setText("Announcement Added Successfully");
            } catch (SQLException e) {
                e.printStackTrace();
                announcementAddedSuccessfully.setText("Announcement Added Successfully");
            }
	        
	        
	    }

	private int extractCourseId(String courseInfo) {
		// Example courseInfo: "Course 123: Name: Math"

		// Define a regular expression to match the course ID pattern
		String regex = "Course\\s+(\\d+):";

		// Use a Pattern and Matcher to find the match
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(courseInfo);

		// Check if a match is found
		if (matcher.find()) {
			// Extract and return the course ID from the matched group
			return Integer.parseInt(matcher.group(1));
		} else {
			// If no match is found, handle the error or return a default value
			System.out.println("No valid course ID found in the string: " + courseInfo);
			return -1; // Return a default value or handle the error as needed
		}
	}



	private int extractClassId(String classInfo) {
	        // Example classInfo: "Class 123: Description: ABC, Course: Math"
	    	
	    	// Define a regular expression to match the class ID pattern
	        String regex = "Class\\s+(\\d+):";

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

	    



	    public void teacher_remove_class()
	    {
	    	// Get the selected item from the ListView
	        String selectedClass = classListView.getSelectionModel().getSelectedItem();

	        System.out.print(selectedClass);
	        // Extract class_id from the selected item (you may need to parse it depending on your formatting)
	        int classId = extractClassId(selectedClass);
	        
	        System.out.print(classId);
	        // Call a method to remove the class with the retrieved class_id from the database
	    	
	    	Classroom cl = new Classroom(classId, 0, 0, "");
	    	if (cl.remove_classroom())
	    	{
	    		teacherRemovedClassSuccessfully.setText("Class Removed Successfully");
	    	}
	    	else
	    	{
	    		teacherRemovedClassSuccessfully.setText("Invalid Class ID");
	    	}
	    }
	    

	    public Teacher() {
	        // Initialization code if needed
	    }
	    
	    // Initialize the controller with data (you can fetch data from the database)
	    public void initialize() {

	        this.classes = get_teacher_classes();
	        classListView.getItems().addAll(classes);
	    }


		// Call this method in your initialization logic or wherever you need it
		public void populateCourseListView() {
			this.courses = get_teacher_courses();
			courseListView.getItems().addAll(courses);
		}





	private List<String> get_teacher_courses() {
		List<String> courses = new ArrayList<>();

		// SQL query to retrieve courses based on teacher ID
		String selectCoursesQuery = "SELECT course_id, course_name FROM course";

		try (PreparedStatement preparedStatement = Main.con.prepareStatement(selectCoursesQuery)) {
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					int courseId = resultSet.getInt("course_id");
					String courseName = resultSet.getString("course_name");

					// Format the data as needed
					String courseInfo = "Course " + courseId + ": " +
							"Name: " + courseName;

					//System.out.println(courseInfo);
					courses.add(courseInfo);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return courses;
	}



	private List<String> get_teacher_classes() {
	        List<String> classes = new ArrayList<>();

	        // SQL query to retrieve classes with course details based on teacher ID
	        String selectClassesQuery = "SELECT classroom.class_id, classroom.description, course.course_name " +
	                "FROM classroom " +
	                "JOIN course ON classroom.course_id = course.course_id " +
	                "WHERE classroom.teacher_id = ?";

	        try (PreparedStatement preparedStatement = Main.con.prepareStatement(selectClassesQuery)) {
	            preparedStatement.setInt(1, this.teacher_id);

	            try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                while (resultSet.next()) {
	                    int classId = resultSet.getInt("class_id");
	                    String description = resultSet.getString("description");
	                    String courseName = resultSet.getString("course_name");

	                    // Format the data as needed
	                    String classInfo = "Class " + classId + ": " +
	                    		"Course: " + courseName + ", " +
	                    		"Description: " + description + ", ";
	                            
	                            
	                    
	                    //System.out.print("classInfo");
	                    
	                    classes.add(classInfo);
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return classes;
	    }

	    public void teacher_create_class()
	    {
	        String description = descriptionArea.getText();
	        
	        System.out.print(description);
			System.out.print(this.teacher_class_id);
	        
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
	        
	        Classroom cl = new Classroom (class_id, this.teacher_course_id, this.teacher_id, description);
	        
	        if(cl.add_classroom())
	        {
	        	teacherCreatedClassSuccessfully.setText("Classroom Added Successfully");	
	        }
	        else
	        {
	        	teacherCreatedClassSuccessfully.setText("This teacher already has a classroom with this course");
	        }
	    }
	    
	    @FXML
	    public void show_teacher_create_class_screen(ActionEvent event)
	    {
	    	try {
	            // Load the FXML file for the login window
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("teacher_create_class_screen.fxml"));

				// Get the selected item from the ListView before entering to add announcement screen
				String selectedCourse = courseListView.getSelectionModel().getSelectedItem();
				// Extract class_id from the selected item (you may need to parse it depending on your formatting)
				int courseId = extractCourseId(selectedCourse);


	        	// Set the controller for the login window (create a LoginController class)
	            Teacher teacherController = new Teacher(this.teacher_id, 0, courseId);
	            loader.setController(teacherController);



	            // Load the FXML file with the specified controller
	            Main.root = loader.load();
	            
	            
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
	    public void show_teacher_view_class_screen(ActionEvent event) {
	    	
	        try {
	            // Load the FXML file for the login window
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("teacher_view_classes.fxml"));
	        	
	        	// Set the controller for the login window (create a LoginController class)
	            Teacher teacherController = new Teacher(this.teacher_id);
	            loader.setController(teacherController);

	            // Load the FXML file with the specified controller
	            Main.root = loader.load();
	            
	            teacherController.initialize();
	            
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
	    public void show_teacher_menu_screen(ActionEvent event) {
	    	
	        try {
	            // Load the FXML file for the login window
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("teacher_menu.fxml"));
	        	
	        	// Set the controller for the login window (create a LoginController class)
	            Teacher teacherController = new Teacher(this.teacher_id);
	            loader.setController(teacherController);

	            // Load the FXML file with the specified controller
	            Main.root = loader.load();
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
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    												//hassan's code
}
