package com.example.gerenciadordelivros.data;

public class LivroContract {

    public static final String TABLE_NAME = "Livro";

    public  static  final class Columns{
        public static final String _ID = "_id";
        public static final String titulo = "_titulo";
        public static final String autor = "autor";
        public static final String editora = "editora";
        public static final String emprestado = "emprestado";
    }
}
