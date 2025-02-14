package final_project_backend.controller.model;

import java.time.LocalDate;

import final_project_backend.entity.Orders;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AppleStoreOrder {
	private Long orderId;
	
	private String product_name;
	
	private int purchase_quantity;
	
	//private LocalDate orderDate;
	
	public AppleStoreOrder(Orders order) {
		orderId = order.getOrderId();
		product_name = order.getProduct_name();
		purchase_quantity = order.getPurchase_quantity();
		//orderDate = order.getOrderDate();
	}
}
