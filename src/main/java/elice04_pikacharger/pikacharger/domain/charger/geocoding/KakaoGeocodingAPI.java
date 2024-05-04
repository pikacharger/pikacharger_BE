package elice04_pikacharger.pikacharger.domain.charger.geocoding;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
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
public class KakaoGeocodingAPI {



    @Value("${charger.geocoding.key}")
    private String clientKey;

    public List<String> coordinatePairs(String address) throws Exception {
        String apiURL = "https://dapi.kakao.com/v2/local/search/address.json?query=" + java.net.URLEncoder.encode(address, "UTF-8");

        URL url = new URL(apiURL);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestProperty("Authorization", clientKey);


        BufferedReader br;
        br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = br.readLine()) != null) {
            response.append(inputLine);
        }
        br.close();

        JSONObject responseObject = new JSONObject(response.toString());
        JSONArray addresses = responseObject.getJSONArray("documents");
        List<String> locations = new ArrayList<>();

        if (addresses.length() == 0) {
            log.debug("주소 정보를 찾을 수 없습니다.");
            return locations;
        }
        JSONObject firstAddress = addresses.getJSONObject(0);
        String x = firstAddress.getString("x"); // 경도
        String y = firstAddress.getString("y"); // 위도
        locations.add(y);
        locations.add(x);

        return locations;
    }
}
