package org.techhub.Model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
	private int userId;
	private String email;
	private String password;
	private String Role;
}
