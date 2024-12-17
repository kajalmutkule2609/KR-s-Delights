package org.techhub.Service;

import java.util.*;
import org.techhub.Model.*;
import org.techhub.Repository.*;

public class MenuServiceImp implements MenuService {
	MenuRepository menuRepo = new MenuRepositoryImp();

	@Override
	public Optional<List<MenuModel>> showMenus() {
		return menuRepo.showMenus();
	}

	@Override
	public int getMenuIdByMenuType(String type) {
		return menuRepo.getMenuIdByMenuType(type);
	}

	@Override
	public boolean addMenuType(MenuModel model) {
		return menuRepo.addMenuType(model);
	}

	@Override
	public String searchMenuType(String type) {
		return menuRepo.searchMenuType(type);
	}

	@Override
	public boolean deleteMenuType(String type) {
		return menuRepo.deleteMenuType(type);
	}

}
