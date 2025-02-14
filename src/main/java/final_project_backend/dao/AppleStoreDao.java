package final_project_backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import final_project_backend.entity.AppleStore;

public interface AppleStoreDao extends JpaRepository<AppleStore, Long> {

}
