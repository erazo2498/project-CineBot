package com.edu.cinebot.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Usuario {
    private String nombreUsuario;
    private String contrasena;
    private String nombre;
    private String apellido;
    private Date fechaNacimiento;
    private String identificacion;
    private String tipoUsuario;
}
