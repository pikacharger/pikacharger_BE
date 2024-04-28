package elice04_pikacharger.pikacharger.domain.charger.geocoding;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class GeocodingAPI {

    @Value("${charger.geocoding.id}")
    private String clientId;

    @Value("${charger.geocoding.key}")
    private String clientKey;


    public List<String> coordinatePairs(String address) throws Exception {

        String apiURL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=" + java.net.URLEncoder.encode(address, "UTF-8");

        URL url = new URL(apiURL);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
        con.setRequestProperty("X-NCP-APIGW-API-KEY", clientKey);

        int responseCode = con.getResponseCode();
        BufferedReader br;
        if(responseCode==200) { // 정상 호출
            br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
        } else {  // 에러 발생
            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        }
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = br.readLine()) != null) {
            response.append(inputLine);
        }
        br.close();

        JSONObject responseObject = new JSONObject(response.toString());
        JSONArray addresses = responseObject.getJSONArray("addresses");
        List<String> locations = new ArrayList<>();

        if (addresses.length() > 0) {
            JSONObject firstAddress = addresses.getJSONObject(0);
            String x = firstAddress.getString("x"); // 경도
            String y = firstAddress.getString("y"); // 위도
            locations.add(y);
            locations.add(x);

        } else {
            log.debug("주소 정보를 찾을 수 없습니다.");
        }

        return locations;
    }
}
