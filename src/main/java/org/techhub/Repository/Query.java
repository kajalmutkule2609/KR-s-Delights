package org.techhub.Repository;

public class Query {
	public static String getUserByEmail = "Select *  from UserMaster where Email=?";
	//public static String deleteStaffByEmail = "Delete from StaffMaster Where Email=?";
	public static String getMenuIdByType = "Select * from MenuMaster Where menuType=?";
}
