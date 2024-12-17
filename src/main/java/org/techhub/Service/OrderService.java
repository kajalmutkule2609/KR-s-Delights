package org.techhub.Service;

import java.util.List;
import java.util.Optional;

import org.techhub.Model.*;

public interface OrderService {
	public List<OrderModel> orderPlaced(OrderModel model);

	public Optional<List<OrderModel>> viewAllOrders();

	public List<OrderModel> ViewOrderByTableNo(int tableNo);

	public boolean generateBill(BillModel bill);
	
}
