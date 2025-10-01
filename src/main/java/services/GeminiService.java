package services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.ejb.Stateless;
import javax.annotation.Resource; 
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray; // Asegúrate de que esta importación es necesaria si no usas el nombre completo
import java.util.Locale;

@Stateless
public class GeminiService implements GeminiServiceLocal {

    // 1. INYECCIÓN JNDI: El servidor debe inyectar el valor aquí.
    @Resource(lookup = "GeminiApiKey") 
    private String apiKey; 

    private static final String API_ENDPOINT = "https://generativelanguage.googleapis.com/v1beta/models/";
    private static final String MODEL_NAME = "gemini-2.5-flash";

    @Override
    public String consultarIA(String pregunta, String contexto) {
        
        // 1. Verificación de inyección
        if (this.apiKey == null || this.apiKey.isEmpty()) {
            return "ERROR CRÍTICO: El recurso JNDI 'GeminiApiKey' no fue inyectado. Por favor, verifica la configuración del Custom Resource en GlassFish.";
        }

        // 2. Unir pregunta y contexto para el prompt 
        String prompt = String.format(Locale.ROOT,
                "Eres un asesor financiero experto y amigable llamado FinPay AI. %s. Basado en esta información, y de manera concisa y profesional, responde a la siguiente consulta: '%s'. Usa formato HTML básico si lo consideras útil (ej. <strong>, <br/>) para la respuesta.",
                contexto, pregunta);

        // 3. Construir el cuerpo JSON 
        JsonObject root = new JsonObject();
        JsonObject content = new JsonObject();
        content.addProperty("role", "user");
        
        JsonObject part = new JsonObject();
        part.addProperty("text", prompt);
        
        // Uso de com.google.gson.JsonArray para evitar ambigüedad
        JsonArray parts = new JsonArray();
        parts.add(part);
        
        content.add("parts", parts);
        
        JsonArray contents = new JsonArray();
        contents.add(content);
        
        root.add("contents", contents);

        // Opciones de configuración
        // 🔥 CORRECCIÓN: El campo para la configuración de la generación debe llamarse "generationConfig"
        JsonObject config = new JsonObject();
        config.addProperty("temperature", 0.7);
        root.add("generationConfig", config); // ✅ Línea corregida

        String jsonInput = root.toString();

        try {
            // 4. Configurar y Abrir Conexión HTTP
            URL url = new URL(API_ENDPOINT + MODEL_NAME + ":generateContent?key=" + this.apiKey);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            conn.setConnectTimeout(8000);
            conn.setReadTimeout(20000);

            // 5. Enviar la petición
            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonInput.getBytes(StandardCharsets.UTF_8));
            }

            // 6. Leer y manejar respuesta
            StringBuilder response = new StringBuilder();
            int responseCode = conn.getResponseCode();

            if (responseCode != HttpURLConnection.HTTP_OK) {
                // ... (manejo de error de la API)
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                }
                return "Error de API (" + responseCode + "): " + response.toString();
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
            }
            conn.disconnect();

            // 7. Procesar la respuesta JSON (Extraer el texto)
            JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();

            if (jsonResponse.has("candidates")) {
                // Navegación segura en el JSON para obtener el texto
                String text = jsonResponse.getAsJsonArray("candidates")
                                         .get(0).getAsJsonObject()
                                         .getAsJsonObject("content")
                                         .getAsJsonArray("parts")
                                         .get(0).getAsJsonObject()
                                         .get("text").getAsString();
                return text;
            } else {
                return "Respuesta de la IA no tiene el formato esperado.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error interno del sistema: " + e.getMessage();
        }
    }
}