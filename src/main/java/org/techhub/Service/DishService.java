package org.techhub.Service;

import java.util.List;
import java.util.Optional;

import org.techhub.Model.DishModel;

public interface DishService {
	public boolean addBulkDishes(String MenuType, String fileName);

	public List<DishModel> showAllDishes(String type);

	public String searchDishByName(String type, String dishName);

	public boolean deleteDishByName(String type, String dishName);

	public DishModel getDish(int dishId);
}
