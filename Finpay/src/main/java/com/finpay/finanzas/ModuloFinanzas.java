package com.finpay.finanzas;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.primefaces.model.chart.PieChartModel;

/**
 * Módulo de Finanzas Personales
 */
@Named("financeBean")
@SessionScoped
public class ModuloFinanzas implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Transaction> transactions = new ArrayList<>();
    private Transaction formTransaction = new Transaction();

    private DianClient dianClient = new DianClient();

    // Modelo del gráfico
    private PieChartModel chartModel;

    // --- Métodos CRUD locales ---
    public void addTransaction() {
        transactions.add(formTransaction);
        formTransaction = new Transaction(); // limpiar el formulario
        actualizarChart();
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public Transaction getFormTransaction() {
        return formTransaction;
    }

    public void setFormTransaction(Transaction formTransaction) {
        this.formTransaction = formTransaction;
    }

    // --- Dashboard / Gráfico ---
    public PieChartModel getChartModel() {
        if (chartModel == null) {
            actualizarChart();
        }
        return chartModel;
    }

    public void actualizarChart() {
        chartModel = new PieChartModel();

        double ingresos = transactions.stream()
                .filter(t -> "Ingreso".equalsIgnoreCase(t.getType()))
                .mapToDouble(Transaction::getAmount)
                .sum();

        double gastos = transactions.stream()
                .filter(t -> "Gasto".equalsIgnoreCase(t.getType()))
                .mapToDouble(Transaction::getAmount)
                .sum();

        chartModel.set("Ingresos", ingresos);
        chartModel.set("Gastos", gastos);
    }

    // --- Cliente DIAN interno ---
    public static class DianClient {
        private final Gson gson;
        private String baseUrl = "https://www.dian.gov.co/"; // placeholder
        private String token;

        public DianClient() {
            gson = new Gson();
        }

        public Map<String, Object> consultarAdquiriente(String tipoDoc, String numeroDoc) throws Exception {
            String path = String.format(
                    "/impuestos/factura-electronica/api/consultaAdquiriente?tipo=%s&numero=%s",
                    tipoDoc, numeroDoc);
            URL url = new URL(baseUrl + path);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json");
            // con.setRequestProperty("Authorization", "Bearer " + getToken());

            int status = con.getResponseCode();
            if (status == 200) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                    String inputLine;
                    StringBuilder content = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }
                    return gson.fromJson(content.toString(), new TypeToken<Map<String, Object>>() {}.getType());
                }
            } else {
                throw new RuntimeException("DIAN service error: " + status);
            }
        }

        private String getToken() {
            if (token == null) {
                token = "__TOKEN_DE_PRUEBA__"; // simulado
            }
            return token;
        }
    }

    /**
     * Clase interna para transacciones financieras
     */
    public static class Transaction {
        private double amount;
        private String type;     // ingreso / gasto
        private String category; // categoría
        private Date date = new Date();

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }
    }
}
