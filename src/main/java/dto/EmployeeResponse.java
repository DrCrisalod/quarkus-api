package dto;


import entity.Employee;

public class EmployeeResponse {
    public Long id;
    public boolean success;

    public EmployeeResponse(Long id, boolean success) {
        this.id = id;
        this.success = success;
    }

    public EmployeeResponse(Employee employee) {
        this.id = employee.getId();
        this.success = true;
    }
}