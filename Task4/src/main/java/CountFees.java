import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.TypeReference;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountFees {
    RequestCreator requestCreator = new RequestCreator();
    public static void main(String[] args) {
        CountFees countFees = new CountFees();

        List<Double> amounts = new ArrayList<Double>();
        amounts.add(100.0);
        amounts.add(1000.0);
        amounts.add(10000.0);

        try {
            List<HiddenCompanyInfo> infos = new ArrayList<HiddenCompanyInfo>();
            List<String> currencies = countFees.getCurrencies();

            for (Double amount: amounts) {
                for (int i = 0; i < currencies.size(); i++) {
                    String sourceCurrency = currencies.get(i);
                        for (int j = 0; j < currencies.size(); j ++ ) {
                                String targetCurrency = currencies.get(j);

                                Map<String, QuoteInfo> companiesQuote = countFees.getQuotes(sourceCurrency,targetCurrency,amount);
                                MidMarket midMarketRate = countFees.getMidMarketRate(sourceCurrency, targetCurrency);

                                for (String company: companiesQuote.keySet()) {
                                    HiddenCompanyInfo hInfo = new HiddenCompanyInfo();
                                    hInfo.setCompanyName(company);
                                    hInfo.setSourceAmount(amount);
                                    hInfo.setSourceCurrency(sourceCurrency);
                                    hInfo.setTargetCurrency(targetCurrency);

                                    Double receiveMidMarket = companiesQuote.get(company).getSourceAmount() * midMarketRate.getRate();
                                    Double receiveCompany = companiesQuote.get(company).getRecipientReceives();
                                    Integer hiddenFee =  (int) (((receiveMidMarket - receiveCompany)/receiveMidMarket) * 100);

                                    hInfo.setHiddenFeePercentage(hiddenFee);

                                    infos.add(hInfo);
                                }
                        }
                }
            }

            countFees.findCheaters(infos);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private List<String> getCurrencies() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<String> currencies = mapper.readValue(requestCreator.getRequest("currency"), TypeFactory.defaultInstance().constructCollectionType(ArrayList.class,
                String.class)) ;

        return currencies;
    }

    private MidMarket getMidMarketRate(String sourceCur, String targetCur) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        MidMarket midMarketRate = mapper.readValue(requestCreator.getRequest("rate/midMarket/" + sourceCur + "/" + targetCur),MidMarket.class) ;

        return midMarketRate;
    }

    private Map<String, QuoteInfo> getQuotes(String sourceCur, String targetCur, Double sourceAmount) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, QuoteInfo> companies = mapper.readValue(requestCreator.getRequest("quote/" + sourceAmount + "/" + sourceCur + "/" + targetCur),new TypeReference<Map<String, QuoteInfo>>(){}) ;

        return companies;
    }

    private void findCheaters(List<HiddenCompanyInfo> hci) throws IOException {
        for (HiddenCompanyInfo h1: hci) {
            String url = "http://bootcamp-api.transferwise.com/hiddenFee/forCompany/"+h1.getCompanyName()+"/"+h1.getSourceAmount()+
                    "/"+h1.getSourceCurrency()+"/"+h1.getTargetCurrency()+"/"+h1.getHiddenFeePercentage()+"/?token=2cbb7af43afa490eeb85d44ad222d3a416ef3dcc";
            System.out.println(requestCreator.sendPost(url));
        }

    }


}
