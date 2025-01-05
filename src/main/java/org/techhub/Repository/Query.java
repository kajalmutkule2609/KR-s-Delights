package org.techhub.Repository;

public class Query {
	public static String getUserByEmail = "Select *  from UserMaster where Email=?";
	
	public static String getMenuIdByType = "Select * from MenuMaster Where menuType=?";
	
	public static String getAllCustomers = "Select * from UserMaster where Role='Customer'";
	
	public static String getAllMenu = "Select * from MenuMaster";
	
	public static String addMenu = "Insert into MenuMaster Values('0',?)";
	
	public static String getAllMenuByType = "Select * from MenuMaster Where menuType=?";
	
	public static String deleteMenuByType = "delete from menuMaster where menuType=?";
	
	public static String getCustomerByEmail = "select * from UserMaster where Email=?";
	
	public static String addDish = "Insert into DishMaster (dishName,Price,category) values(?,?,?)";
	
	public static String addDishIdandMenuId = "INSERT INTO MenuDishJoin VALUES ((SELECT menuId FROM Menumaster WHERE menuType = ?),(SELECT MAX(dishId) FROM DishMaster WHERE dishName = ?))";
	
	public static String showAllDishes = "Select d.dishId,d.dishName,d.price,d.category from DishMaster d"
			+ " inner join menudishjoin mdj on mdj.dishId=d.dishId"
			+ " inner join MenuMaster m on m.menuId=mdj.menuId where m.menuType=?";
	
	public static String searchDishByTypeAndName = "Select d.dishName from DishMaster d"
			+ " inner join MenuDishJoin mdj on d.dishId=mdj.dishId" + " inner join MenuMaster m on m.menuId=mdj.menuId "
			+ "where m.menuType=? and d.dishName=?";
	
	public static String deleteDishByTypeAndName = "DELETE d FROM DishMaster d"
			+ " INNER JOIN MenuDishJoin mdj ON d.dishId = mdj.dishId"
			+ " INNER JOIN MenuMaster m ON m.menuId = mdj.menuId" + " WHERE m.menuType = ? AND d.dishName = ?";
	
	public static String getDishById = "SELECT * FROM DishMaster WHERE dishId = ?";
	
	public static String getAvailableTables= "SELECT tableno FROM tableMaster WHERE status=0";
	
	public static String getUserIdByEmail="Select userId from UserMaster where Email=?";
	
	public static String updateStatus="Update TableMaster set status=1, userId=? where tableNo=? and status=0";
	
	public static String addOrder= "Insert into OrderMaster (dishName, quantity, price, tableNo) Values(?,?,?,?)";
	
	public static String getOrderById="Select * from OrderMaster Order by OrderId asc";
	
	public static String viewPendingOrdersByTableNo="Select * from OrderMaster where tableNo = ? and OrderStatus='Pending'";
	
	public static String addBill="INSERT INTO Bill (orderId, billDate, subtotal, Gst,ServiceCharges, totalBill) VALUES (?, ?, ?, ?, ?, ?)";
	
	public static String getBillByOrderNo="Select * From Bill Where OrderId=? and orderStatus='pending'";
	
	public static String updateBillOrderAndTableStatus="UPDATE Bill b "
    		+"JOIN OrderMaster o ON b.orderId = o.orderId "
    		+"JOIN TableMaster t ON t.tableNo = o.tableNo "
    		+"SET b.billstatus = IF(b.TotalBill = ?, 'paid', b.billstatus), "
    		    +"o.orderStatus = 'complete', "
    		   +"t.userId = NULL, "
    		    +"t.status = 0 "
    		+"WHERE t.tableNo = ?";
	
	public static String updateOrderStatus="UPDATE OrderMaster o "
	    		+ "JOIN TableMaster t ON t.tableNo = o.tableNo "
	    		+ "SET o.orderStatus = 'complete' "
	    		+ "WHERE t.tableNo = ? ";
	   
	public static String checkCustomerIsNew="select * from CustomerHistory where Email=?";
	
	public static String addFeedback="insert into CustomerFeedback(feedback,rating,Email) values(?,?,?)";
}
