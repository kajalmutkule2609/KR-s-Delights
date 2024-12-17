package org.techhub.Repository;

import org.techhub.Model.BillModel;
import org.techhub.Model.DishModel;

import org.techhub.Model.OrderModel;
import java.util.*;

public interface OrderRepository {
	public List<OrderModel> orderPlaced(OrderModel model);

	public Optional<List<OrderModel>> viewAllOrders();

	public List<OrderModel> ViewOrderByTableNo(int tableNo);

	public boolean generateBill(BillModel bill);
}
