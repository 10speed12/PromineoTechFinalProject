package final_project_backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import final_project_backend.entity.Orders;

public interface OrderDao extends JpaRepository<Orders, Long> {

}
