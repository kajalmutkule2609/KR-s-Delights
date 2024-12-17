package org.techhub.Repository;

import org.techhub.Model.*;
import java.util.*;

public interface TableRepository {
	public boolean showAvailableTables();

	public boolean reserveTable(int tableNo, String email);
}
