package org.techhub.Repository;

import java.util.*;
import org.techhub.Model.*;

public interface StaffRepository {
	public boolean isRegisteredStaff(StaffModel model);

	public Optional<List<StaffModel>> getAllStaff();

	public String getStaffByEmail(String email);

	public boolean deleteStaff(String email);

	public String validateUser(String email, String password);
}
