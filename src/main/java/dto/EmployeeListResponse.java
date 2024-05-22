package dto;

import java.util.List;

public class EmployeeListResponse {
    private List<EmployeeResponse> employees;
    private boolean success;

    public EmployeeListResponse(List<EmployeeResponse> employees, boolean success) {
        this.employees = employees;
        this.success = success;
    }

    public List<EmployeeResponse> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeResponse> employees) {
        this.employees = employees;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
