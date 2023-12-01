package com.example.fxproject;

import java.io.IOException;
import javafx.scene.Node;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
import javafx.scene.control.Button;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
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



////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//hassan's code


public class User implements Initializable{
	int user_id;
	String username;
	String password;
	String email;
	String role;
	private static int loginUnsuccessful = 0;
	
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
    private Button loginButton;
    @FXML
    private Label userRegisteredSuccessfully;
    @FXML
    private Label errorMessageLabel;
    
    private String[] options = {"Admin", "Teacher", "Student", "Parent"};
    
    private boolean loginSuccessful;
    private String userRole;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // Initialize the ChoiceBox with role options
    	if (roleChoiceBox != null) {
            roleChoiceBox.getItems().addAll(options);
        }
    	
    	if (errorMessageLabel != null)
    	{
    		if(loginUnsuccessful == 1)
            {
    			errorMessageLabel.setText("Enter Correct Credentials");   
            }
    	}
    }
    
    
    
    
    
    public boolean remove_user(int userId) {

        // SQL query to remove a user from the user table
        String removeUserQuery = "DELETE FROM user WHERE id = ?";

        try (PreparedStatement removeUserStatement = Main.con.prepareStatement(removeUserQuery)) {
            // Set the value for the placeholder in the query
            removeUserStatement.setInt(1, userId);

            // Execute the delete statement for the user table
            int rowsAffected = removeUserStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User removed successfully.");
                return true;
            } else {
                System.out.println("Invalid User Id");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    
    
    @FXML
    public void registerButtonClicked(ActionEvent event) {
        username = usernameField.getText();
        password = passwordField.getText();
        email = emailField.getText();
        role = roleChoiceBox.getValue();
        
        if(register_user(username, password, email, role))
        {
        	userRegisteredSuccessfully.setText("User registered successfully.");
        }
        else
        {
        	userRegisteredSuccessfully.setText("Invalid Information / User Already Registered");
        }
        
    }
    
    
    public boolean register_user(String username, String password, String email, String role)
    {
        try {
            // SQL query to get the max ID from the user table
            String getMaxIdQuery = "SELECT MAX(id) FROM user";
            int maxUserId = 0;

            try (PreparedStatement getMaxIdStatement = Main.con.prepareStatement(getMaxIdQuery);
                 ResultSet resultSet = getMaxIdStatement.executeQuery()) {
                if (resultSet.next()) {
                    maxUserId = resultSet.getInt(1);
                }
            }

            // Increment the max ID to generate a new ID for the current user
            int newUserId = maxUserId + 1;

            // SQL query to insert values into the user table
            String insertUserQuery = "INSERT INTO user (id, username, password, email, role) VALUES (?, ?, ?, ?, ?)";

            // Prepare the statement with the query
            try (PreparedStatement insertUserStatement = Main.con.prepareStatement(insertUserQuery)) {
                // Set the values for the placeholder in the query
                insertUserStatement.setInt(1, newUserId);
                insertUserStatement.setString(2, username);
                insertUserStatement.setString(3, password);
                insertUserStatement.setString(4, email);
                insertUserStatement.setString(5, role);

                // Execute the insert statement for the user table
                insertUserStatement.executeUpdate();

                // Insert into additional tables based on the role
                switch (role) {
                    case "Admin":
                        insertIntoAdminTable(newUserId, username, password, email);
                        break;
                    case "Teacher":
                        insertIntoTeacherTable(newUserId, username, password, email);
                        break;
                    case "Student":
                        insertIntoStudentTable(newUserId, username, password, email);
                        break;
                    case "Parent":
                        insertIntoParentTable(newUserId, username, password, email);
                        break;
                    default:
                        // Handle other cases if needed
                }
                
                System.out.println("User registered successfully.");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void insertIntoAdminTable(int userId, String username, String password, String email) throws SQLException {
    	
        String insertAdminQuery = "INSERT INTO admin (id, username, password, email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = Main.con.prepareStatement(insertAdminQuery)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, email);
            preparedStatement.executeUpdate();
        }
    }

    private void insertIntoTeacherTable(int userId, String username, String password, String email) throws SQLException {
    	
        String insertTeacherQuery = "INSERT INTO teacher (id, username, password, email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = Main.con.prepareStatement(insertTeacherQuery)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, email);
            preparedStatement.executeUpdate();
        }
    }

    private void insertIntoStudentTable(int userId, String username, String password, String email) throws SQLException {
    	
    	String insertStudentQuery = "INSERT INTO student (id, username, password, email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = Main.con.prepareStatement(insertStudentQuery)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, email);
            preparedStatement.executeUpdate();
        }
    }

    private void insertIntoParentTable(int userId, String username, String password, String email) throws SQLException {
    	
        String insertParentQuery = "INSERT INTO parent (id, username, password, email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = Main.con.prepareStatement(insertParentQuery)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, email);
            preparedStatement.executeUpdate();
        }
    }

    
    @FXML
    public void showRegisterWindow(ActionEvent event) {
    	Main m = new Main();
    	m.start(Main.stage);
    }
    
    
    @FXML
    public void showLoginWindow(ActionEvent event) {
    	
        try {
            // Load the FXML file for the login window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        	
        	// Set the controller for the login window (create a LoginController class)
            User loginController = new User();
            loader.setController(loginController);

            // Load the FXML file with the specified controller
            Main.root = loader.load();
            Main.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            
            // Create a scene with the loaded FXML file
            Main.scene = new Scene(Main.root);
            
            Main.stage.setScene(Main.scene);
            Main.stage.setTitle("Login");
            
            // Show the login window
            Main.stage.show();
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    @FXML
    public void loginButtonClicked(ActionEvent event) {

        String email = emailField.getText();
        String password = passwordField.getText();

        // Updated login query to retrieve user ID along with the role
        String loginQuery = "SELECT id, role FROM user WHERE email = ? AND password = ?";

        try (PreparedStatement preparedStatement = Main.con.prepareStatement(loginQuery)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Login successful
                    loginSuccessful = true;
                    int userId = resultSet.getInt("id");
                    userRole = resultSet.getString("role");
                    errorMessageLabel.setText("Logged in Successfully");

                    // Now you have the user ID (userId) of the logged-in user
                    System.out.println("Logged-in User ID: " + userId);

                    // Show screens according to roles after logging in
                    if (userRole.equals("Admin"))
                    {
                        Admin a = new Admin();
                        a.show_admin_menu_screen(event);
                    }
                    else if (userRole.equals("Teacher"))
                    {
                        Teacher t = new Teacher(userId);
                        t.show_teacher_menu_screen(event);
                    }
                    else if (userRole.equals("Student"))
                    {
                        Student s = new Student(userId);
                        s.show_student_main_menu(event);
                    }
                    else if (userRole.equals("Parent"))
                    {
                        // Parent p = new Parent();
                        // p.show_parent_menu_screen(event);
                    }

                } else {
                    // Login failed
                    loginSuccessful = false;
                    loginUnsuccessful = 1;
                    userRole = "None"; // Set an appropriate default value
                    showLoginWindow(event);

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
 
    
    
    
    
    
    
    
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//hassan's code
    
    
}



