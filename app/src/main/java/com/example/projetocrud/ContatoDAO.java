package com.example.projetocrud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ContatoDAO {

    private Conexao conexao;
    private SQLiteDatabase banco;

    public ContatoDAO(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    public long inserir (Contato contato){

        ContentValues values = new ContentValues();

        values.put("nome", contato.getNome());
        values.put("telefone", contato.getTelefone());
        values.put("email", contato.getEmail());
        return banco.insert("contatos", null, values);
    }

    public void excluir(Contato contato){
        banco.delete("contatos", "id = ?", new String[]{contato.getId().toString()});
    }

    public void atualizar(Contato contato){

        ContentValues values = new ContentValues();

        values.put("nome", contato.getNome());
        values.put("telefone", contato.getTelefone());
        values.put("email", contato.getEmail());
        banco.update("contatos", values,"id=?", new String[]{contato.getId().toString()});
    }


    public List<Contato> obterTodos(){
        List<Contato> contatos = new ArrayList<>();
        Cursor cursor = banco.query("contatos", new String[]{"id","nome","telefone","email"},
        null, null,null,null, null);
        while (cursor.moveToNext()){
            Contato c = new Contato();
            c.setId(cursor.getInt(0));
            c.setNome(cursor.getString(1));
            c.setTelefone(cursor.getString(2));
            c.setEmail(cursor.getString(3));
            contatos.add(c);
        }
        return contatos;
    }



}
