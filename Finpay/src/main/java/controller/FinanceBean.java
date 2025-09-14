/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean
@ViewScoped
public class FinanceBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Double incomeAmount;
    private String incomeDescription;
    private Date incomeDate;
    private String incomeCategory;

    private Double expenseAmount;
    private String expenseDescription;
    private Date expenseDate;
    private String expenseCategory;

    // Lista de categorías disponibles para el selector
    private List<SelectItem> categories;

    public FinanceBean() {
        // Inicializar categorías
        categories = new ArrayList<>();
        categories.add(new SelectItem("", "Seleccione una categoría")); // Opción por defecto
        categories.add(new SelectItem("Salario", "Salario"));
        categories.add(new SelectItem("Inversiones", "Inversiones"));
        categories.add(new SelectItem("Regalo", "Regalo"));
        categories.add(new SelectItem("Ventas", "Ventas"));
        categories.add(new SelectItem("Alquiler", "Alquiler"));
        categories.add(new SelectItem("Otros Ingresos", "Otros Ingresos"));

        categories.add(new SelectItem("Alimentos", "Alimentos"));
        categories.add(new SelectItem("Transporte", "Transporte"));
        categories.add(new SelectItem("Vivienda", "Vivienda"));
        categories.add(new SelectItem("Entretenimiento", "Entretenimiento"));
        categories.add(new SelectItem("Servicios", "Servicios (Luz, Agua, Internet)"));
        categories.add(new SelectItem("Educación", "Educación"));
        categories.add(new SelectItem("Salud", "Salud"));
        categories.add(new SelectItem("Ropa", "Ropa"));
        categories.add(new SelectItem("Deudas", "Pago de Deudas"));
        categories.add(new SelectItem("Otros Gastos", "Otros Gastos"));
    }

    public void registerMovement() {
        FacesContext context = FacesContext.getCurrentInstance();
        boolean hasIncome = incomeAmount != null && incomeAmount > 0;
        boolean hasExpense = expenseAmount != null && expenseAmount > 0;

        if (!hasIncome && !hasExpense) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error de Registro", "Debe ingresar un monto para Ingreso o para Gasto."));
            return;
        }

        if (hasIncome) {
            if (incomeDate == null) {
                context.addMessage("incomeDate", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La fecha del ingreso es obligatoria."));
            }
            if (incomeCategory == null || incomeCategory.isEmpty()) {
                context.addMessage("incomeCategory", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La categoría del ingreso es obligatoria."));
            }
            if (incomeAmount <= 0) {
                 context.addMessage("incomeAmount", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El monto del ingreso debe ser mayor a cero."));
            }
            if (!context.getMessageList().isEmpty()) return; // Detener si hay errores de validación de ingreso

            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Ingreso Registrado:",
                    "Monto: $" + incomeAmount + ", Descripción: " + incomeDescription +
                    ", Fecha: " + incomeDate + ", Categoría: " + incomeCategory));
        }

        if (hasExpense) {
            if (expenseDate == null) {
                context.addMessage("expenseDate", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La fecha del gasto es obligatoria."));
            }
            if (expenseCategory == null || expenseCategory.isEmpty()) {
                context.addMessage("expenseCategory", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La categoría del gasto es obligatoria."));
            }
            if (expenseAmount <= 0) {
                 context.addMessage("expenseAmount", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El monto del gasto debe ser mayor a cero."));
            }
            if (!context.getMessageList().isEmpty()) return; // Detener si hay errores de validación de gasto

            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Gasto Registrado:",
                    "Monto: $" + expenseAmount + ", Descripción: " + expenseDescription +
                    ", Fecha: " + expenseDate + ", Categoría: " + expenseCategory));
        }

        // Limpiar campos después del registro exitoso si no hay errores pendientes
        if (context.getMessageList().isEmpty()) {
            incomeAmount = null;
            incomeDescription = null;
            incomeDate = null;
            incomeCategory = "";

            expenseAmount = null;
            expenseDescription = null;
            expenseDate = null;
            expenseCategory = "";
        }
    }

    // --- Getters y Setters para todas las propiedades ---
    public Double getIncomeAmount() { return incomeAmount; }
    public void setIncomeAmount(Double incomeAmount) { this.incomeAmount = incomeAmount; }

    public String getIncomeDescription() { return incomeDescription; }
    public void setIncomeDescription(String incomeDescription) { this.incomeDescription = incomeDescription; }

    public Date getIncomeDate() { return incomeDate; }
    public void setIncomeDate(Date incomeDate) { this.incomeDate = incomeDate; }

    public String getIncomeCategory() { return incomeCategory; }
    public void setIncomeCategory(String incomeCategory) { this.incomeCategory = incomeCategory; }

    public Double getExpenseAmount() { return expenseAmount; }
    public void setExpenseAmount(Double expenseAmount) { this.expenseAmount = expenseAmount; }

    public String getExpenseDescription() { return expenseDescription; }
    public void setExpenseDescription(String expenseDescription) { this.expenseDescription = expenseDescription; }

    public Date getExpenseDate() { return expenseDate; }
    public void setExpenseDate(Date expenseDate) { this.expenseDate = expenseDate; }

    public String getExpenseCategory() { return expenseCategory; }
    public void setExpenseCategory(String expenseCategory) { this.expenseCategory = expenseCategory; }

    public List<SelectItem> getCategories() { return categories; }
    public void setCategories(List<SelectItem> categories) { this.categories = categories; }
}