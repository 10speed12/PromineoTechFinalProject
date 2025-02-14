package final_project_backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import final_project_backend.entity.Customer;

public interface CustomerDao extends JpaRepository<Customer, Long> {

}
