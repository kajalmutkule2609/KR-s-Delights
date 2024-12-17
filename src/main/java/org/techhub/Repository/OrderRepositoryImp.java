package org.techhub.Repository;

import org.techhub.Model.BillModel;
import org.techhub.Model.DishModel;

import org.techhub.Model.OrderModel;
import java.util.*;

public class OrderRepositoryImp extends DBUser implements OrderRepository {

	List<OrderModel> list = new ArrayList<OrderModel>();

	@Override
	public List<OrderModel> orderPlaced(OrderModel model) {
		String Query = "Insert into OrderMaster (dishName, quantity, price, tableNo) Values(?,?,?,?)";
		list = new ArrayList<OrderModel>();
		try {
			stmt = conn.prepareStatement(Query);
			stmt.setString(1, model.getDishName());
			stmt.setInt(2, model.getQuantity());
			stmt.setInt(3, model.getPrice());
			stmt.setInt(4, model.getTableNo());

			int result = stmt.executeUpdate();

			if (result > 0) {
				return list;
			} else {
				return null;
			}
		} catch (Exception ex) {
			System.out.println("Error Is:" + ex.getMessage());
		}
		return null;
	}

	@Override
	public Optional<List<OrderModel>> viewAllOrders() {
		String Query = "Select * from OrderMaster Order by OrderId asc";
		list = new ArrayList<OrderModel>();
		try {
			stmt = conn.prepareStatement(Query);
			rs = stmt.executeQuery();
			while (rs.next()) {
				list.add(new OrderModel(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5)));
			}
			return Optional.ofNullable(list);
		} catch (Exception ex) {
			System.out.println("Error Is:" + ex.getMessage());
		}
		return Optional.empty();
	}

	@Override
	public List<OrderModel> ViewOrderByTableNo(int tableNo) {
		String Query = "Select * from OrderMaster where tableNo = ?";
		list = new ArrayList<OrderModel>();
		try {
			stmt = conn.prepareStatement(Query);
			stmt.setInt(1, tableNo);
			rs = stmt.executeQuery();
			while (rs.next()) {
				list.add(new OrderModel(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5)));
			}
		} catch (Exception ex) {
			System.out.println("Error Is:" + ex.getMessage());
		}
		return list;
	}

	@Override
	public boolean generateBill(BillModel bill) {
		String query = "INSERT INTO Bill (orderId, billDate, subtotal, Gst,ServiceCharges, totalBill) VALUES (?, ?, ?, ?, ?, ?)";
		try {
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, bill.getOrderId());
			stmt.setDate(2, java.sql.Date.valueOf(bill.getCurrentDate()));
			stmt.setDouble(3, bill.getSubTotal());
			stmt.setDouble(4, bill.getGST());
			stmt.setDouble(5, bill.getServiceCharges());
			stmt.setDouble(6, bill.getTotal());
			int rs = stmt.executeUpdate();
			return rs > 0 ? true : false;
		} catch (Exception e) {
			System.out.println("Error inserting bill: " + e.getMessage());
		}
		return false;
	}

}
