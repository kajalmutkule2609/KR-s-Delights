package org.techhub.Service;

import java.util.List;
import java.util.Optional;

import org.techhub.Model.DishModel;
import org.techhub.Repository.*;

public class DishServiceImp implements DishService {
	DishRepository dishRepo = new DishRepositoryImp();

	@Override
	public boolean addBulkDishes(String MenuType, String fileName) {
		return dishRepo.addBulkDishes(MenuType, fileName);
	}

	@Override
	public List<DishModel> showAllDishes(String type) {
		return dishRepo.showAllDishes(type);
	}

	@Override
	public String searchDishByName(String type, String dishName) {
		return dishRepo.searchDishByName(type, dishName);
	}

	@Override
	public boolean deleteDishByName(String type, String dishName) {
		return dishRepo.deleteDishByName(type, dishName);
	}

	@Override
	public DishModel getDish(int dishId) {
		return dishRepo.getDish(dishId);
	}

	@Override
	public boolean addDish(String MenuType, DishModel model) {
		return dishRepo.addDish(MenuType, model);
	}

}
