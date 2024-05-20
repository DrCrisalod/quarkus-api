package dto;

import java.util.List;

public class EmployeeListResponse {
    public List<EmployeeResponse> employees;
    public boolean success;

    public EmployeeListResponse(List<EmployeeResponse> employees, boolean success) {
        this.employees = employees;
        this.success = success;
    }
}