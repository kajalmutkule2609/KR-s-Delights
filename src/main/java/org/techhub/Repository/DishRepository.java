package org.techhub.Repository;

import org.techhub.Model.*;
import java.util.*;

public interface DishRepository {
	public boolean addBulkDishes(String MenuType, String fileName);

	public List<DishModel> showAllDishes(String type);

	public String searchDishByName(String type, String dishName);

	public boolean deleteDishByName(String type, String dishName);

	public DishModel getDish(int dishId);
}
