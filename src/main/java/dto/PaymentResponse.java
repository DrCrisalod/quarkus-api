package dto;

public class PaymentResponse {
    public int totalHours;
    public double payment;

    public PaymentResponse(int totalHours, double payment) {
        this.totalHours = totalHours;
        this.payment = payment;
    }
}
