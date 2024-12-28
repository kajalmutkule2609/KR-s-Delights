package org.techhub.Repository;

import org.techhub.Model.*;
import java.util.*;

public interface CustomerRepository {
	public boolean isRegisteredCustomer(CustomerModel model);

	public Optional<List<CustomerModel>> getAllCustomers();

	public String getCustomerByEmail(String email);

	public boolean deleteCustomer(String email);
	
	public CustomerModel getCustomer(String Email);
}
