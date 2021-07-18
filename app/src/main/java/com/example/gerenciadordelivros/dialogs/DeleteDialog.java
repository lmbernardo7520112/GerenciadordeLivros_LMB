package com.example.gerenciadordelivros.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.gerenciadordelivros.dominios.Livro;

public class DeleteDialog extends DialogFragment implements DialogInterface.OnClickListener{

    private Livro livro;
    private onDeleteListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(!(context instanceof  onDeleteListener)){
            throw new RuntimeException("Não é um onDeleteListener");
        }

        this.listener= (onDeleteListener) context;
    }

    @NonNull
    @Override
    public  Dialog onCreateDialog(@Nullable Bundle savedInstanceState){


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Deseja excluir o livro "+livro.getTitulo());
        builder.setPositiveButton("SIM", this);
        builder.setNegativeButton("NÃO", this);


        return builder.create();
    }

    public void setLivro (Livro livro){
        this.livro=livro;
    }

    @Override
    public void onClick(DialogInterface dialog, int i) {
        if(i == DialogInterface.BUTTON_POSITIVE);
        listener.onDelete(livro);
    }

    public interface  onDeleteListener{
        void  onDelete(Livro livro);
    }
}
