package com.example.gerenciadordelivros.views;


import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gerenciadordelivros.R;
import com.example.gerenciadordelivros.data.LivroDAO;
import com.example.gerenciadordelivros.dominios.Livro;

public class EditarLivroActivity extends AppCompatActivity {

    private EditText edt_titulo;
    private EditText edt_autor;
    private EditText edt_editora;
    private CheckBox chk_emprestimo;

    private LivroDAO livroDAO;

    private Livro livro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_livro);

        edt_titulo = findViewById(R.id.edt_titulo);
        edt_autor = findViewById(R.id.edt_autor);
        edt_editora = findViewById(R.id.edt_editora);
        chk_emprestimo = findViewById(R.id.check_emprestado);

        livroDAO = LivroDAO.getInstance(this);

        livro = (Livro) getIntent().getSerializableExtra("livro");

        if(livro != null){
            edt_titulo.setText(livro.getTitulo());
            edt_autor.setText(livro.getAutor());
            edt_editora.setText(livro.getEditora());
            chk_emprestimo.setChecked((livro.getEmprestado() == 1) ? true : false);
        }
    }

    public void cancelar(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void processar(View view) {
        String titulo = edt_titulo.getText().toString();
        String autor = edt_autor.getText().toString();
        String editora = edt_editora.getText().toString();
        int emprestimo = (chk_emprestimo.isChecked()) ? 1 : 0;

        String msg;

        if (livro == null){
            Livro livro = new Livro(titulo,autor,editora,emprestimo);
            livroDAO.save(livro);
            msg = "Livro adicionado com sucesso! ID=" + livro.getId();
        }else {
            livro.setTitulo(titulo);
            livro.setAutor(autor);
            livro.setEditora(editora);
            livro.setEmprestado(emprestimo);

            livroDAO.update(livro);
            msg = "Livro atualizado com sucesso! ID=" + livro.getId();
        }


        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }


}