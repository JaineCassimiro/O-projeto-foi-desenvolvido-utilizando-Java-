package com.currencyconverter.api;

import com.currencyconverter.model.ExchangeRateResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * Class responsible for communicating with the exchange rate API.
 * Fetches real-time exchange rates for currency conversion.
 */
public class ExchangeRateApi {
    private static final String API_URL = "https://open.er-api.com/v6/latest/";
    private final OkHttpClient client;
    private final ObjectMapper objectMapper;
    
    public ExchangeRateApi() {
        this.client = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
    }
    
    /**
     * Retrieves the latest exchange rates for the specified base currency.
     * 
     * @param baseCurrency The base currency code (e.g., "USD")
     * @return ExchangeRateResponse containing the rates
     * @throws IOException If there's an error communicating with the API
     * @throws RuntimeException If the API response cannot be parsed
     */
    public ExchangeRateResponse getExchangeRates(String baseCurrency) throws IOException {
        if (baseCurrency == null || baseCurrency.trim().isEmpty()) {
            throw new IllegalArgumentException("A moeda base não pode estar vazia");
        }
        
        String url = API_URL + baseCurrency.toUpperCase().trim();
        
        Request request = new Request.Builder()
                .url(url)
                .build();
        
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Código de resposta inesperado: " + response);
            }
            
            String responseBody = response.body().string();
            return objectMapper.readValue(responseBody, ExchangeRateResponse.class);
        } catch (IOException e) {
            System.out.println("Falha na requisição à API: " + e.getMessage());
            throw new IOException("Não foi possível conectar ao serviço de taxas de câmbio. Verifique sua conexão com a internet.", e);
        } catch (Exception e) {
            System.out.println("Erro ao processar resposta da API: " + e.getMessage());
            throw new RuntimeException("Falha ao processar dados de taxa de câmbio", e);
        }
    }
}
