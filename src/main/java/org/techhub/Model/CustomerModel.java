package org.techhub.Model;

import java.sql.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerModel {
	private int custId;
	private String custName;
	private String custEmail;
	private String custContact;
	private String password;
	private String address;
	private String role;
}
