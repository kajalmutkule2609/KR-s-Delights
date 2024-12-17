package org.techhub.Model;

import java.sql.Date;
import java.time.LocalDate;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class BillModel extends OrderModel {
	private int billId;
	private int orderId;
	private LocalDate currentDate = LocalDate.now();
	private double subTotal;
	private double GST;
	private double serviceCharges;
	private double total;
}
