package final_project_backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import final_project_backend.entity.Inventory;

public interface InventoryDao extends JpaRepository<Inventory, Long> {

}
