package org.techhub.Service;

import java.util.*;

import org.techhub.Model.*;
import org.techhub.Repository.*;

public class CustomerServiceImp implements CustomerService {
	CustomerRepository custRepository = new CustomerRepositoryImp();

	@Override
	public boolean isRegisteredCustomer(CustomerModel model) {
		return custRepository.isRegisteredCustomer(model);
	}

	@Override
	public Optional<List<CustomerModel>> getAllCustomers() {
		return custRepository.getAllCustomers();
	}

	@Override
	public String getCustomerByEmail(String email) {
		return custRepository.getCustomerByEmail(email);
	}

	@Override
	public boolean deleteCustomer(String email) {
		return custRepository.deleteCustomer(email);
	}
	@Override
	public CustomerModel getCustomer(String Email) {
		return custRepository.getCustomer(Email);
	}

}
