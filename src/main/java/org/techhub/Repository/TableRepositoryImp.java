package org.techhub.Repository;

import org.techhub.Model.TableModel;
import java.util.*;

public class TableRepositoryImp extends DBUser implements TableRepository {
	List<TableModel> list = new ArrayList<>();

	@Override
	public boolean showAvailableTables() {
		try {
			int cnt=1;
			stmt = conn.prepareStatement(Query.getAvailableTables);
			rs = stmt.executeQuery();
			System.out.println("Available tables:");
			while (rs.next()) {
				if (cnt % 10 == 0) {
	                System.out.println(rs.getString("tableno"));
	            } else {
	                System.out.print(rs.getString("tableno") + "\t");
	            }
	            cnt++;

			}
			return true;
		} catch (Exception ex) {
			System.out.println("Error is:" + ex.getMessage());
		}
		return false;
	}

	@Override
	public boolean reserveTable(int tableNo, String email) {
		try {
			stmt = conn.prepareStatement(Query.getUserIdByEmail);
			stmt.setString(1, email);
			rs = stmt.executeQuery();
			if (rs.next()) {
				int userId = rs.getInt(1);
				try {
					stmt = conn.prepareStatement(Query.updateStatus);
					stmt.setInt(1, userId);
					stmt.setInt(2, tableNo);

					int result = stmt.executeUpdate();
					if (result >0) {
						return true;
					} else {
						System.out.println("Error: Table " + tableNo + " not found or already occupied");
						return false;
					}
				} catch (Exception ex) {
					System.out.println("Error Is:" + ex.getMessage());
				}

			}

		} catch (Exception ex) {
			System.out.println("Error Is:" + ex.getMessage());
		}
		return false;
	}

	@Override
	public String getEmailByTableNo(int tableNo) {
		String query="SELECT u.Email "
				+ "FROM usermaster "
				+ "JOIN tablemaster t ON u.userId = t.userId "
				+ "WHERE t.tableNo = ?";
		try {
			stmt=conn.prepareStatement(query);
			stmt.setInt(1, tableNo);
			rs=stmt.executeQuery();
			if(rs.next()) {
				return rs.getString("email");
			}
			else {
				return null;
			}
		}
		catch(Exception ex) {
			System.out.println("Error Is:"+ex.getMessage());
		}
		return null;
	}

}
