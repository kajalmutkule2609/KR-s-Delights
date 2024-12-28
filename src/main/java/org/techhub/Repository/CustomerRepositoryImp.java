package org.techhub.Repository;

import org.techhub.Model.*;
import org.techhub.Repository.*;
import java.util.*;

public class CustomerRepositoryImp extends DBUser implements CustomerRepository {
	List<CustomerModel> list = new ArrayList<>();

	@Override
	public boolean isRegisteredCustomer(CustomerModel model) {
		String Query = "{call addUser(?,?,?,?,?,?)}";
		try {
			stmt = conn.prepareStatement(Query);
			stmt.setString(1, model.getCustName());
			stmt.setString(2, model.getCustEmail());
			stmt.setString(3, model.getCustContact());
			stmt.setString(4, model.getPassword());
			stmt.setString(5, model.getAddress());
			stmt.setString(6, model.getRole());
			int result = stmt.executeUpdate();
			return result > 0 ? true : false;
		} catch (Exception e) {
			System.out.println("Error is:" + e.getMessage());
			return false;
		}

	}

	@Override
	public Optional<List<CustomerModel>> getAllCustomers() {
		try {
			list = new ArrayList<>();
			stmt = conn.prepareStatement("Select * from UserMaster where Role='Customer'");
			rs = stmt.executeQuery();
			while (rs.next()) {
				list.add(new CustomerModel(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7)));
			}
			return Optional.ofNullable(list);
		} catch (Exception ex) {
			System.out.println("Error Is:" + ex.getMessage());
		}
		return Optional.empty();
	}

	@Override
	public String getCustomerByEmail(String email) {
		try {
			stmt = conn.prepareStatement(Query.getUserByEmail);
			stmt.setString(1, email);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return email;
			}
		} catch (Exception ex) {
			System.out.println("Error is:" + ex.getMessage());
		}
		return null;
	}

	@Override
	public boolean deleteCustomer(String email) {
		try {
			stmt = conn.prepareStatement("call deleteUser(?)");
			stmt.setString(1, email);
			int value = stmt.executeUpdate();
			if (value > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			System.out.println("Error Is:" + ex.getMessage());
			return false;
		}

	}
	@Override
	public CustomerModel getCustomer(String Email) {
		String query="select * from UserMaster where Email=?";
		try {
			stmt=conn.prepareStatement(query);
			stmt.setString(1,Email);
			rs=stmt.executeQuery();
			if(rs.next()) {
				return new CustomerModel(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7));
			}
		}
		catch(Exception ex) {
			System.out.println("Error Is:"+ex.getMessage());
		}
		
		return null;
	}

}
