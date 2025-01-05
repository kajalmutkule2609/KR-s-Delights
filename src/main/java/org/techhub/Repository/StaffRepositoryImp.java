package org.techhub.Repository;

import java.util.*;
import org.techhub.Model.*;

public class StaffRepositoryImp extends DBUser implements StaffRepository {
	List<StaffModel> list = new ArrayList<>();

	@Override
	public boolean isRegisteredStaff(StaffModel model) {
		try {
			cstmt = conn.prepareCall("{call addUser(?,?,?,?,?,?)}");
			cstmt.setString(1, model.getStaffName());
			cstmt.setString(2, model.getEmail());
			cstmt.setString(3, model.getContact());
			cstmt.setString(4, model.getPassword());
			cstmt.setString(5, model.getAddress());
			cstmt.setString(6, model.getRole());
			int result = cstmt.executeUpdate();
			return result > 0 ? true : false;
		} catch (Exception ex) {
			System.out.println("Error is:" + ex.getMessage());
			return false;
		}
	}

	@Override
	public Optional<List<StaffModel>> getAllStaff() {
		try {
			list = new ArrayList<>();
			stmt = conn.prepareStatement("Select * from UserMaster where not Role='Customer'");
			rs = stmt.executeQuery();
			while (rs.next()) {
				list.add(new StaffModel(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7)));
			}
			return Optional.ofNullable(list);
		} catch (Exception ex) {
			System.out.println("Error Is:" + ex.getMessage());
		}
		return Optional.empty();
	}

	@Override
	public String getStaffByEmail(String email) {
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
	public boolean deleteStaff(String email) {
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
	public String validateUser(String email, String password) {
		try {
			stmt = conn.prepareStatement("Select Role from UserLogin where Email=? and Password=?");
			stmt.setString(1, email);
			stmt.setString(2, password);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getString("Role");
			} else {
				return "Invalid User";
			}
		} catch (Exception ex) {
			System.out.println("Error Is:" + ex.getMessage());
		}
		return null;
	}

	@Override
	public boolean forgotPassword(String email,String password) {
		try {
			stmt=conn.prepareStatement("call forgotPass(?,?)");
			stmt.setString(1, email);
			stmt.setString(2, password);
			int result=stmt.executeUpdate();
			return result>0;
		}
		catch(Exception ex) {
			System.out.println("Error Is:"+ex.getMessage());
		}
		
		return false;
	}

}
