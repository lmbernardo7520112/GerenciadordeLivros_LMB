package com.example.gerenciadordelivros.views;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gerenciadordelivros.R;
import com.example.gerenciadordelivros.adapter.LivroAdapter;
import com.example.gerenciadordelivros.data.LivroDAO;
import com.example.gerenciadordelivros.dialogs.DeleteDialog;
import com.example.gerenciadordelivros.dominios.Livro;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LivroAdapter.OnLivroListener, DeleteDialog.onDeleteListener {

    private LivroDAO livroDAO;
    LivroAdapter livroAdapter;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        listaLivros.add(new Livro(1L,"Android para Leigos","Michael Burton","Alta books",0));
//        listaLivros.add(new Livro(2L,"Android para Programadores","Paul J, Deitel","Bookman",1));
//        listaLivros.add(new Livro(3L,"Desenvolvimento para Android","Griffiths, David","Alta books",0));
//        listaLivros.add(new Livro(4L,"Android Base de Dados","Queirós, Ricardo","FCA Editora",1));
//        listaLivros.add(new Livro(5L,"Android em Ação","King, Chris","Elsevier - Campus",0));
//        listaLivros.add(new Livro(6L,"Jogos em Android","Queirós, Ricardo","FCA - Editora",1));
//        listaLivros.add(new Livro(7L,"Android Essencial com Kotlin","Ricardo R.","NOVATEC",0));

        livroDAO = LivroDAO.getInstance(this);

        List<Livro> listaLivros = livroDAO.list();

        livroAdapter = new LivroAdapter(listaLivros,this, this);

        recyclerView.setAdapter(livroAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

    switch (item.getItemId()){
        case R.id.action_adicionar:
            Intent intent = new Intent(getApplicationContext(),EditarLivroActivity.class);
            startActivityForResult(intent,100);
            return true;
        case R.id.action_sair:
            finish();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK){
            atualizaListaLivros();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void atualizaListaLivros(){
        List<Livro> livros = livroDAO.list();
        livroAdapter.setItems(livros);
        livroAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLivroClick(int posicao) {
        //Toast.makeText(this, "onLivroClick ="+(posicao+1), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), EditarLivroActivity.class);
        intent.putExtra("livro", livroAdapter.getItem(posicao));
        startActivityForResult(intent, 101);
    }


    @Override
    public void onLivroLongClick(int posicao) {

        Livro livro = livroAdapter.getItem(posicao);
        DeleteDialog dialog = new DeleteDialog();
        dialog.setLivro(livro);
        dialog.show(getSupportFragmentManager(), "deleteDialod");

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onDelete(Livro livro) {

        livroDAO.delete(livro);
        atualizaListaLivros();
        Toast.makeText(this, "Livro excluído com sucesso!", Toast.LENGTH_LONG).show();

    }
}