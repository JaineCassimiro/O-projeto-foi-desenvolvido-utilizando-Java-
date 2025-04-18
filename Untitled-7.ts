import { apiRequest } from "./queryClient";

// Types for API responses
export type ExchangeRatesResponse = {
  success: boolean;
  timestamp: number;
  rates: Record<string, number>;
  base: string;
};

export type ConversionHistoryItem = {
  id: number;
  fromCurrency: string;
  toCurrency: string;
  fromAmount: number;
  toAmount: number;
  rate: number;
  timestamp: string;
};

export type ConversionHistoryResponse = {
  success: boolean;
  data: ConversionHistoryItem[];
};

export type HistoricalRatesResponse = {
  success: boolean;
  data: Array<{ date: string; rate: number }>;
  from: string;
  to: string;
};

// Currency information with names and codes
export type CurrencyInfo = {
  code: string;
  name: string;
  flagCode?: string;
};

// List of supported currencies
export const currencies: CurrencyInfo[] = [
  { code: "BRL", name: "Real Brasileiro", flagCode: "br" },
  { code: "USD", name: "Dólar Americano", flagCode: "us" },
  { code: "EUR", name: "Euro", flagCode: "eu" },
  { code: "GBP", name: "Libra Esterlina", flagCode: "gb" },
  { code: "JPY", name: "Iene Japonês", flagCode: "jp" },
  { code: "AUD", name: "Dólar Australiano", flagCode: "au" },
  { code: "CAD", name: "Dólar Canadense", flagCode: "ca" },
  { code: "CHF", name: "Franco Suíço", flagCode: "ch" }
];

// Get currency info by code
export function getCurrencyInfo(code: string): CurrencyInfo {
  return currencies.find(currency => currency.code === code) || { code, name: code };
}

// Format currency for display
export function formatCurrency(amount: number, currencyCode: string): string {
  return new Intl.NumberFormat("pt-BR", {
    style: "currency",
    currency: currencyCode,
    minimumFractionDigits: 2,
    maximumFractionDigits: 2
  }).format(amount);
}

// Format date for display
export function formatDate(dateString: string): string {
  const date = new Date(dateString);
  return date.toLocaleDateString("pt-BR", {
    day: "numeric",
    month: "long",
    year: "numeric",
    hour: "2-digit",
    minute: "2-digit"
  });
}

// API functions
export async function getExchangeRates(baseCurrency: string): Promise<ExchangeRatesResponse> {
  const response = await apiRequest(
    "GET",
    `/api/exchange-rates?base=${baseCurrency}`,
    undefined
  );
  return await response.json();
}

export async function saveConversion(
  fromCurrency: string,
  toCurrency: string,
  fromAmount: number,
  toAmount: number,
  rate: number
): Promise<ConversionHistoryItem> {
  const response = await apiRequest(
    "POST",
    "/api/conversion-history",
    { fromCurrency, toCurrency, fromAmount, toAmount, rate }
  );
  const data = await response.json();
  return data.data;
}

export async function getConversionHistory(
  limit: number = 5,
  offset: number = 0
): Promise<ConversionHistoryResponse> {
  const response = await apiRequest(
    "GET",
    `/api/conversion-history?limit=${limit}&offset=${offset}`,
    undefined
  );
  return await response.json();
}

export async function getHistoricalRates(
  fromCurrency: string,
  toCurrency: string,
  days: number = 30
): Promise<HistoricalRatesResponse> {
  const response = await apiRequest(
    "GET",
    `/api/historical-rates?from=${fromCurrency}&to=${toCurrency}&days=${days}`,
    undefined
  );
  return await response.json();
}
