package org.techhub.Repository;

import java.io.*;
import java.util.*;

import org.techhub.Model.DishModel;

public class DishRepositoryImp extends DBUser implements DishRepository {
	List<DishModel> list = new ArrayList<>();

	@Override
	public boolean addBulkDishes(String menuType, String fileName) {


		try {
			String type = menuType + ".csv";
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line;
			boolean success = true;

			if (type.equalsIgnoreCase(fileName)) {
				// br.readLine();
				while ((line = br.readLine()) != null) {
					String values[] = line.split(",");
					try {
						stmt = conn.prepareStatement(Query.addDish);
						stmt.setString(1, values[0]);
						stmt.setInt(2, Integer.parseInt(values[1]));
						stmt.setString(3, values[2]);
						int result = stmt.executeUpdate();
						if (result > 0) {
							try {
								stmt = conn.prepareStatement(Query.addDishIdandMenuId);
								stmt.setString(1, menuType);
								stmt.setString(2, values[0]);
								result = stmt.executeUpdate();
								if (result <= 0) {
									success = false;
								}
							} catch (Exception ex) {
								System.out.println("Error Is:" + ex.getMessage());
								success = false;
							}
						} else {
							success = false;
						}
					} catch (Exception ex) {
						System.out.println("Error Is:" + ex.getMessage());
						success = false;
					}
				}
				return success;
			} else {
				System.out.println("Menu Type does not match with FileName");
				return false;
			}
		} catch (Exception ex) {
			System.out.println("Error Is:" + ex.getMessage());
			return false;
		}
	}

	@Override
	public List<DishModel> showAllDishes(String type) {
		list = new ArrayList<DishModel>();
		try {
			stmt = conn.prepareStatement(Query.showAllDishes);
			stmt.setString(1, type);
			rs = stmt.executeQuery();
			while (rs.next()) {
				DishModel model = new DishModel(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4));
				list.add(model);
			}
		} catch (Exception ex) {
			System.out.println("Error is:" + ex.getMessage());
		}
		return list;
	}

	@Override
	public String searchDishByName(String type, String dishName) {
		try {
			stmt = conn.prepareStatement(Query.searchDishByTypeAndName);
			stmt.setString(1, type);
			stmt.setString(2, dishName);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception ex) {
			System.out.println("Error is:" + ex.getMessage());
		}
		return null;
	}

	@Override

	public boolean deleteDishByName(String type, String dishName) {
		try {
			stmt = conn.prepareStatement(Query.deleteDishByTypeAndName);
			stmt.setString(1, type);
			stmt.setString(2, dishName);
			int result = stmt.executeUpdate();
			return result > 0 ? true : false;
		} catch (Exception ex) {
			System.out.println("Error Is:" + ex.getMessage());
		}
		return false;
	}

	@Override
	public DishModel getDish(int dishId) {
		try {
			stmt = conn.prepareStatement(Query.getDishById);
			stmt.setInt(1, dishId);
			rs = stmt.executeQuery();
			if (rs.next()) {
				DishModel dish = new DishModel();
				dish.setDishId(rs.getInt("dishId"));
				dish.setDishName(rs.getString("dishName"));
				dish.setPrice(rs.getInt("price"));
				return dish;
			} else {
				return null;
			}
		} catch (Exception ex) {
			System.out.println("Error getting dish: " + ex.getMessage());
		}
		return null;
	}

	@Override
	public boolean addDish(String MenuType, DishModel model) {
		try {
			stmt=conn.prepareStatement(Query.addDish);
			stmt.setString(1,model.getDishName());
			stmt.setInt(2, model.getPrice());
			stmt.setString(3, model.getCategory());
			
			int result=stmt.executeUpdate();
			if(result>0) {
					stmt=conn.prepareStatement(Query.addDishIdandMenuId);
					stmt.setString(1, MenuType);
					stmt.setString(2, model.getDishName());
					result=stmt.executeUpdate();
					return result>0;
				}
			else {
				System.out.println("Problem Occurred While Inserting Dish !!!");
			}
			
		}
		catch(Exception ex) {
			System.out.println("Error Is:"+ex.getMessage());
		}
		return false;
	}

}
