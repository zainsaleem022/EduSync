package com.example.fxproject;
import java.sql.*;











////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//hassan's code



public class Classroom {

	private int class_id;
	private int course_id;
	private int teacher_id;
	private String description;
	
	
	Classroom(int classId, int courseId, int teacherId, String desc)
	{
		this.class_id = classId;
		this.course_id = courseId;
		this.teacher_id = teacherId;
		this.description = desc;
	}
	
	
	public boolean add_classroom()
	{
		PreparedStatement preparedStatement = null;

	    try {
	        // SQL query to insert values into the classroom table
	        String insertClassroomQuery = "INSERT INTO classroom (class_id, course_id, teacher_id, description) VALUES (?, ?, ?, ?)";

	        // Prepare the statement with the query
	        preparedStatement = Main.con.prepareStatement(insertClassroomQuery);

	        // Set the values for the placeholder in the query
	        preparedStatement.setInt(1, this.class_id);
	        preparedStatement.setInt(2, this.course_id);
	        preparedStatement.setInt(3, this.teacher_id);
	        preparedStatement.setString(4, this.description);

	        // Execute the insert statement for the classroom table
	        preparedStatement.executeUpdate();

	        System.out.println("Classroom added successfully.");
	        return true; // Return true if classroom is added successfully
	    } catch (SQLIntegrityConstraintViolationException e) {
	        // Handle duplicate entry error
	        System.out.println("Duplicate Error/This teacher is already assigned to this course.");
	        return false; // Return false if there is a duplicate entry
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false; // Return false for other SQL errors
	    }
	}
	
	
	public boolean remove_classroom()
	{
		  // SQL query to delete a classroom based on the class_id
        String deleteClassroomQuery = "DELETE FROM classroom WHERE class_id = ?";

        try (PreparedStatement deleteClassroomStatement = Main.con.prepareStatement(deleteClassroomQuery)) {
            // Set the value for the placeholder in the query
            deleteClassroomStatement.setInt(1, this.class_id);

            // Execute the delete statement for the classroom table
            int rowsAffected = deleteClassroomStatement.executeUpdate();

            // Check if any rows were affected, indicating successful deletion
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Handle the exception as needed
        }
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//hassan's code
}
