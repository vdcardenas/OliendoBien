package com.herprogramacion.lawyersapp.addeditlawyer;


import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.herprogramacion.lawyersapp.R;
import com.herprogramacion.lawyersapp.data.Alumnos;
import com.herprogramacion.lawyersapp.data.AlumnosDbHelper;

/**
 * Vista para creación/edición de un alumno :-)
 */
public class AddEditLawyerFragment extends Fragment {
    private static final String ARG_LAWYER_ID = "arg_lawyer_id";

    private String mLawyerId;

    private AlumnosDbHelper mLawyersDbHelper;

    private FloatingActionButton mSaveButton;

    private TextInputEditText mDniField;
    private TextInputEditText mNombreField;
    private TextInputEditText mTelefonoField;
    private TextInputEditText mCursoField;
    private TextInputEditText mEmailField;

    private TextInputLayout mDniLabel;
    private TextInputLayout mNombreLabel;
    private TextInputLayout mTelefonoLabel;
    private TextInputLayout mCursoLabel;
    private TextInputLayout mEmailLabel;


    public AddEditLawyerFragment() {
        // Required empty public constructor
    }

    public static AddEditLawyerFragment newInstance(String lawyerId) {
        AddEditLawyerFragment fragment = new AddEditLawyerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_LAWYER_ID, lawyerId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLawyerId = getArguments().getString(ARG_LAWYER_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.faragment_add_edit_alumno, container, false);

        // Referencias UI
        mSaveButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        mDniField = (TextInputEditText) root.findViewById(R.id.et_dni);
        mNombreField = (TextInputEditText) root.findViewById(R.id.et_nombre);
        mTelefonoField = (TextInputEditText) root.findViewById(R.id.et_telefono);
        mCursoField = (TextInputEditText) root.findViewById(R.id.et_curso);
        mEmailField = (TextInputEditText) root.findViewById(R.id.et_email);

        mDniLabel =  (TextInputLayout) root.findViewById(R.id.til_dni);
        mNombreLabel = (TextInputLayout) root.findViewById(R.id.til_nombre);
        mTelefonoLabel = (TextInputLayout) root.findViewById(R.id.til_telefono);
        mCursoLabel = (TextInputLayout) root.findViewById(R.id.til_curso);
        mEmailLabel = (TextInputLayout) root.findViewById(R.id.til_email);

        // Eventos
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditLawyer();
            }
        });

        mLawyersDbHelper = new AlumnosDbHelper(getActivity());

        // Carga de datos
        if (mLawyerId != null) {
            loadLawyer();
        }

        return root;
    }

    private void loadLawyer() {
        new GetLawyerByIdTask().execute();
    }

    private void addEditLawyer() {
        boolean error = false;

        String dni =  mDniField.getText().toString();
        String nombre = mNombreField.getText().toString();
        String telefono = mTelefonoField.getText().toString();
        String curso = mCursoField.getText().toString();
        String email = mEmailField.getText().toString();

        if (TextUtils.isEmpty(dni)) {
            mDniLabel.setError("Ingresa un valor");
            error = true;
        }

        if (TextUtils.isEmpty(nombre)) {
            mNombreLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(telefono)) {
            mTelefonoLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(curso)) {
            mCursoLabel.setError(getString(R.string.field_error));
            error = true;
        }


        if (TextUtils.isEmpty(email)) {
            mEmailLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (error) {
            return;
        }

        Alumnos alumnos = new Alumnos(dni,nombre,telefono,curso,email, "");

        new AddEditLawyerTask().execute(alumnos);

    }

    private void showLawyersScreen(Boolean requery) {
        if (!requery) {
            showAddEditError();
            getActivity().setResult(Activity.RESULT_CANCELED);
        } else {
            getActivity().setResult(Activity.RESULT_OK);
        }

        getActivity().finish();
    }

    private void showAddEditError() {
        Toast.makeText(getActivity(),
                "Error al agregar nueva información", Toast.LENGTH_SHORT).show();
    }
//añadur metodos restantes
    private void showLawyer(Alumnos alumno) {
        mDniField.setText(alumno.getDni());
        mNombreField.setText(alumno.getNombre());
        mTelefonoField.setText(alumno.getTelefono());
        mCursoField.setText(alumno.getCurso());
        mEmailField.setText(alumno.getEmail()
        );
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al editar el alumno", Toast.LENGTH_SHORT).show();
    }

    private class GetLawyerByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mLawyersDbHelper.getAlumnosById(mLawyerId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showLawyer(new Alumnos(cursor));
            } else {
                showLoadError();
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        }

    }

    private class AddEditLawyerTask extends AsyncTask<Alumnos, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Alumnos... lawyers) {
            if (mLawyerId != null) {
                return mLawyersDbHelper.updateAlumnos(lawyers[0], mLawyerId) > 0;

            } else {
                return mLawyersDbHelper.saveAlumnos(lawyers[0]) > 0;
            }

        }

        @Override
        protected void onPostExecute(Boolean result) {
            showLawyersScreen(result);
        }

    }

}
