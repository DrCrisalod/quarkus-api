package dto;

import java.math.BigDecimal;

public class PaymentResponse {
    private Long employeeId;
    private BigDecimal totalHours;
    private BigDecimal totalPayment;
    private boolean success;

    public PaymentResponse(Long employeeId, BigDecimal totalHours, BigDecimal totalPayment, boolean success) {
        this.employeeId = employeeId;
        this.totalHours = totalHours;
        this.totalPayment = totalPayment;
        this.success = success;
    }

    // Getters y setters

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public BigDecimal getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(BigDecimal totalHours) {
        this.totalHours = totalHours;
    }

    public BigDecimal getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(BigDecimal totalPayment) {
        this.totalPayment = totalPayment;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}