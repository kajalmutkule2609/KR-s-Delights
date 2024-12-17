package org.techhub.Repository;

import java.util.*;
import org.techhub.Model.*;

public class MenuRepositoryImp extends DBUser implements MenuRepository {
	List<MenuModel> list = new ArrayList<>();

	@Override
	public Optional<List<MenuModel>> showMenus() {
		list = new ArrayList<MenuModel>();
		String Query = "Select * from MenuMaster";
		try {
			stmt = conn.prepareStatement(Query);
			rs = stmt.executeQuery();
			while (rs.next()) {
				list.add(new MenuModel(rs.getInt(1), rs.getString(2)));
			}
			return Optional.ofNullable(list);
		} catch (Exception e) {
			System.out.println("Error Is:" + e.getMessage());
		}
		return Optional.empty();
	}

	@Override
	public int getMenuIdByMenuType(String type) {
		try {
			stmt = conn.prepareStatement(Query.getMenuIdByType);
			stmt.setString(1, type);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			} else {
				return -1;
			}
		} catch (Exception ex) {
			System.out.println("Error Is:" + ex.getMessage());
		}
		return -1;
	}

	@Override
	public boolean addMenuType(MenuModel model) {
		try {
			stmt = conn.prepareStatement("Insert into MenuMaster Values('0',?)");
			stmt.setInt(1, model.getMenuId());
			stmt.setString(1, model.getMenyType());
			int result = stmt.executeUpdate();
			return result > 0 ? true : false;
		} catch (Exception ex) {
			System.out.println("Error Is:" + ex.getMessage());
		}
		return false;
	}

	@Override
	public String searchMenuType(String type) {
		try {
			stmt = conn.prepareStatement("Select * from MenuMaster Where menuType=?");
			stmt.setString(1, type);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return type;
			} else {
				return null;
			}
		} catch (Exception ex) {
			System.out.println("Error is:" + ex.getMessage());
		}
		return null;
	}

	@Override
	public boolean deleteMenuType(String type) {
		try {
			stmt = conn.prepareStatement("delete from menuMaster where menuType=?");
			stmt.setString(1, type);
			int result = stmt.executeUpdate();
			return result > 0 ? true : false;
		} catch (Exception ex) {
			System.out.println("Error is:" + ex.getMessage());
		}
		return false;
	}

}
