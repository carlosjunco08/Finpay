/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;
import javax.ejb.Local;

@Local
public interface GeminiServiceLocal {
    String consultarIA(String pregunta, String contexto);
}