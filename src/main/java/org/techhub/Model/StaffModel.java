package org.techhub.Model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffModel {
	public static final String securityKey = "Admin@123";
	private int staffId;
	private String staffName;
	private String email;
	private String contact;
	private String Password;
	private String address;
	private String role;
	
}
