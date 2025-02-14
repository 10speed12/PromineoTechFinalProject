package final_project_backend.controller.model;

import final_project_backend.entity.Employee;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
public class AppleStoreEmployee {
	private Long employeeId;
	private String employeeFirstName;
	private String employeeLastName;
	private String employeeEmail;
	private String employeeJobTitle;
	// Constructor for creating from an employee object:
	public AppleStoreEmployee(Employee employee) {
		employeeId = employee.getEmployeeId();
		employeeFirstName = employee.getEmployeeFirstName();
		employeeLastName = employee.getEmployeeLastName();
		employeeEmail = employee.getEmployeeEmail();
		employeeJobTitle = employee.getEmployeeJobTitle();
	}
}
