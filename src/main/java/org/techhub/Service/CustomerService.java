package org.techhub.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.techhub.Model.CustomerModel;

public interface CustomerService {
	public boolean isRegisteredCustomer(CustomerModel model);

	public Optional<List<CustomerModel>> getAllCustomers();

	public String getCustomerByEmail(String email);

	public boolean deleteCustomer(String email);
	
	public CustomerModel getCustomer(String Email);
}
