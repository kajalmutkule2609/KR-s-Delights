package org.techhub.Repository;

public class Query {
	public static String getCustByEmail = "Select *  from CustomerMaster where Email=?";
	public static String getStaffByEmail = "Select *  from StaffMaster where Email=?";
	public static String deleteStaffByEmail = "Delete from StaffMaster Where Email=?";
	public static String getMenuIdByType = "Select * from MenuMaster Where menuType=?";
}
