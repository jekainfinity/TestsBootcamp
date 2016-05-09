import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class WorkWithPeps {
    private RequestCreator requestCreator = new RequestCreator();
    private ObjectMapper mapper = new ObjectMapper();

    public Peps getPeps() throws IOException {
        JsonNode treeNode = mapper.readTree(requestCreator.getRequest("task/5"));
        JsonNode jsonNode = treeNode.get("peps");

        String jsonPeps = String.format("{ \"pepList\": %s}", jsonNode);
        return mapper.readValue(jsonPeps, Peps.class);
    }

    public List<Payment> getPayments() throws IOException {
        List<Payment> payments = mapper.readValue(requestCreator.getRequest("payment"), TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, Payment.class));
        return payments;
    }

    public String markTransactionPEP(String id) throws IOException {
        String url = "http://bootcamp-api.transferwise.com/payment/" + id + "/aml/?token=2cbb7af43afa490eeb85d44ad222d3a416ef3dcc";
        return requestCreator.sendRequest(url, "PUT");
    }

    public String deleteTransactionAsNotPEP(String id) throws IOException {
        String url = "http://bootcamp-api.transferwise.com/payment/" + id + "/aml/?token=2cbb7af43afa490eeb85d44ad222d3a416ef3dcc";
        return requestCreator.sendRequest(url, "DELETE");
    }

}
