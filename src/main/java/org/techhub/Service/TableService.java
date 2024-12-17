package org.techhub.Service;

import org.techhub.Model.*;
import java.util.*;

public interface TableService {

	public boolean showAvailableTables();

	public boolean reserveTable(int tableNo, String email);
}
