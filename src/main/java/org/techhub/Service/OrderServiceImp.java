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

}
