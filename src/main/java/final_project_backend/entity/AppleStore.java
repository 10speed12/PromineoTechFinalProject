package final_project_backend.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Entity
@Data
public class AppleStore {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long appleStoreId;
	private String appleStoreName;
	private String appleStoreAddress;
	private String appleStoreCity;
	private String appleStoreState;
	private String appleStoreZip;
	private String appleStorePhone;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "appleStore", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Employee> employees = new HashSet<>();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "apple_store_customer", joinColumns = @JoinColumn(name = "apple_store_id"),
		inverseJoinColumns = @JoinColumn(name = "customer_id"))
	private Set<Customer> customers = new HashSet<>();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "appleStore", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Inventory> inventories = new HashSet<>();

}
