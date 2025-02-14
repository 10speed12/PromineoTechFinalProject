package final_project_backend.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import final_project_backend.controller.model.AppleStoreData;
import final_project_backend.controller.model.AppleStoreEmployee;
import final_project_backend.controller.model.AppleStoreInventory;
import final_project_backend.controller.model.AppleStoreOrder;
import final_project_backend.controller.model.AppleStoreCustomer;

import final_project_backend.dao.AppleStoreDao;
import final_project_backend.dao.EmployeeDao;
import final_project_backend.dao.InventoryDao;
import final_project_backend.dao.OrderDao;
import final_project_backend.dao.CustomerDao;

import final_project_backend.entity.AppleStore;
import final_project_backend.entity.Employee;
import final_project_backend.entity.Inventory;
import final_project_backend.entity.Orders;
import final_project_backend.entity.Customer;

@Service
public class AppleStoreService {
	@Autowired
	private AppleStoreDao appleStoreDao;
	@Autowired
	private EmployeeDao employeeDao;
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private InventoryDao inventoryDao;
	@Autowired
	private OrderDao orderDao;

	@Transactional(readOnly = false)
	public AppleStoreData saveAppleStore(AppleStoreData appleStoreData) {
		Long appleStoreId = appleStoreData.getAppleStoreId();
		AppleStore appleStore = findOrCreateAppleStore(appleStoreId);
		copyAppleStoreFields(appleStore, appleStoreData);
		return new AppleStoreData(appleStoreDao.save(appleStore));
	}

	private void copyAppleStoreFields(AppleStore appleStore, AppleStoreData appleStoreData) {
		appleStore.setAppleStoreName(appleStoreData.getAppleStoreName());
		appleStore.setAppleStoreAddress(appleStoreData.getAppleStoreAddress());
		appleStore.setAppleStoreCity(appleStoreData.getAppleStoreCity());
		appleStore.setAppleStoreState(appleStoreData.getAppleStoreState());
		appleStore.setAppleStoreZip(appleStoreData.getAppleStoreZip());
		appleStore.setAppleStorePhone(appleStoreData.getAppleStorePhone());
	}

	private AppleStore findOrCreateAppleStore(Long appleStoreId) {
		AppleStore appleStore;
		if (Objects.isNull(appleStoreId)) {
			appleStore = new AppleStore();
		} else {
			appleStore = findAppleStoreById(appleStoreId);
		}
		return appleStore;
	}

	private AppleStore findAppleStoreById(Long appleStoreId) {
		return appleStoreDao.findById(appleStoreId).orElseThrow(
				() -> new NoSuchElementException("Apple Store with ID=" + appleStoreId + " was not found."));
	}

	@Transactional(readOnly = false)
	public AppleStoreEmployee saveEmployee(Long appleStoreId, AppleStoreEmployee employeeData) {
		AppleStore appleStore = findAppleStoreById(appleStoreId);
		Long employeeId = employeeData.getEmployeeId();
		Employee employee = findOrCreateEmployee(appleStoreId, employeeId);
		copyEmployeeFields(employee, employeeData);
		employee.setAppleStore(appleStore);
		appleStore.getEmployees().add(employee);
		Employee dbEmployee = employeeDao.save(employee);
		return new AppleStoreEmployee(dbEmployee);
	}

	private void copyEmployeeFields(Employee employee, AppleStoreEmployee employeeData) {
		employee.setEmployeeFirstName(employeeData.getEmployeeFirstName());
		employee.setEmployeeLastName(employeeData.getEmployeeLastName());
		employee.setEmployeeJobTitle(employeeData.getEmployeeJobTitle());
		employee.setEmployeeEmail(employeeData.getEmployeeEmail());
		employee.setEmployeeId(employeeData.getEmployeeId());
	}

	private Employee findOrCreateEmployee(Long appleStoreId, Long employeeId) {
		if (Objects.isNull(employeeId)) {
			return new Employee();
		} else {
			return findEmployeeById(appleStoreId, employeeId);
		}
	}

	private Employee findEmployeeById(Long appleStoreId, Long employeeId) {
		Employee employee = employeeDao.findById(employeeId)
				.orElseThrow(() -> new NoSuchElementException("Employee with ID=" + employeeId + " was not found."));
		// Confirm that id of apple store for retrieved employee matches that of entered
		// appleStoreId:
		if (employee.getAppleStore().getAppleStoreId() != appleStoreId) {
			throw new IllegalArgumentException(
					"Error, id of apple store for retrieved employee does not match entered value.");
		}
		// Otherwise, return retrieved employee:
		return employee;
	}

	@Transactional(readOnly = false)
	public AppleStoreCustomer saveCustomer(Long appleStoreId, AppleStoreCustomer appleStoreCustomer) {
		AppleStore appleStore = findAppleStoreById(appleStoreId);
		Long customerId = appleStoreCustomer.getCustomerId();
		Customer customer = findOrCreateCustomer(appleStoreId, customerId);
		copyCustomerFields(customer, appleStoreCustomer);
		customer.getAppleStores().add(appleStore);
		appleStore.getCustomers().add(customer);
		Customer dbCustomer = customerDao.save(customer);
		return new AppleStoreCustomer(dbCustomer);
	}

	private void copyCustomerFields(Customer customer, AppleStoreCustomer customerData) {
		customer.setCustomerFirstName(customerData.getCustomerFirstName());
		customer.setCustomerLastName(customerData.getCustomerLastName());
		customer.setCustomerEmail(customerData.getCustomerEmail());
		customer.setCustomerId(customerData.getCustomerId());
	}

	private Customer findOrCreateCustomer(Long appleStoreId, Long customerId) {
		if (Objects.isNull(customerId)) {
			return new Customer();
		} else {
			return findCustomerById(appleStoreId, customerId);
		}
	}

	private Customer findCustomerById(Long appleStoreId, Long customerId) {
		Customer customer = customerDao.findById(customerId)
				.orElseThrow(() -> new NoSuchElementException("Customer with ID=" + customerId + " was not found."));
		// Confirm that found customer has a appleStore with Id of entered appleStoreId
		// in it's records:
		boolean found = false;
		for (AppleStore appleStore : customer.getAppleStores()) {
			if (appleStore.getAppleStoreId() == appleStoreId) {
				// If an apple store whose Id matches entered value is found in retrieved customers
				// appleStore list,
				// set found variable to true and exit the loop:
				found = true;
				break;
			}
		}
		if (found) {
			// If found is set to true, return retrieved customer:
			return customer;
		} else {
			// Otherwise, throw an IllegalArgumentException:
			throw new IllegalArgumentException("The customer with ID=" + customerId
					+ " is not a member of the Apple store with ID=" + appleStoreId);
		}
	}
	
	//Find a customer without a provided appleStoreId:
	private Customer findCustomerById(Long customerId) {
		return customerDao.findById(customerId)
				.orElseThrow(() -> new NoSuchElementException("Customer with ID=" + customerId + " was not found."));
	}
	
	@Transactional(readOnly=true)
	public List<AppleStoreData> retrieveAllAppleStores() {
		List<AppleStore> appleStores = appleStoreDao.findAll();
		
		List<AppleStoreData> result = new LinkedList<>();
		for(AppleStore appleStore : appleStores) {
			// Create a new AppleStoreData object named psd using the information of current petStore:
			AppleStoreData psd = new AppleStoreData(appleStore);
			// Remove contents of the customer and employee lists for psd:
			psd.getCustomers().clear();
			psd.getEmployees().clear();
			// Add adjusted psd to result list:
			result.add(psd);
		}
		return result;
	}
	
	@Transactional
	public AppleStoreData getAppleStoreById(Long appleStoreId) {
		AppleStore appleStore = findAppleStoreById(appleStoreId);
		// Convert retrieved PetStore into a new PetStoreData object and return it:
		return new AppleStoreData(appleStore);
	}
	
	@Transactional
	public AppleStoreCustomer getCustomerById(Long customerId) {
		Customer customer = findCustomerById(customerId);
		return new AppleStoreCustomer(customer);
	}
	
	@Transactional(readOnly = false)
	public void deleteAppleStoreById(Long appleStoreId) {
		AppleStore appleStore = findAppleStoreById(appleStoreId);
		appleStoreDao.delete(appleStore);
	}
	
	
	@Transactional(readOnly = false)
	public AppleStoreInventory saveInventory(Long appleStoreId, AppleStoreInventory inventoryData) {
		AppleStore appleStore = findAppleStoreById(appleStoreId);
		Long inventoryId = inventoryData.getInventoryId();
		Inventory inventory = findOrCreateInventory(appleStoreId, inventoryId);
		copyInventoryFields(inventory, inventoryData);
		inventory.setAppleStore(appleStore);
		appleStore.getInventories().add(inventory);
		Inventory dbInventory = inventoryDao.save(inventory);
		return new AppleStoreInventory(dbInventory);
	}


	
	private void copyInventoryFields(Inventory inventory, AppleStoreInventory inventoryData) {
		inventory.setProduct_name(inventoryData.getProduct_name());
		inventory.setProduct_quantity(inventoryData.getProduct_quantity());
	}

	private Inventory findOrCreateInventory(Long appleStoreId, Long inventoryId) {
		if (Objects.isNull(inventoryId)) {
			return new Inventory();
		} else {
			return findInventoryById(appleStoreId, inventoryId);
		}
	}

	private Inventory findInventoryById(Long appleStoreId, Long inventoryId) {
		Inventory inventory = inventoryDao.findById(inventoryId)
				.orElseThrow(() -> new NoSuchElementException("Inventory record with ID=" + inventoryId + " was not found."));
		// Confirm that id of apple store for retrieved inventory matches that of entered
		// appleStoreId:
		if (inventory.getAppleStore().getAppleStoreId() != appleStoreId) {
			throw new IllegalArgumentException(
					"Error, id of apple store for retrieved inventory does not match entered value.");
		}
		// Otherwise, return retrieved inventory:
		return inventory;
	}

	public AppleStoreOrder saveOrder(Long customerId, AppleStoreOrder orderData) {
		Customer customer = findCustomerById(customerId);
		Long orderId = orderData.getOrderId();
		Orders order = findOrCreateOrder(customerId, orderId);
		copyOrderFields(order, orderData);
		order.setCustomer(customer);
		customer.getOrders().add(order);
		Orders dbOrder= orderDao.save(order);
		return new AppleStoreOrder(dbOrder);
	}
	
	private void copyOrderFields(Orders order, AppleStoreOrder orderData) {
		order.setProduct_name(orderData.getProduct_name());
		order.setPurchase_quantity(orderData.getPurchase_quantity());
		//order.setOrderDate(orderData.getOrderDate());
		
	}

	private Orders findOrCreateOrder(Long customerId, Long orderId) {
		if (Objects.isNull(orderId)) {
			return new Orders();
		} else {
			return findOrderById(customerId, orderId);
		}
	}

	private Orders findOrderById(Long customerId, Long orderId) {
		Orders order = orderDao.findById(orderId)
				.orElseThrow(() -> new NoSuchElementException("Order record with ID="+orderId+" was not found."));
		if(order.getCustomer().getCustomerId() != customerId) {
			throw new IllegalArgumentException(
					"Error, id of customer for retrieved order does not match entered value.");
		}
		return order;
	}
	
}
