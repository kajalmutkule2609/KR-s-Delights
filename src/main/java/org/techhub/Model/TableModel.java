package org.techhub.Model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableModel extends CustomerModel {
	private int tableNo;
	private int custId;
	private String Occupied;

}
