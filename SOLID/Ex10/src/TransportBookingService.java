public class TransportBookingService {
    private final DistanceCalculation distCalc;
    private final DriverAllocation driverAlloc;
    private final PaymentProcessing payment;

    public TransportBookingService(DistanceCalculation distCalc, DriverAllocation driverAlloc, PaymentProcessing payment) {
        this.distCalc = distCalc; this.driverAlloc = driverAlloc; this.payment = payment;
    }

    public void book(TripRequest req) {
        double km = distCalc.km(req.from, req.to);
        System.out.println("DistanceKm=" + km);

        String driver = driverAlloc.allocate(req.studentId);
        System.out.println("Driver=" + driver);

        double fare = 50.0 + km * 6.6666666667;
        fare = Math.round(fare * 100.0) / 100.0;

        String txn = payment.charge(req.studentId, fare);
        System.out.println("Payment=PAID txn=" + txn);

        BookingReceipt r = new BookingReceipt("R-501", fare);
        System.out.println("RECEIPT: " + r.id + " | fare=" + String.format("%.2f", r.fare));
    }
}
