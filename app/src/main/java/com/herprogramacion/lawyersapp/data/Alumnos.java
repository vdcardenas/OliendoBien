package com.herprogramacion.lawyersapp.data;

import android.content.ContentValues;
import android.database.Cursor;
import com.herprogramacion.lawyersapp.data.Database.AlumnoEntry;

import java.util.UUID;

/**
 * Created by neuhaus on 05/12/2016.
 */

public class Alumnos {

    private static String dni;
    private static String nombre;
    private static String telefono;
    private static String curso;
    private static String email;
    private static String foto;

    public Alumnos(String dni, String nombre, String telefono, String curso, String email, String foto) {
        this.dni = dni;
        this.nombre = nombre;
        this.telefono = telefono;
        this.curso = curso;
        this.email = email;
        this.foto = foto;
    }

    public Alumnos(Cursor cursor) {
        dni = cursor.getString(cursor.getColumnIndex(AlumnoEntry.dni));
        nombre = cursor.getString(cursor.getColumnIndex(AlumnoEntry.nombre));
        telefono = cursor.getString(cursor.getColumnIndex(AlumnoEntry.telefono));
        curso = cursor.getString(cursor.getColumnIndex(AlumnoEntry.curso));
        email= cursor.getString(cursor.getColumnIndex(AlumnoEntry.email));
        foto = cursor.getString(cursor.getColumnIndex(AlumnoEntry.foto));
    }

    public static ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(AlumnoEntry.dni, dni);
        values.put(AlumnoEntry.nombre, nombre);
        values.put(AlumnoEntry.telefono, telefono);
        values.put(AlumnoEntry.curso, curso);
        values.put(AlumnoEntry.email, email);
        values.put(AlumnoEntry.foto, foto);
        return values;
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCurso() {
        return curso;
    }

    public String getEmail() {
        return email;
    }

    public String getFoto() {
        return foto;
    }
}
