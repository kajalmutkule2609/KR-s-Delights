package org.techhub.Service;

import java.util.*;
import org.techhub.Model.*;

public interface StaffService {
	public boolean isRegisteredStaff(StaffModel model);

	public Optional<List<StaffModel>> getAllStaff();

	public String getStaffByEmail(String email);

	public boolean deleteStaff(String email);

	public String validateUser(String email, String password);
}
