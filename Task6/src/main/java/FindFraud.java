import jsonObj.Payment;
import jsonObj.PaymentHistory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FindFraud {
    public static void main(String[] args) {
        WorkWithPayment workWithPayment = new WorkWithPayment();

        try {
            // fetch lists pf information about payments
            List<PaymentHistory> paymentHistories = workWithPayment.getPaymentHistory();
            List<Payment> payments = workWithPayment.getPayments();
            List<String> fraudstersId = new ArrayList<String>();

            // put fraud id to fraudstersId to mark payment as fraudsters
            for (PaymentHistory paymentHistory: paymentHistories) {
                if (paymentHistory.isFraud()) {
                    int lastIndexOfAtFraud = paymentHistory.getEmail().lastIndexOf("@");
                    String fraudEmailPart = paymentHistory.getEmail().substring(0,lastIndexOfAtFraud);

                    int lastIndexOfPointFraud = paymentHistory.getIp().lastIndexOf(".");
                    String fraudIpPart = paymentHistory.getIp().substring(0,lastIndexOfPointFraud);

                    for (Payment payment: payments) {
                        int lastIndexOfAtPayment = payment.getEmail().lastIndexOf("@");
                        int lastIndexOfPointPayment = payment.getIp().lastIndexOf(".");

                        String paymentEmailPart = payment.getEmail().substring(0,lastIndexOfAtPayment);
                        String paymentIpPart = payment.getIp().substring(0,lastIndexOfPointPayment);

                        if (paymentEmailPart.equals(fraudEmailPart) && paymentIpPart.equals(fraudIpPart)) {
                            fraudstersId.add(payment.getId());
                        }

                        if (paymentHistory.getIp().equals(payment.getIp())) {
                            fraudstersId.add(payment.getId());
                        }
                    }
                }
            }

            for (Payment payment: payments) {
                String id = payment.getId();
                boolean status = false;

                if (fraudstersId.contains(id)) {
                    workWithPayment.markTransactionAsFraud(id);
                    status = true;
                }

                if (!status) {
                    workWithPayment.markTransactionAsNotFraud(id);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
