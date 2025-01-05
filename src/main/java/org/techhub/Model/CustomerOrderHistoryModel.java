package org.techhub.Model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOrderHistoryModel {
	private int id;
	private String email;
	private int count;
}
