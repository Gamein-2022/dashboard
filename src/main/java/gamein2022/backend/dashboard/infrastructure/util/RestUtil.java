package gamein2022.backend.dashboard.infrastructure.util;

import com.google.gson.JsonObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class RestUtil {
    public static void sendSMS(String baseUrl, String token, String receptor, String template) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format(baseUrl + "?receptor=%s&token=%s&template=%s",receptor,token,template);
        ResponseEntity<String> response = restTemplate.getForEntity(url,String.class);
        if (!response.getStatusCode().equals(HttpStatus.OK)){
            throw new Exception("خطا در ارسال پبامک");
        }
    }

    public static String sendRawRequest(String url, Map<String, String> params, HttpMethod method, MediaType mediaType) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        if (mediaType != null)
            headers.setContentType(mediaType);

        JsonObject properties = new JsonObject();
        params.keySet().forEach(key -> {
            properties.addProperty(key, params.get(key));
        });

        HttpEntity<String> request = new HttpEntity<>(properties.toString(), headers);
        ResponseEntity<String> response = restTemplate.exchange(url, method, request, String.class);

        return response.getBody();
    }

    public static void sendNotificationToATeam(String text,String type,String teamId,String liveUrl){
        Map<String, String> params = new HashMap<>();
        params.put("teamId", teamId);
        params.put("type", type);
        params.put("message", text);
        RestUtil.sendRawRequest(liveUrl + "/team", params, HttpMethod.POST, MediaType.APPLICATION_JSON);
    }

    public static void sendNotificationToAll(String text,String type,String liveUrl){
        Map<String, String> params = new HashMap<>();
        params.put("type", type);
        params.put("message", text);
        RestUtil.sendRawRequest(liveUrl, params, HttpMethod.POST, MediaType.APPLICATION_JSON);
    }

    public static String sendRawRequestByToken(String token, String url, Map<String, String> params, HttpMethod method, MediaType mediaType) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.set("G-BT-TOKEN", token);
        if (mediaType != null)
            headers.setContentType(mediaType);

        JsonObject properties = new JsonObject();
        params.keySet().forEach(key -> {
            properties.addProperty(key, params.get(key));
        });

        HttpEntity<String> request = new HttpEntity<>(properties.toString(), headers);
        ResponseEntity<String> response = restTemplate.exchange(url, method, request, String.class);

        return response.getBody();
    }
}
