package com.chat_bot;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class QnAService {

    private final WebClient webClient;
    public QnAService(WebClient.Builder webClient) {
        this.webClient = webClient.build();
    }

    @Value("${gemini.api.url}")
    private  String geminiApiUrl;
    @Value("${gemini.api.key}")
    private  String geminiApiKey;
    public String getAnswer(String question) {

        //Construct the Payload
        Map<String,Object>requestBody=Map.of(
            "contents",new Object[]{
                Map.of(
                    "parts",new Object[]{
                        Map.of(
                            "text",question
                        )
                    }
                )
            }
        );

        //make api call
       String response= webClient.post()
                .uri(geminiApiUrl+geminiApiKey)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
                //return response
        return response;
    }

}
