package org.techhub.Repository;

import org.techhub.Model.*;
import java.util.*;

public interface OrderRepository {
	public List<OrderModel> orderPlaced(OrderModel model);

	public Optional<List<OrderModel>> viewAllOrders();

	public List<OrderModel> ViewOrderByTableNo(int tableNo);

	public boolean generateBill(BillModel bill);
	
//	public List<OrderModel> getOrdersByOrderNo(int orderNo);
	
	public boolean getBillByOrderNo(BillModel bill, int orderNo);
	
	public boolean completePayment(double billAmount,int tableNo);
	
	public boolean checkIfCustomerIsNew(String email);
	
	public boolean getFeedback(FeedBackModel model);
}
