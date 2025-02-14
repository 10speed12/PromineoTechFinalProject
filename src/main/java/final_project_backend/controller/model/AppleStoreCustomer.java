package final_project_backend.controller.model;

import java.util.HashSet;
import java.util.Set;

import final_project_backend.entity.Customer;
import final_project_backend.entity.Orders;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class AppleStoreCustomer {
	private Long customerId;
	
	private String customerFirstName;
	private String customerLastName;
	private String customerEmail;
	
	private Set<AppleStoreOrder> orders = new HashSet<>();
	// Constructor for creating from a customer object:
	public AppleStoreCustomer(Customer customer){
		customerId = customer.getCustomerId();
		customerFirstName = customer.getCustomerFirstName();
		customerLastName = customer.getCustomerLastName();
		customerEmail = customer.getCustomerEmail();
		
		for(Orders order: customer.getOrders()) {
			orders.add(new AppleStoreOrder(order));
		}
	}
}
