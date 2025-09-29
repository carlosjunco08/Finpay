package security;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Filtro implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest solicitud = (HttpServletRequest) request;
        HttpServletResponse respuesta = (HttpServletResponse) response;

        respuesta.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        respuesta.setHeader("Pragma", "no-cache");
        respuesta.setDateHeader("Expires", 0);

        HttpSession sesion = solicitud.getSession(false);
        String rutaSolicitud = solicitud.getRequestURI();
        String raiz = solicitud.getContextPath();

        // permitir recursos gestionados por JSF
        if (rutaSolicitud.startsWith(raiz + "/javax.faces.resource/")) {
            chain.doFilter(request, response);
            return;
        }

        boolean validarSesion = (sesion != null && sesion.getAttribute("usuario") != null);

        // Rutas públicas (no requieren sesión)
        boolean esRutaPublica =
                rutaSolicitud.equals(raiz + "/") ||
                rutaSolicitud.equals(raiz + "/newLogin.xhtml") ||
                rutaSolicitud.equals(raiz + "/registro.xhtml");

        // Recursos estáticos (CSS, JS, imágenes)
        boolean esRecursoEstatico = rutaSolicitud.matches(raiz + "/resources/.*\\.(css|js|png|jpg|jpeg|gif|woff|woff2|ttf)$");

        if (validarSesion || esRutaPublica || esRecursoEstatico) {
            chain.doFilter(request, response);
        } else {
            respuesta.sendRedirect(raiz + "/newLogin.xhtml");
        }
    }

    @Override
    public void destroy() {
    }
}
