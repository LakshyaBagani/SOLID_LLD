public class PaymentGateway implements PaymentProcessing {
    public String charge(String studentId, double amount) {
        return "TXN-9001";
    }
}
