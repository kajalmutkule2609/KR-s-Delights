package org.techhub.Service;

import java.util.List;
import java.util.Optional;

import org.techhub.Model.*;
import org.techhub.Repository.*;

public class OrderServiceImp implements OrderService {
	OrderRepository orderRepo = new OrderRepositoryImp();

	@Override
	public List<OrderModel> orderPlaced(OrderModel model) {
		return orderRepo.orderPlaced(model);
	}

	@Override
	public Optional<List<OrderModel>> viewAllOrders() {
		return orderRepo.viewAllOrders();
	}

	@Override
	public List<OrderModel> ViewOrderByTableNo(int tableNo) {
		return orderRepo.ViewOrderByTableNo(tableNo);
	}

	@Override
	public boolean generateBill(BillModel bill) {
		return orderRepo.generateBill(bill);
	}

//	@Override
//	public List<OrderModel> getOrdersByOrderNo(int orderNo) {
//		return orderRepo.getOrdersByOrderNo(orderNo);
//	}

	@Override
	public boolean getBillByOrderNo(BillModel bill, int orderNo) {
		return orderRepo.getBillByOrderNo(bill, orderNo);
		
	}

	@Override
	public boolean completePayment(double billAmount,int tableNo) {
		return orderRepo.completePayment(billAmount,tableNo);
	}

	@Override
	public boolean checkIfCustomerIsNew(String email) {
		return orderRepo.checkIfCustomerIsNew(email);
	}

	@Override
	public boolean getFeedback(FeedBackModel model) {
		return orderRepo.getFeedback(model);
	}

	@Override
	public boolean checkCustomerOrders(String email) {
		return orderRepo.checkCustomerOrders(email);
	}

}
