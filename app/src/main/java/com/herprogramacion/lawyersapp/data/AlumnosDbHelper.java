package com.herprogramacion.lawyersapp.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.herprogramacion.lawyersapp.data.Database.AlumnoEntry;

/**
 * Manejador de la base de datos
 */
public class AlumnosDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Alumnos.db";

    public AlumnosDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + AlumnoEntry.TABLE_NAME + " ("
                + AlumnoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + AlumnoEntry.dni + " TEXT NOT NULL,"
                + AlumnoEntry.nombre + " TEXT NOT NULL,"
                + AlumnoEntry.telefono + " TEXT NOT NULL,"
                + AlumnoEntry.curso + " TEXT NOT NULL,"
                + AlumnoEntry.email + " TEXT NOT NULL,"
                + AlumnoEntry.foto + " TEXT,"
                + "UNIQUE (" + AlumnoEntry.dni + "))");



        // Insertar datos ficticios para prueba inicial
        mockData(db);

    }

    private void mockData(SQLiteDatabase sqLiteDatabase) {
     /*   mockAlumnos(sqLiteDatabase, new Alumnos("Carlos Perez", "Abogado penalista",
                "300 200 1111", "Gran profesional con experiencia de 5 años en casos penales.",
                "carlos_perez.jpg"));
        mockAlumnos(sqLiteDatabase, new Alumnos("Daniel Samper", "Abogado accidentes de tráfico",
                "300 200 2222", "Gran profesional con experiencia de 5 años en accidentes de tráfico.",
                "daniel_samper.jpg"));
        mockAlumnos(sqLiteDatabase, new Alumnos("Lucia Aristizabal", "Abogado de derechos laborales",
                "300 200 3333", "Gran profesional con más de 3 años de experiencia en defensa de los trabajadores.",
                "lucia_aristizabal.jpg"));
        mockAlumnos(sqLiteDatabase, new Alumnos("Marina Acosta", "Abogado de familia",
                "300 200 4444", "Gran profesional con experiencia de 5 años en casos de familia.",
                "marina_acosta.jpg"));
        mockAlumnos(sqLiteDatabase, new Alumnos("Olga Ortiz", "Abogado de administración pública",
                "300 200 5555", "Gran profesional con experiencia de 5 años en casos en expedientes de urbanismo.",
                "olga_ortiz.jpg"));
        mockAlumnos(sqLiteDatabase, new Alumnos("Pamela Briger", "Abogado fiscalista",
                "300 200 6666", "Gran profesional con experiencia de 5 años en casos de derecho financiero",
                "pamela_briger.jpg"));
        mockAlumnos(sqLiteDatabase, new Alumnos("Rodrigo Benavidez", "Abogado Mercantilista",
                "300 200 1111", "Gran profesional con experiencia de 5 años en redacción de contratos mercantiles",
                "rodrigo_benavidez.jpg"));
        mockAlumnos(sqLiteDatabase, new Alumnos("Tom Bonz", "Abogado penalista",
                "300 200 1111", "Gran profesional con experiencia de 5 años en casos penales.",
                "tom_bonz.jpg"));*/
    }

    public long mockAlumnos(SQLiteDatabase db, Alumnos alumnos) {
        return db.insert(
                AlumnoEntry.TABLE_NAME,
                null,
                Alumnos.toContentValues());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No hay operaciones
    }

    public long saveAlumnos(Alumnos alumno) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                AlumnoEntry.TABLE_NAME,
                null,
                Alumnos.toContentValues());

    }

    public Cursor getAllAlumnos() {
        return getReadableDatabase()
                .query(
                        AlumnoEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    public Cursor getAlumnosById(String alumnoId) {
        Cursor c = getReadableDatabase().query(
                AlumnoEntry.TABLE_NAME,
                null,
                AlumnoEntry.dni + " LIKE ?",
                new String[]{alumnoId},
                null,
                null,
                null);
        return c;
    }

    public int deleteAlumnos(String alumnoId) {
        return getWritableDatabase().delete(
                AlumnoEntry.TABLE_NAME,
                AlumnoEntry.dni + " LIKE ?",
                new String[]{alumnoId});
    }

    public int updateAlumnos(Alumnos alumnos, String alumnoId) {
        return getWritableDatabase().update(
                AlumnoEntry.TABLE_NAME,
                Alumnos.toContentValues(),
                AlumnoEntry.dni + " LIKE ?",
                new String[]{alumnoId}
        );
    }
}
