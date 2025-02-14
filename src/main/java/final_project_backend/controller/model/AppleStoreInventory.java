package final_project_backend.controller.model;

import final_project_backend.entity.Inventory;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AppleStoreInventory {

	private Long inventoryId;
	// Name of product in inventory:
	private String product_name;
	// Amount of product in inventory:
	private Long product_quantity;
	
	//Constructor to build object given an Inventory item:
	public AppleStoreInventory(Inventory inventory) {
		inventoryId = inventory.getInventoryId();
		product_name = inventory.getProduct_name();
		product_quantity = inventory.getProduct_quantity();
	}
}
