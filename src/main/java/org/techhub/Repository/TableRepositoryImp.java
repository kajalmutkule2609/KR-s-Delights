package org.techhub.Repository;

import org.techhub.Model.TableModel;
import java.util.*;

public class TableRepositoryImp extends DBUser implements TableRepository {
	List<TableModel> list = new ArrayList<>();

	@Override
	public boolean showAvailableTables() {
		String query = "SELECT tableno FROM tableMaster WHERE occupied = 'no'";
		try {
			stmt = conn.prepareStatement(query);
			rs = stmt.executeQuery();
			System.out.println("Available tables:");
			while (rs.next()) {
				System.out.print(rs.getString("tableno") + "\t");
			}
			return true;
		} catch (Exception ex) {
			System.out.println("Error is:" + ex.getMessage());
		}
		return false;
	}

	@Override
	public boolean reserveTable(int tableNo, String email) {
		String Query = "Select custId from CustomerMaster where Email=?";
		try {
			stmt = conn.prepareStatement(Query);
			stmt.setString(1, email);
			rs = stmt.executeQuery();
			if (rs.next()) {
				int custId = rs.getInt(1);
				Query = "Update TableMaster set Occupied='YES', custId=? where tableNo=?";
				try {
					stmt = conn.prepareStatement(Query);
					stmt.setInt(1, custId);
					stmt.setInt(2, tableNo);

					int result = stmt.executeUpdate();
					if (result == 1) {
						System.out.println("Table " + tableNo + " reserved for customer " + custId);
					} else {
						System.out.println("Error: Table " + tableNo + " not found or already occupied");
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

}
