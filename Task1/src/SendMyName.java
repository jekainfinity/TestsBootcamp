import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class SendMyName {
    private static final String DESTINATION_URL="http://bootcamp-api.transferwise.com/name/";
    private static final String TOKEN = "2cbb7af43afa490eeb85d44ad222d3a416ef3dcc";

    public static void main(String[] args) throws UnsupportedEncodingException {
        SendMyName task1 = new SendMyName();
        String name = "Evgenii Palyvoda";
        try {
            String jsonStringResponse = task1.sendMyNamePost(name);
            System.out.println(jsonStringResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String sendMyNamePost(String name) throws IOException {
        URL url = new URL(DESTINATION_URL+URLEncoder.encode(name,"utf-8").replace("+", "%20"));

        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);

        String param = String.format("token=%s", URLEncoder.encode(TOKEN, "utf-8"));

        OutputStream outputStream = httpURLConnection.getOutputStream();
        outputStream.write(param.getBytes());
        outputStream.flush();
        outputStream.close();

        BufferedReader in = new BufferedReader(
                            new InputStreamReader(httpURLConnection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }
}
