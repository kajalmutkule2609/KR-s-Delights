package org.techhub.Service;

import org.techhub.Model.*;
import org.techhub.Repository.*;
import java.util.*;

public class TableServiceImp implements TableService {
	TableRepository tableRepo = new TableRepositoryImp();

	@Override
	public boolean showAvailableTables() {
		return tableRepo.showAvailableTables();
	}

	@Override
	public boolean reserveTable(int tableNo, String email) {
		return tableRepo.reserveTable(tableNo, email);
	}

}
