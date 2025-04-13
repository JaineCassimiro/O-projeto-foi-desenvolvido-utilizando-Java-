package com.currencyconverter;

import com.currencyconverter.model.ConversionRecord;
import com.currencyconverter.model.UserPreferences;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for the Currency Converter application.
 * Provides the console interface and menu for the user.
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final CurrencyConverter converter = new CurrencyConverter();
    private static String userName;
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_MAGENTA = "\u001B[35m";
    private static final String ANSI_BOLD = "\u001B[1m";

    public static void main(String[] args) {
        printColoredText("=============================================", ANSI_BLUE);
        printColoredText("  Bem-vindo ao Aplicativo de Conversão de Moedas  ", ANSI_GREEN);
        printColoredText("=============================================", ANSI_BLUE);

        askUserName();

        // Inicializar as preferências do usuário
        converter.initUserPreferences(userName);

        while (true) {
            displayMenu();
            int option = getUserOption();

            if (option == 15) {
                printColoredText("\n=============================================", ANSI_BLUE);
                printColoredText("Obrigado por usar o Conversor de Moedas, " + userName + "!", ANSI_GREEN);
                printColoredText("Esperamos que tenha encontrado as informações que precisava.", ANSI_CYAN);
                printColoredText("Volte sempre que precisar de conversões de moeda em tempo real.", ANSI_CYAN);
                printColoredText("=============================================", ANSI_BLUE);
                break;
            }

            processOption(option);
        }

        scanner.close();
    }

    /**
     * Imprime texto colorido no console usando códigos ANSI.
     *
     * @param text O texto a ser impresso
     * @param color O código de cor ANSI
     */
    private static void printColoredText(String text, String color) {
        System.out.println(color + text + ANSI_RESET);
    }

    private static void askUserName() {
        System.out.print("Por favor, digite seu nome: ");
        userName = scanner.nextLine().trim();

        while (userName.isEmpty()) {
            System.out.print("O nome não pode estar vazio. Por favor, digite seu nome: ");
            userName = scanner.nextLine().trim();
        }

        printColoredText("\n=========================================", ANSI_BLUE);
        printColoredText("Olá, " + userName + "! Bem-vindo ao nosso Conversor de Moedas.", ANSI_GREEN);
        printColoredText("Utilizamos a API Exchange Rate para obter taxas de câmbio em tempo real.", ANSI_CYAN);
        printColoredText("Nossa aplicação suporta diversas moedas internacionais e oferece", ANSI_CYAN);
        printColoredText("múltiplas opções de conversão para facilitar suas operações financeiras.", ANSI_CYAN);
        printColoredText("=========================================", ANSI_BLUE);
    }

    private static void displayMenu() {
        printColoredText("\nOlá, " + userName + "! Por favor, selecione uma opção:", ANSI_GREEN);
        printColoredText("=================== CONVERSÕES ===================", ANSI_YELLOW);
        System.out.println("1. USD para EUR (Dólar Americano para Euro)");
        System.out.println("2. EUR para USD (Euro para Dólar Americano)");
        System.out.println("3. USD para GBP (Dólar Americano para Libra Esterlina)");
        System.out.println("4. GBP para USD (Libra Esterlina para Dólar Americano)");
        System.out.println("5. USD para JPY (Dólar Americano para Iene Japonês)");
        System.out.println("6. EUR para GBP (Euro para Libra Esterlina)");
        System.out.println("7. BRL para USD (Real Brasileiro para Dólar Americano)");
        System.out.println("8. USD para BRL (Dólar Americano para Real Brasileiro)");
        System.out.println("9. EUR para BRL (Euro para Real Brasileiro)");
        printColoredText("================ OUTRAS OPÇÕES =================", ANSI_YELLOW);
        System.out.println("10. Converter moeda personalizada");
        System.out.println("11. Ver histórico de conversões");
        System.out.println("12. Gerenciar moedas favoritas");
        System.out.println("13. Converter usando favoritos");
        System.out.println("14. Iniciar mini-game de moedas");
        System.out.println("15. Sair");
        printColoredText("===============================================", ANSI_YELLOW);
        System.out.print("Digite sua escolha (1-15): ");
    }

    private static int getUserOption() {
        int option = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                option = scanner.nextInt();
                if (option >= 1 && option <= 15) {
                    validInput = true;
                } else {
                    System.out.print("Por favor, digite um número entre 1 e 15: ");
                }
            } catch (InputMismatchException e) {
                System.out.print("Entrada inválida. Por favor, digite um número entre 1 e 15: ");
                scanner.next(); // Clear the invalid input
            }
        }

        scanner.nextLine(); // Consume newline
        return option;
    }

    private static void processOption(int option) {
        if (option >= 1 && option <= 9) {
            String fromCurrency = "";
            String toCurrency = "";

            switch (option) {
                case 1:
                    fromCurrency = "USD";
                    toCurrency = "EUR";
                    break;
                case 2:
                    fromCurrency = "EUR";
                    toCurrency = "USD";
                    break;
                case 3:
                    fromCurrency = "USD";
                    toCurrency = "GBP";
                    break;
                case 4:
                    fromCurrency = "GBP";
                    toCurrency = "USD";
                    break;
                case 5:
                    fromCurrency = "USD";
                    toCurrency = "JPY";
                    break;
                case 6:
                    fromCurrency = "EUR";
                    toCurrency = "GBP";
                    break;
                case 7:
                    fromCurrency = "BRL";
                    toCurrency = "USD";
                    break;
                case 8:
                    fromCurrency = "USD";
                    toCurrency = "BRL";
                    break;
                case 9:
                    fromCurrency = "EUR";
                    toCurrency = "BRL";
                    break;
            }

            performConversion(fromCurrency, toCurrency);
        } else if (option == 10) {
            performCustomConversion();
        } else if (option == 11) {
            showConversionHistory();
        } else if (option == 12) {
            manageFavorites();
        } else if (option == 13) {
            convertUsingFavorites();
        } else if (option == 14) {
            startCurrencyGame();
        }
    }

    /**
     * Inicia o mini-game de moedas para aprendizado interativo.
     */
    private static void startCurrencyGame() {
        printColoredText("\n===== INICIANDO MINI-GAME DE MOEDAS =====", ANSI_MAGENTA);
        printColoredText("Divirta-se aprendendo sobre moedas do mundo todo!", ANSI_CYAN);

        try {
            // Importação e criação da UI do jogo
            com.currencyconverter.game.CurrencyGameUI gameUI = new com.currencyconverter.game.CurrencyGameUI(scanner);
            gameUI.run();
        } catch (Exception e) {
            printColoredText("Erro ao iniciar o mini-game: " + e.getMessage(), ANSI_RED);
            e.printStackTrace();
        }

        printColoredText("Retornando ao menu principal...", ANSI_CYAN);
    }

    /**
     * Permite ao usuário gerenciar sua lista de moedas favoritas.
     */
    private static void manageFavorites() {
        while (true) {
            printColoredText("\n========== Gerenciar Moedas Favoritas ==========", ANSI_MAGENTA);
            List<UserPreferences.CurrencyPair> favorites = converter.getUserPreferences().getFavoritePairs();

            if (favorites.isEmpty()) {
                printColoredText("Você ainda não tem moedas favoritas.", ANSI_YELLOW);
            } else {
                printColoredText("Suas moedas favoritas:", ANSI_CYAN);
                for (int i = 0; i < favorites.size(); i++) {
                    UserPreferences.CurrencyPair pair = favorites.get(i);
                    System.out.printf("%d. %s → %s\n", i + 1, pair.getFromCurrency(), pair.getToCurrency());
                }
            }

            printColoredText("\nOpções:", ANSI_YELLOW);
            System.out.println("1. Adicionar par de moedas aos favoritos");
            System.out.println("2. Remover par de moedas dos favoritos");
            System.out.println("3. Voltar ao menu principal");
            System.out.print("Digite sua escolha (1-3): ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consumir newline

                if (choice == 1) {
                    addFavorite();
                } else if (choice == 2) {
                    removeFavorite(favorites);
                } else if (choice == 3) {
                    break;
                } else {
                    printColoredText("Opção inválida! Por favor, tente novamente.", ANSI_RED);
                }
            } catch (InputMismatchException e) {
                printColoredText("Entrada inválida! Por favor, digite um número.", ANSI_RED);
                scanner.nextLine(); // Limpar entrada inválida
            }
        }
    }

    /**
     * Adiciona um novo par de moedas aos favoritos.
     */
    private static void addFavorite() {
        printColoredText("\n--- Adicionar par de moedas favorito ---", ANSI_CYAN);
        System.out.print("Digite o código da moeda de origem (ex: USD): ");
        String fromCurrency = scanner.nextLine().trim().toUpperCase();

        if (fromCurrency.isEmpty()) {
            printColoredText("Código de moeda inválido!", ANSI_RED);
            return;
        }

        // Verificar se a moeda é válida
        if (!converter.isCurrencySupported(fromCurrency)) {
            printColoredText("Aviso: A moeda '" + fromCurrency + "' pode não ser suportada pela API.", ANSI_YELLOW);
            System.out.print("Deseja continuar mesmo assim? (S/N): ");
            if (!scanner.nextLine().trim().toUpperCase().equals("S")) {
                return;
            }
        }

        System.out.print("Digite o código da moeda de destino (ex: EUR): ");
        String toCurrency = scanner.nextLine().trim().toUpperCase();

        if (toCurrency.isEmpty()) {
            printColoredText("Código de moeda inválido!", ANSI_RED);
            return;
        }

        // Verificar se a moeda é válida
        if (!converter.isCurrencySupported(toCurrency)) {
            printColoredText("Aviso: A moeda '" + toCurrency + "' pode não ser suportada pela API.", ANSI_YELLOW);
            System.out.print("Deseja continuar mesmo assim? (S/N): ");
            if (!scanner.nextLine().trim().toUpperCase().equals("S")) {
                return;
            }
        }

        boolean added = converter.addFavoritePair(fromCurrency, toCurrency);

        if (added) {
            printColoredText("Par de moedas " + fromCurrency + " → " + toCurrency + " adicionado aos favoritos!", ANSI_GREEN);
        } else {
            printColoredText("Este par de moedas já está nos favoritos.", ANSI_YELLOW);
        }
    }

    /**
     * Remove um par de moedas dos favoritos.
     *
     * @param favorites Lista de pares de moedas favoritos
     */
    private static void removeFavorite(List<UserPreferences.CurrencyPair> favorites) {
        if (favorites.isEmpty()) {
            printColoredText("Não há favoritos para remover.", ANSI_YELLOW);
            return;
        }

        printColoredText("\n--- Remover par de moedas favorito ---", ANSI_CYAN);
        System.out.print("Digite o número do par a ser removido (1-" + favorites.size() + "): ");

        try {
            int index = scanner.nextInt();
            scanner.nextLine(); // Consumir newline

            if (index < 1 || index > favorites.size()) {
                printColoredText("Número inválido!", ANSI_RED);
                return;
            }

            boolean removed = converter.removeFavoritePair(index - 1);

            if (removed) {
                printColoredText("Par de moedas removido dos favoritos!", ANSI_GREEN);
            } else {
                printColoredText("Não foi possível remover o par de moedas.", ANSI_RED);
            }
        } catch (InputMismatchException e) {
            printColoredText("Entrada inválida! Por favor, digite um número.", ANSI_RED);
            scanner.nextLine(); // Limpar entrada inválida
        }
    }

    /**
     * Permite ao usuário converter usando seus pares de moedas favoritos.
     */
    private static void convertUsingFavorites() {
        List<UserPreferences.CurrencyPair> favorites = converter.getUserPreferences().getFavoritePairs();

        if (favorites.isEmpty()) {
            printColoredText("\nVocê ainda não tem moedas favoritas.", ANSI_YELLOW);
            printColoredText("Adicione pares de moedas aos favoritos primeiro.", ANSI_YELLOW);
            System.out.println("\nPressione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        printColoredText("\n========== Converter Usando Favoritos ==========", ANSI_MAGENTA);
        printColoredText("Escolha um par de moedas favorito:", ANSI_CYAN);

        for (int i = 0; i < favorites.size(); i++) {
            UserPreferences.CurrencyPair pair = favorites.get(i);
            System.out.printf("%d. %s → %s\n", i + 1, pair.getFromCurrency(), pair.getToCurrency());
        }

        System.out.print("\nDigite o número do par desejado (1-" + favorites.size() + "): ");

        try {
            int index = scanner.nextInt();
            scanner.nextLine(); // Consumir newline

            if (index < 1 || index > favorites.size()) {
                printColoredText("Número inválido!", ANSI_RED);
                return;
            }

            UserPreferences.CurrencyPair selectedPair = favorites.get(index - 1);
            performConversion(selectedPair.getFromCurrency(), selectedPair.getToCurrency());

        } catch (InputMismatchException e) {
            printColoredText("Entrada inválida! Por favor, digite um número.", ANSI_RED);
            scanner.nextLine(); // Limpar entrada inválida
        }
    }

    /**
     * Permite ao usuário converter entre quaisquer moedas suportadas pela API.
     */
    private static void performCustomConversion() {
        printColoredText("\n==== Conversão Personalizada ====", ANSI_YELLOW);
        System.out.print("Digite o código da moeda de origem (ex: USD, EUR, BRL): ");
        String fromCurrency = scanner.nextLine().trim().toUpperCase();

        if (fromCurrency.isEmpty()) {
            printColoredText("Código de moeda inválido!", ANSI_RED);
            return;
        }

        System.out.print("Digite o código da moeda de destino (ex: USD, EUR, BRL): ");
        String toCurrency = scanner.nextLine().trim().toUpperCase();

        if (toCurrency.isEmpty()) {
            printColoredText("Código de moeda inválido!", ANSI_RED);
            return;
        }

        performConversion(fromCurrency, toCurrency);
    }

    /**
     * Exibe o histórico de conversões e oferece opção para salvar em arquivo.
     */
    private static void showConversionHistory() {
        List<ConversionRecord> history = converter.getConversionHistory();

        if (history.isEmpty()) {
            printColoredText("\nHistórico de conversões vazio.", ANSI_YELLOW);
            System.out.println("\nPressione Enter para continuar...");
            scanner.nextLine();
            return;
        }

        printColoredText("\n========== Histórico de Conversões ==========", ANSI_BLUE);
        int count = 1;
        for (ConversionRecord record : history) {
            System.out.println(count + ". " + record);
            count++;
        }
        printColoredText("===========================================", ANSI_BLUE);

        // Perguntar se deseja salvar em arquivo
        System.out.print("\nDeseja salvar o histórico em arquivo? (S/N): ");
        String saveOption = scanner.nextLine().trim().toUpperCase();

        if (saveOption.equals("S")) {
            String fileName = "conversoes_" + userName.replace(" ", "_") + ".csv";
            try {
                converter.saveHistoryToFile(fileName);
            } catch (IOException e) {
                printColoredText("Erro ao salvar arquivo: " + e.getMessage(), ANSI_RED);
            }
        }

        System.out.println("\nPressione Enter para continuar...");
        scanner.nextLine();
    }

    private static void performConversion(String fromCurrency, String toCurrency) {
        printColoredText("\nConvertendo de " + fromCurrency + " para " + toCurrency, ANSI_CYAN);
        System.out.print("Digite o valor a ser convertido: ");

        double amount = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                amount = scanner.nextDouble();
                if (amount < 0) {
                    System.out.print("Por favor, digite um número positivo: ");
                } else {
                    validInput = true;
                }
            } catch (InputMismatchException e) {
                System.out.print("Entrada inválida. Por favor, digite um número válido: ");
                scanner.next(); // Clear the invalid input
            }
        }

        scanner.nextLine(); // Consume newline

        try {
            double convertedAmount = converter.convert(amount, fromCurrency, toCurrency);
            String formattedResult = converter.getFormatter().formatWithSymbol(convertedAmount, toCurrency);
            String formattedAmount = converter.getFormatter().formatWithSymbol(amount, fromCurrency);
            double exchangeRate = converter.getLastRate();

            printColoredText("\n===== Resultado da Conversão =====", ANSI_GREEN);
            printColoredText(formattedAmount + " = " + formattedResult, ANSI_YELLOW);
            System.out.printf("Taxa de câmbio atual: 1 %s = %.4f %s\n", fromCurrency, exchangeRate, toCurrency);
            printColoredText("Dados obtidos em tempo real da API Exchange Rate", ANSI_CYAN);
            printColoredText("==================================", ANSI_GREEN);

            System.out.println("\nPressione Enter para continuar...");
            scanner.nextLine();
        } catch (Exception e) {
            printColoredText("\n===== ERRO =====", ANSI_RED);
            printColoredText("Erro durante a conversão: " + e.getMessage(), ANSI_RED);
            if (e.getMessage().contains("API") || e.getMessage().contains("internet")) {
                printColoredText("Ops! Parece que o mercado financeiro está tirando uma soneca.", ANSI_YELLOW);
                printColoredText("Verifique sua conexão com a internet ou tente novamente mais tarde.", ANSI_YELLOW);
            } else if (e.getMessage().contains("suportada")) {
                printColoredText("Essa moeda não é suportada pela API. Verifique o código da moeda.", ANSI_YELLOW);
                printColoredText("Exemplos de códigos válidos: USD, EUR, BRL, GBP, JPY, CAD, AUD", ANSI_YELLOW);
            } else {
                printColoredText("Verifique os dados e tente novamente.", ANSI_YELLOW);
            }
            printColoredText("================", ANSI_RED);

            System.out.println("\nPressione Enter para continuar...");
            scanner.nextLine();
        }
    }
}
