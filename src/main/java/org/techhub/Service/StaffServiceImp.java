package org.techhub.Service;

import java.util.List;
import java.util.Optional;

import org.techhub.Model.StaffModel;
import org.techhub.Repository.*;

public class StaffServiceImp implements StaffService {
	StaffRepository staffRepo = new StaffRepositoryImp();

	@Override
	public boolean isRegisteredStaff(StaffModel model) {
		return staffRepo.isRegisteredStaff(model);
	}

	@Override
	public Optional<List<StaffModel>> getAllStaff() {
		return staffRepo.getAllStaff();
	}

	@Override
	public String getStaffByEmail(String email) {
		return staffRepo.getStaffByEmail(email);
	}

	@Override
	public boolean deleteStaff(String email) {
		return staffRepo.deleteStaff(email);
	}

	@Override
	public String validateUser(String email, String password) {
		return staffRepo.validateUser(email, password);
	}
}
