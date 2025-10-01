package controller;

import entities.Pais;
import entities.Rol;
import entities.Usuario;
import entities.EstadoRol;

import javax.inject.Named;
import java.io.*;
import java.io.Serializable;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import services.PaisFacadeLocal;
import services.RolFacadeLocal;
import services.UsuarioFacadeLocal;
import services.EstadoRolFacadeLocal;

import com.google.gson.Gson;
import java.util.stream.Collectors;

import org.mindrot.jbcrypt.BCrypt;

// iText 7 imports
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

@Named(value = "usuariosController")
@SessionScoped
public class UsuariosController implements Serializable {

    private Usuario user;
    private Rol rol;
    private Pais pais;

    @EJB
    private PaisFacadeLocal pfl;
    @EJB
    private UsuarioFacadeLocal ufl;
    @EJB
    private RolFacadeLocal rfl; // Usado para buscar el Rol ID 2
    @EJB
    private EstadoRolFacadeLocal estadoRolFacade;

    private List<SelectItem> listaPaises;
    private List<SelectItem> listarRoles;
    private Part archivoCsv;

    // ========= GETTERS & SETTERS =========
    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public Part getArchivoCsv() {
        return archivoCsv;
    }

    public void setArchivoCsv(Part archivoCsv) {
        this.archivoCsv = archivoCsv;
    }

    // ========= INIT =========
    @PostConstruct
    public void init() {
        user = new Usuario();
        rol = new Rol();
        pais = new Pais();

        // Inicializar las entidades anidadas para evitar NullPointerExceptions
        user.setRolId(new Rol()); 
        user.setPaisId(new Pais());
    }

    public UsuariosController() {
    }

    // ========= LISTAR =========
    public List<Usuario> listarUsuarios() {
        try {
            return this.ufl.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    // ========= PDF =========
    public void generarPdfUsuarios() {
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();

            response.reset();
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"usuarios.pdf\"");

            // Crear PDF
            PdfWriter writer = new PdfWriter(response.getOutputStream());
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Reporte de Usuarios").setBold().setFontSize(16));

            // Tabla con 5 columnas
            float[] columnWidths = {100f, 100f, 120f, 120f, 80f};
            Table table = new Table(columnWidths);

            // Encabezados
            table.addHeaderCell("Nombre");
            table.addHeaderCell("Apellido");
            table.addHeaderCell("Correo");
            table.addHeaderCell("Rol");
            table.addHeaderCell("País");

            // Datos
            for (Usuario u : listarUsuarios()) {
                table.addCell(u.getNombres() != null ? u.getNombres() : "");
                table.addCell(u.getApellidos() != null ? u.getApellidos() : "");
                table.addCell(u.getCorreo() != null ? u.getCorreo() : "");
                table.addCell(u.getRolId() != null ? u.getRolId().getNombreRol() : "");
                table.addCell(u.getPaisId() != null ? u.getPaisId().getNombrePais() : "");
            }

            document.add(table);
            document.close();

            fc.responseComplete();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMsg(FacesMessage.SEVERITY_ERROR, "Error generando PDF: " + e.getMessage());
        }
    }

    // ========= LISTAR PAISES =========
    public List<SelectItem> listarPaises() {
        listaPaises = new ArrayList<>();
        try {
            for (Pais pais : this.pfl.findAll()) {
                listaPaises.add(new SelectItem(pais.getIdpais(), pais.getNombrePais()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaPaises;
    }

    // ========= LISTAR ESTADOS =========
    public List<EstadoRol> listarEstadosRol() {
        try {
            return estadoRolFacade.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // ========= CREAR USUARIO =========
    
    public String crearUsuario() {
        try {
            // Validaciones de unicidad de datos
            if (ufl.findByIdentificacion(user.getIdentificacion()) != null) {
                mostrarMsg(FacesMessage.SEVERITY_ERROR, "Este Documento Ya Existe");
                return null;
            }
            if (ufl.findByUsername(user.getNombreUsuario()) != null) {
                mostrarMsg(FacesMessage.SEVERITY_ERROR, "Este nombre de usuario ya Existe");
                return null;
            }
            if (ufl.findByCorreo(user.getCorreo()) != null) {
                mostrarMsg(FacesMessage.SEVERITY_ERROR, "Este Correo Ya Existe");
                return null;
            }

            // Hasheo de contraseña con BCrypt
            if (user.getContrasena() != null && !user.getContrasena().isEmpty()) {
                String hashed = BCrypt.hashpw(user.getContrasena(), BCrypt.gensalt());
                user.setContrasena(hashed);
            } else {
                mostrarMsg(FacesMessage.SEVERITY_ERROR, "Debe ingresar una contrasena válida");
                return null;
            }

            // 🛑 MODIFICACIÓN CLAVE: Asigna el Rol ID 1 por defecto (User/Cliente)
            user.setRolId(rfl.find(1));
            
            // Asigna el país seleccionado en el formulario
            user.setPaisId(pfl.find(user.getPaisId().getIdpais()));

            ufl.create(user);

            
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            mostrarMsg(FacesMessage.SEVERITY_INFO, "Usuario registrado correctamente");

            return "/newLogin.xhtml?faces-redirect=true";

        } catch (ConstraintViolationException ex) {
            for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
                System.out.println("Error en propiedad: " + violation.getPropertyPath() + " - " + violation.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMsg(FacesMessage.SEVERITY_FATAL, "Error al registrar usuario: " + e.getMessage());
        }
        return null;
    }

    // ========= EDITAR =========
    public void editarUserOne(Usuario u) {
        this.user = u;
    }

    public void editarStepTwo() {
        try {
            user.setRolId(rfl.find(user.getRolId().getIdrol()));
            user.setPaisId(pfl.find(user.getPaisId().getIdpais()));

            if (user.getContrasena() != null && !user.getContrasena().startsWith("$2a$")) {
                user.setContrasena(BCrypt.hashpw(user.getContrasena(), BCrypt.gensalt()));
            }

            ufl.edit(user);
            mostrarMsg(FacesMessage.SEVERITY_INFO, "Usuario Editado Exitosamente");
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMsg(FacesMessage.SEVERITY_ERROR, "Error al editar usuario");
        }
    }

    // ========= ELIMINAR =========
    public void eliminar(Usuario user) {
        try {
            ufl.remove(user);
            mostrarMsg(FacesMessage.SEVERITY_INFO, "Usuario Eliminado Exitosamente");
        } catch (Exception e) {
            mostrarMsg(FacesMessage.SEVERITY_ERROR, "Error al eliminar usuario: " + e.getMessage());
        }
    }

    // ========= LISTAR ROLES (Para uso de administrador, no de registro) =========
    public List<SelectItem> listarRol() {
        listarRoles = new ArrayList<>();
        try {
            // Solo lista el rol "Cliente" para compatibilidad con tu código JSF original
            // Aunque ya no se usa para registro, se mantiene por si lo usas en otro lado.
            for (Rol rol : rfl.findAll()) {
                if ("Cliente".equalsIgnoreCase(rol.getNombreRol())) {
                    listarRoles.add(new SelectItem(rol.getIdrol(), rol.getNombreRol()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listarRoles;
    }

    public List<SelectItem> listarRolAdmin() {
        listarRoles = new ArrayList<>();
        try {
            for (Rol rol : rfl.findAll()) {
                listarRoles.add(new SelectItem(rol.getIdrol(), rol.getNombreRol()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listarRoles;
    }

    // ========= CARGAR CSV (Se mantiene la lógica de Rol Cliente/User) =========
    public void cargarUsuariosDesdeCsv() {
        if (archivoCsv == null) {
            mostrarMsg(FacesMessage.SEVERITY_ERROR, "Debe seleccionar un archivo CSV");
            return;
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(archivoCsv.getInputStream()))) {
            String linea;
            int contador = 0;

            br.readLine(); // Saltar encabezado

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");

                if (datos.length < 10) {
                    continue;
                }

                Usuario nuevo = new Usuario();
                nuevo.setNombres(datos[0].trim());
                nuevo.setApellidos(datos[1].trim());
                nuevo.setIdentificacion(datos[2].trim());
                nuevo.setCorreo(datos[3].trim());
                nuevo.setTelefono(datos[4].trim());
                nuevo.setDireccion(datos[5].trim());
                nuevo.setEdad(Integer.parseInt(datos[6].trim()));

                String contrasenaPlano = datos[7].trim();
                if (contrasenaPlano != null && !contrasenaPlano.startsWith("$2a$")) {
                    String hashed = BCrypt.hashpw(contrasenaPlano, BCrypt.gensalt());
                    nuevo.setContrasena(hashed);
                } else {
                    nuevo.setContrasena(contrasenaPlano);
                }

                int paisId = Integer.parseInt(datos[8].trim());
                nuevo.setPaisId(pfl.find(paisId));

                nuevo.setNombreUsuario(datos[9].trim());

                Rol rolCliente = rfl.findAll().stream()
                        .filter(r -> "Cliente".equalsIgnoreCase(r.getNombreRol()))
                        .findFirst()
                        .orElse(null);
                if (rolCliente != null) {
                    nuevo.setRolId(rolCliente);
                }

                try {
                    ufl.create(nuevo);
                    contador++;
                } catch (Exception e) {
                    System.err.println("❌ Error insertando usuario " + nuevo.getNombreUsuario() + ": " + e.getMessage());
                }
            }

            mostrarMsg(FacesMessage.SEVERITY_INFO, "✅ Se cargaron " + contador + " usuarios desde el CSV");

        } catch (Exception e) {
            e.printStackTrace();
            mostrarMsg(FacesMessage.SEVERITY_FATAL, "❌ Error al cargar el archivo CSV: " + e.getMessage());
        }
    }

    // ========= JSON =========
    public String getJsonUsuariosPorRol() {
        Map<String, Integer> conteoPorRol = new HashMap<>();
        for (Usuario u : ufl.findAll()) {
            String nombreRol = u.getRolId().getNombreRol();
            conteoPorRol.put(nombreRol, conteoPorRol.getOrDefault(nombreRol, 0) + 1);
        }
        return new Gson().toJson(conteoPorRol);
    }

    public String getJsonUsuariosPorPais() {
        try {
            List<Usuario> usuarios = ufl.findAll();
            Map<String, Long> usuariosPorPais = usuarios.stream()
                    .filter(u -> u.getPaisId() != null && u.getPaisId().getNombrePais() != null)
                    .collect(Collectors.groupingBy(u -> u.getPaisId().getNombrePais(), Collectors.counting()));
            return new Gson().toJson(usuariosPorPais);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "[]";
    }

    // ========= UTIL =========
    private void mostrarMsg(FacesMessage.Severity tipo, String detalle) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(tipo, detalle, null));
    }
}