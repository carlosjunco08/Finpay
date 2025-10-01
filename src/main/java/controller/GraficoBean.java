package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.line.LineChartOptions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

@Named("graficoBean")
@RequestScoped
public class GraficoBean {

    private LineChartModel graficoEdad;
    private LineChartModel graficoFecha;

    @PostConstruct
    public void init() {
        cargarGraficoEdad();
        cargarGraficoFecha();
    }

    private void cargarGraficoEdad() {
        graficoEdad = new LineChartModel();
        ChartData data = new ChartData();

        LineChartDataSet dataSet = new LineChartDataSet();
        dataSet.setLabel("Usuarios por Edad");
        dataSet.setFill(true);
        dataSet.setBorderColor("rgb(0, 122, 255)");
        dataSet.setBackgroundColor("rgba(0, 122, 255, 0.2)");
        dataSet.setShowLine(true);
        dataSet.setBorderWidth(2);
        dataSet.setPointRadius(4);
        dataSet.setPointBackgroundColor("rgb(0, 122, 255)");

        List<Object> valores = new ArrayList<>();
        List<String> etiquetas = new ArrayList<>();

        try {
            URL url = new URL("http://127.0.0.1:5000/edad");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }
            conn.disconnect();

            Gson gson = new Gson();
            Type listType = new TypeToken<List<Map<String, Object>>>() {}.getType();
            List<Map<String, Object>> datos = gson.fromJson(sb.toString(), listType);

            for (Map<String, Object> fila : datos) {
                int edad = ((Number) fila.get("edad")).intValue();
                int cantidad = ((Number) fila.get("cantidad")).intValue();
                etiquetas.add(String.valueOf(edad));
                valores.add(cantidad);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        dataSet.setData(valores);
        data.addChartDataSet(dataSet);
        data.setLabels(etiquetas);

        graficoEdad.setData(data);
        graficoEdad.setOptions(new LineChartOptions());
    }

    private void cargarGraficoFecha() {
        graficoFecha = new LineChartModel();
        ChartData data = new ChartData();

        LineChartDataSet dataSet = new LineChartDataSet();
        dataSet.setLabel("Usuarios por Fecha de Registro");
        dataSet.setFill(true);
        dataSet.setBorderColor("rgb(255, 99, 132)");
        dataSet.setBackgroundColor("rgba(255, 99, 132, 0.2)");
        dataSet.setShowLine(true);
        dataSet.setBorderWidth(2);
        dataSet.setPointRadius(4);
        dataSet.setPointBackgroundColor("rgb(255, 99, 132)");

        List<Object> valores = new ArrayList<>();
        List<String> etiquetas = new ArrayList<>();

        try {
            URL url = new URL("http://127.0.0.1:5000/fecha");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }
            conn.disconnect();

            Gson gson = new Gson();
            Type listType = new TypeToken<List<Map<String, Object>>>() {}.getType();
            List<Map<String, Object>> datos = gson.fromJson(sb.toString(), listType);

            // 🔹 Formatos de fecha
            SimpleDateFormat formatoEntrada = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
            SimpleDateFormat formatoSalida = new SimpleDateFormat("dd/MM/yyyy");

            for (Map<String, Object> fila : datos) {
                String fechaStr = fila.get("fecha").toString();
                int cantidad = ((Number) fila.get("cantidad")).intValue();

                // Parsear fecha
                Date fecha = formatoEntrada.parse(fechaStr);
                String fechaFormateada = formatoSalida.format(fecha);

                etiquetas.add(fechaFormateada);
                valores.add(cantidad);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        dataSet.setData(valores);
        data.addChartDataSet(dataSet);
        data.setLabels(etiquetas);

        graficoFecha.setData(data);
        graficoFecha.setOptions(new LineChartOptions());
    }

    public LineChartModel getGraficoEdad() {
        return graficoEdad;
    }

    public LineChartModel getGraficoFecha() {
        return graficoFecha;
    }
}
