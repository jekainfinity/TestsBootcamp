import jsonObj.Payment;
import jsonObj.PaymentHistory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WorkWithPayment {
    RequestCreator requestCreator = new RequestCreator();
    ObjectMapper objectMapper = new ObjectMapper();

    public List<PaymentHistory> getPaymentHistory() throws IOException {
        List<PaymentHistory> paymentHistories = objectMapper.readValue(requestCreator.getRequest("payment/history"),new TypeReference< ArrayList<PaymentHistory>>(){});
        return paymentHistories;
    }

    public List<Payment> getPayments() throws IOException {
        List<Payment> payments = objectMapper.readValue(requestCreator.getRequest("payment"),new TypeReference< ArrayList<Payment>>(){});
        return payments;
    }

    public String markTransactionAsFraud(String id) throws IOException {
        String url = "http://bootcamp-api.transferwise.com/payment/" + id + "/fraud/?token=2cbb7af43afa490eeb85d44ad222d3a416ef3dcc";
        return requestCreator.sendRequest(url, "PUT");
    }

    public String markTransactionAsNotFraud(String id) throws IOException {
        String url = "http://bootcamp-api.transferwise.com/payment/" + id + "/fraud/?token=2cbb7af43afa490eeb85d44ad222d3a416ef3dcc";
        return requestCreator.sendRequest(url, "DELETE");
    }
}
