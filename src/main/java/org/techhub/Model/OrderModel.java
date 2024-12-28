package org.techhub.Model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderModel extends TableModel {
	private int orderId;
	private String dishName;
	private int quantity;
	private int price;
	private int tableNo;
	private String orderStatus;
}
