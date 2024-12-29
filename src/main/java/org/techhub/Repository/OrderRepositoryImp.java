package org.techhub.Repository;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.techhub.ClientApp.RestaurantOrderManagementSystem;
import org.techhub.Model.BillModel;

import org.techhub.Model.DishModel;

import org.techhub.Model.OrderModel;
import java.util.*;
import java.sql.*;

public class OrderRepositoryImp extends DBUser implements OrderRepository {

	private static Logger logger = Logger.getLogger(OrderRepositoryImp.class);
	static {
		logger.info("Initializing Log4j");
		PropertyConfigurator.configure(
				"C:\\Users\\15s-eq0024au\\Techhubworkspace\\RestaurantOrderManagementSystem\\src\\main\\resources\\log4j.properties");
	}
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
			logger.error("error while inserting orders..");
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
				list.add(new OrderModel(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5),rs.getString(6)));
			}
			return Optional.ofNullable(list);
		} catch (Exception ex) {
			System.out.println("Error Is:" + ex.getMessage());
			logger.error("Error while Fetching orders..");
		}
		return Optional.empty();
	}

	@Override
	public List<OrderModel> ViewOrderByTableNo(int tableNo) {


		String Query = "Select * from OrderMaster where tableNo = ? and OrderStatus='Pending'";
		list = new ArrayList<OrderModel>();
		try {
			stmt = conn.prepareStatement(Query);
			stmt.setInt(1, tableNo);
			rs = stmt.executeQuery();
			while (rs.next()) {
				list.add(new OrderModel(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5),rs.getString(6)));
			}
		} catch (Exception ex) {
			System.out.println("Error Is:" + ex.getMessage());
			logger.error("error while fetching Orders by tableNo...");
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
			logger.error("Error in Inserting Bill Data.");
		}
		return false;
	}
	public List<OrderModel> getOrdersByOrderNo(int orderNo) {

	   try {
		   list=new ArrayList<OrderModel>();
		    stmt= conn.prepareStatement("Select * FROM OrderMaster WHERE orderId=orderNo and orderStatus='pending'");
		    stmt.setInt(1, orderNo);
		    rs=stmt.executeQuery();
		    while(rs.next()) {
		    	list.add(new OrderModel(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4),rs.getInt(5),rs.getString(6)));
		    }
		    return list;
		    
	   }
	   catch(Exception ex) {
		   System.out.println("Error Is:"+ex.getMessage());
		   logger.error("error in getting orders by orderno");
	   }
	 return null;
	}
	
	public boolean getBillByOrderNo(BillModel bill, int orderNo) {

	    try {
	        stmt = conn.prepareStatement("Select * From Bill Where OrderId=? and orderStatus='pending'");
	        stmt.setInt(1, orderNo);
	        rs = stmt.executeQuery();
	        while (rs.next()) {
	            bill.setOrderId(rs.getInt("orderId"));
	            bill.setSubTotal(rs.getDouble("subTotal"));
	            bill.setGST(rs.getDouble("gst"));
	            bill.setServiceCharges(rs.getDouble("serviceCharges"));
	            bill.setTotal(rs.getDouble("totalAmount"));
	        }
	        return true;
	    } catch (Exception e) {
	        System.out.println("Error retrieving bill by order number: " + e.getMessage());
	        logger.error("Error is Retrieving Bill...");
	    }
	    return false;
	}

	@Override
	public boolean completePayment(double billAmount,int tableNo) {

	    String query = "UPDATE Bill b "
	    		+"JOIN OrderMaster o ON b.orderId = o.orderId "
	    		+"JOIN TableMaster t ON t.tableNo = o.tableNo "
	    		+"SET b.billstatus = IF(b.TotalBill = ?, 'paid', b.billstatus), "
	    		    +"o.orderStatus = 'complete', "
	    		   +"t.userId = NULL, "
	    		    +"t.status = 0 "
	    		+"WHERE t.tableNo = ?";
	    
	    
	    try {
	    	stmt = conn.prepareStatement(query);
	        stmt.setDouble(1, billAmount);
	        stmt.setInt(2, tableNo);


	        int result = stmt.executeUpdate();
	       if(result>0) {
	    	   query="UPDATE OrderMaster o "
	   	    		+ "JOIN TableMaster t ON t.tableNo = o.tableNo "
	   	    		+ "SET o.orderStatus = 'complete' "
	   	    		+ "WHERE t.tableNo = ? ";
	    	   
	    	   stmt = conn.prepareStatement(query);
		        stmt.setInt(1, tableNo);
		        
		        result=stmt.executeUpdate();
		        return result>0;
	    	   
	       }
	    } catch (Exception ex) {
	        System.out.println("Error Is: " + ex.getMessage());
	        logger.error("Error while completing Payment");
	    }
	    return false;
	}

}

