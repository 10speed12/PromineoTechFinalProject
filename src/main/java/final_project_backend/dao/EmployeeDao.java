package final_project_backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import final_project_backend.entity.Employee;

public interface EmployeeDao extends JpaRepository<Employee, Long> {

}
