package com.example.fxproject;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;








////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//hassan's code








public class Course {
	
	private int course_id;
	private String course_name;
	private String description;
	
	
    Course(int courseId, String name, String desc)
    {
    	this.course_id = courseId;
    	this.course_name = name;
    	this.description = desc;
    }
    
    
    
    public void add_course()
    {
    	// SQL query to insert values into the course table
        String insertCourseQuery = "INSERT INTO course (course_id, course_name, description) VALUES (?, ?, ?)";

        // Prepare the statement with the query
        try (PreparedStatement insertCourseStatement = Main.con.prepareStatement(insertCourseQuery)) {
            // Set the values for the placeholder in the query
            insertCourseStatement.setInt(1, this.course_id);
            insertCourseStatement.setString(2, this.course_name);
            insertCourseStatement.setString(3, this.description);

            // Execute the insert statement for the course table
            insertCourseStatement.executeUpdate();

            System.out.println("Course added successfully.");
        }
        catch (SQLException e) {
        	e.printStackTrace();
        }
    }
    
    
    public boolean remove_course()
    {
    	// SQL query to remove a course from the course table
        String removeCourseQuery = "DELETE FROM course WHERE course_id = ?";

        try (PreparedStatement removeCourseStatement = Main.con.prepareStatement(removeCourseQuery)) {
            // Set the value for the placeholder in the query
            removeCourseStatement.setInt(1, this.course_id);

            // Execute the delete statement for the course table
            int rowsAffected = removeCourseStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Course removed successfully.");
                return true;
            } else {
                System.out.println("No course found with the given course ID.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//hassan's code
    
    
    
}
