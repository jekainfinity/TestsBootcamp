import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Payed {
    public static void main(String[] args) {
        Payed payed = new Payed();
        List<UrlParams> urlParams = new ArrayList<UrlParams>();

        try {
            List<Bank> banks = payed.getBanks();
            List<Payment> payments = payed.getPayment();
            List<BankAccount> bankAccounts = payed.getBankAccounts();

            for (Payment payment: payments) {
                UrlParams params = new UrlParams();

                params.setAmount(payment.getAmount());
                params.setTargetBankName(payed.findBankNameById(payment.getRecipientBankId(),banks));

                for (BankAccount account: bankAccounts) {
                    if (account.getAccountName().equals("TransferWise Ltd") && account.getCurrency().equals(payment.getSourceCurrency())) {
                        params.setSourceAccountNumber(account.getAccountNumber());
                        params.setSourceBankName(payed.findBankNameById(account.getBankId(),banks));
                    }

                    if (account.getAccountNumber().equals(payment.getIban())) {
                        params.setTargetAccountNumber(account.getAccountNumber());
                    }
                }

                urlParams.add(params);
            }

            payed.transfer(urlParams);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private String findBankNameById(String id, List<Bank> banks) {
        String name = "";
        for (Bank bank: banks) {
            if (bank.getId().equals(id)) {
               name = bank.getName();
            }
        }

        return name;
    }

    private List<Bank> getBanks() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Bank> banks = mapper.readValue(getRequest("bank"), TypeFactory.defaultInstance().constructCollectionType(ArrayList.class,
                Bank.class)) ;

        return banks;
    }

    private List<Payment> getPayment() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Payment> payments = mapper.readValue(getRequest("payment"), TypeFactory.defaultInstance().constructCollectionType(ArrayList.class,
                Payment.class)) ;

        return payments;
    }

    private List<BankAccount> getBankAccounts() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<BankAccount> bankAccounts = mapper.readValue(getRequest("bankAccount"), TypeFactory.defaultInstance().constructCollectionType(ArrayList.class,
                BankAccount.class)) ;

        return bankAccounts;
    }

    private String getRequest(String destination) throws IOException {

        String url = "http://bootcamp-api.transferwise.com/" + destination + "/?token=2cbb7af43afa490eeb85d44ad222d3a416ef3dcc";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

        String inputLine;
        StringBuilder json = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            json.append(inputLine);
        }

        in.close();

        return json.toString();
    }

    private String transfer(List<UrlParams> urlParams) throws IOException {
        String url = "http://bootcamp-api.transferwise.com/bank/%s/transfer/%s/%s/%s/%s/?token=2cbb7af43afa490eeb85d44ad222d3a416ef3dcc";

        for (UrlParams params: urlParams) {
            String formatUrl = String.format(url, URLEncoder.encode(params.getSourceBankName(), "utf-8").replace("+", "%20") ,params.getSourceAccountNumber(),
                                                  URLEncoder.encode(params.getTargetBankName(),"utf-8").replace("+", "%20") ,params.getTargetAccountNumber(),
                                                  params.getAmount());
            String response = sendPost(formatUrl);

            System.out.println(response);
        }

        return "OK";
    }

    private String sendPost(String url) throws IOException{
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        System.out.println(url);
        con.setRequestMethod("POST");
        con.setDoInput(true);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }
}
