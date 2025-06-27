package com.github.kaydunov.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ExchangeRateService {

    private static final String API_KEY = "e4040867711a88193d64c4b0";
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";
    

    public BigDecimal getExchangeRate(String fromCurrency, String toCurrency) {
        try {
            String urlStr = API_URL + fromCurrency;
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();

                Map<String, Object> ratesData = parseJson(response.toString());
                Map<String, Object> conversionRates = (Map<String, Object>) ratesData.get("conversion_rates");
                if (conversionRates.containsKey(toCurrency)) {
                    return new BigDecimal(conversionRates.get(toCurrency).toString());
                } else {
                    throw new RuntimeException("Exchange rate for " + toCurrency + " not found");
                }
            } else {
                throw new RuntimeException("API Error: " + responseCode);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to get exchange rates: " + e.getMessage(), e);
        }
    }

    // Парсинг JSON
    private Map<String, Object> parseJson(String json) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, HashMap.class);
    }
}
