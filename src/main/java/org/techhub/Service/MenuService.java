package org.techhub.Service;

import java.util.*;

import org.techhub.Model.*;

public interface MenuService {
	public Optional<List<MenuModel>> showMenus();

	public int getMenuIdByMenuType(String type);

	public boolean addMenuType(MenuModel model);

	public String searchMenuType(String type);

	public boolean deleteMenuType(String type);
}
