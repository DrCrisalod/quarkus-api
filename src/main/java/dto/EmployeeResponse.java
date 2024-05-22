package dto;


import entity.Employee;

public class EmployeeResponse {
    private Long id;
    private boolean success;

    public EmployeeResponse(Long id, boolean success) {
        this.id = id;
        this.success = success;
    }

    public EmployeeResponse(Employee employee) {
        this.id = employee.getId();
        this.success = true;
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}