package controller;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Named
@RequestScoped
public class CorreoBean {
    private String asunto;
    private String mensaje;

    public String getAsunto() { return asunto; }
    public void setAsunto(String asunto) { this.asunto = asunto; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public String enviarCorreos() {
        try {
            URL url = new URL("http://localhost:8081/enviar-correos");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

            // IMPORTANTE: enviar "contenido" y no "mensaje"
            String jsonInputString = String.format(
                "{\"asunto\":\"%s\",\"contenido\":\"%s\"}",
                asunto, mensaje.replace("\"", "\\\"")
            );

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int code = conn.getResponseCode();
            if (code == 200) {
                return "enviarCorreos.xhtml?faces-redirect=true";
            } else {
                throw new RuntimeException("Error en API: " + code);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
