package Online_Meansreang.Meangsreang.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;

@Service
public class BakongApiService {

    @Value("${bakong.token}")
    private String token;

    @Value("${bakong.api-url}")
    private String apiUrl;

    private final WebClient webClient;

    public BakongApiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public String checkTransactionStatus(String transactionId) {
        try {
            // Bakong API usually uses /check_transaction or similar
            // This is a placeholder for the actual Bakong API call
            Map response = webClient.post()
                    .uri(apiUrl + "/v1/check_transaction")
                    .header("Authorization", "Bearer " + token)
                    .header("Content-Type", "application/json")
                    .bodyValue(Map.of("external_ref", transactionId))
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (response != null && response.get("responseCode") != null && response.get("responseCode").toString().equals("0")) {
                Map data = (Map) response.get("data");
                if (data != null && "SUCCESS".equals(data.get("status"))) {
                    return "SUCCESS";
                }
            }
            return "PENDING";

        } catch (Exception e) {
            return "ERROR";
        }
    }
}