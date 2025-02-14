package final_project_backend.entity;

//import java.util.HashSet;
//import java.util.Set;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
//import jakarta.persistence.OneToMany;
//import jakarta.validation.constraints.Min;
//import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Inventory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long inventoryId;
	// Name of product in inventory:
	private String product_name;
	// Amount of product in inventory:
	@ColumnDefault(value = "0")
	private Long product_quantity;
	// Key to establish which store item is associated with:
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "appleStoreId", nullable = false)
	private AppleStore appleStore;
	/*
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Inventory> orderList = new HashSet<>();
	*/
}
