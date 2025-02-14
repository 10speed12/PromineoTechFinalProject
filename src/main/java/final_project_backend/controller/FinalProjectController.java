package final_project_backend.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import final_project_backend.controller.model.AppleStoreCustomer;
import final_project_backend.controller.model.AppleStoreData;
import final_project_backend.controller.model.AppleStoreEmployee;
import final_project_backend.controller.model.AppleStoreInventory;
import final_project_backend.controller.model.AppleStoreOrder;
import final_project_backend.service.AppleStoreService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Data
class Payload {
    private LocalDate date;
    private LocalDateTime  dateTime;
}


@RestController
@RequestMapping("/final_project")
@Slf4j
public class FinalProjectController {
	@Autowired
	private AppleStoreService appleStoreService;
	
	@PostMapping("/final_project")
	@ResponseStatus(code = HttpStatus.CREATED)
	public AppleStoreData postAppleStore(
			@RequestBody AppleStoreData appleStoreData) {
		// Log current action data:
		log.info("POSTing appleStoreData {}", appleStoreData);
		return appleStoreService.saveAppleStore(appleStoreData);
	}
	
	@PutMapping("/final_project/{appleStoreId}")
	public AppleStoreData updateAppleStore(@PathVariable Long appleStoreId,
			@RequestBody AppleStoreData appleStoreData) {
		// Set id of inputed AppleStoreData to appleStoreId:
		appleStoreData.setAppleStoreId(appleStoreId);
		// Log current action data:
		log.info("Updating AppleStore {}", appleStoreData);
		return appleStoreService.saveAppleStore(appleStoreData);
	}
	
	@PostMapping("/{appleStoreId}/employee")
	@ResponseStatus(code = HttpStatus.CREATED)
	public AppleStoreEmployee addEmployeeToStore(@PathVariable Long appleStoreId,
			@RequestBody AppleStoreEmployee employeeData) {
		// Log current action data:
		log.info("POSTing appleStoreEmployeeData {} for appleStore with ID = {}",
				employeeData, appleStoreId);
		return appleStoreService.saveEmployee(appleStoreId, employeeData);
	}
	
	@PostMapping("/{appleStoreId}/customer")
	@ResponseStatus(code = HttpStatus.CREATED)
	public AppleStoreCustomer addCustomerToStore(@PathVariable Long appleStoreId,
			@RequestBody AppleStoreCustomer customerData) {
		// Log current action data:
		log.info("POSTing appleStoreCustomerData {} for appleStore with ID = {}",
				customerData, appleStoreId);
		return appleStoreService.saveCustomer(appleStoreId, customerData);
	}

	@PostMapping("/{appleStoreId}/inventory")
	@ResponseStatus(code = HttpStatus.CREATED)
	public AppleStoreInventory addInventoryToStore(@PathVariable Long appleStoreId,
			@RequestBody AppleStoreInventory inventoryData) {
		// Log current action data:
		log.info("POSTing appleStoreInventoryData {} for appleStore with ID = {}",
				inventoryData, appleStoreId);
		return appleStoreService.saveInventory(appleStoreId, inventoryData);
	}
	
	@PostMapping("/{customerId}/orders")
	@ResponseStatus(code = HttpStatus.CREATED)
	public AppleStoreOrder addOrderToCustomer(@PathVariable Long customerId,
			@RequestBody AppleStoreOrder orderData) {
		
		log.info("POSTing appleStoreOrder data {} for customer with ID = {}", orderData, customerId);
		return appleStoreService.saveOrder(customerId, orderData);
	}
	
	@GetMapping()
	public List<AppleStoreData> listAllAppleStores(){
		log.info("Retrieving list of all Apple Stores.");
		return appleStoreService.retrieveAllAppleStores();
	}
	
	@PostMapping
    public Object test (@RequestBody Payload payload) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("payload", payload); // request body
        ret.put("now", new Date());
        return ret;
	}
	
	
	@GetMapping("/{appleStoreId}")
	public AppleStoreData getAppleStoreById(@PathVariable Long appleStoreId) {
		log.info("Retrieving Apple Store with ID={}",appleStoreId);
		return appleStoreService.getAppleStoreById(appleStoreId);
	}
	
	@GetMapping("/customers/{customerId}")
	public AppleStoreCustomer getCustomerById(@PathVariable Long customerId) {
		log.info("Retrieving Customer with ID={}",customerId);
		return appleStoreService.getCustomerById(customerId);
	}
	
	@DeleteMapping("/final_project")
	public void deleteAllAppleStores() {
		log.info("Attempting to delete all apple stores:");
		throw new UnsupportedOperationException("Deleting all stores is not allowed.");
	}
	
	@DeleteMapping("/final_project/{appleStoreId}")
	public Map<String,String> deleteAppleStoreById(@PathVariable Long appleStoreId) {
		log.info("Attempting to delete AppleStore with ID={}",appleStoreId);
		appleStoreService.deleteAppleStoreById(appleStoreId);
		return Map.of("message","Deletion of contributor with ID="+appleStoreId+" was successful.");
	}
}
