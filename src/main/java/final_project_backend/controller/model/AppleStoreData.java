package final_project_backend.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
// import final_project_backend.controller.model.AppleStoreCustomer;
// import final_project_backend.controller.model.AppleStoreEmployee;
import final_project_backend.entity.Customer;
import final_project_backend.entity.Employee;
import final_project_backend.entity.Inventory;
import final_project_backend.entity.AppleStore;


@Data
@NoArgsConstructor
public class AppleStoreData {
	private Long appleStoreId;
	private String appleStoreName;
	private String appleStoreAddress;
	private String appleStoreCity;
	private String appleStoreState;
	private String appleStoreZip;
	private String appleStorePhone;
	
	private Set<AppleStoreCustomer> customers = new HashSet<>();
	private Set<AppleStoreEmployee> employees = new HashSet<>();
	private Set<AppleStoreInventory> inventoryContents = new HashSet<>();
	
	// Constructor for creating from a appleStore object:
	public AppleStoreData(AppleStore appleStore){
		appleStoreId = appleStore.getAppleStoreId();
		appleStoreName = appleStore.getAppleStoreName();
		appleStoreAddress = appleStore.getAppleStoreAddress();
		appleStoreCity = appleStore.getAppleStoreCity();
		appleStoreState = appleStore.getAppleStoreState();
		appleStoreZip = appleStore.getAppleStoreZip();
		appleStorePhone = appleStore.getAppleStorePhone();
		// Iterate through given appleStore's customers list:
		for (Customer customer : appleStore.getCustomers()) {
			// Add new AppleStoreCustomer created from the data in current customer to customers list:
			customers.add(new AppleStoreCustomer(customer));
		}
		// Iterate through given appleStore's employees list:
		for(Employee employee : appleStore.getEmployees()) {
			// Add new AppleStoreEmployee created from the data in current employee to employees list:
			employees.add(new AppleStoreEmployee(employee));
		}
		// Iterate through given appleStore's inventory list:
		for(Inventory inventory : appleStore.getInventories()) {
			// Add new AppleStoreInventory created from the data in current inventory object to inventoryContents list:
			inventoryContents.add(new AppleStoreInventory(inventory));
		}
	}
}
