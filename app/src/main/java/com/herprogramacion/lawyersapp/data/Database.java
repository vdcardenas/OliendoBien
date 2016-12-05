package com.herprogramacion.lawyersapp.data;

import android.provider.BaseColumns;

/**
 * Esquema de la base de datos para Alumnos
 */
public class Database {

    public static abstract class AlumnoEntry implements BaseColumns{
        public static final String TABLE_NAME ="alumnos";

        public static final String dni = "dni";
        public static final String nombre = "nombre";
        public static final String telefono = "telefono";
        public static final String curso = "curso";
        public static final String email = "email";
        public static final String foto="foto";
    }
}
