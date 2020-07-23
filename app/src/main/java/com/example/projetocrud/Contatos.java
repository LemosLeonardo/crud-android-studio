package com.example.projetocrud;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class Contatos extends AppCompatActivity {

    private ListView listView;
    private ContatoDAO dao;
    private List<Contato> contatos;
    private List<Contato> contatosFiltrados = new ArrayList<>();
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contatos);
        listView = findViewById(R.id.ListViewContatos);
        dao = new ContatoDAO(this);
        contatos = dao.obterTodos();
        contatosFiltrados.addAll(contatos);
//        ArrayAdapter<Contato> adaptador = new ArrayAdapter<Contato>(this, android.R.layout.simple_list_item_1, contatosFiltrados);
        ContatoAdapter adaptador = new ContatoAdapter(this, contatosFiltrados);
        listView.setAdapter(adaptador);
        searchView = findViewById(R.id.searchViewPesquisar);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                procurarContato(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                procurarContato(s);
                return false;
            }
        });
        registerForContextMenu(listView);
    }


    public void excluir (MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Contato contatoExcluir = contatosFiltrados.get(menuInfo.position);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.txtAtencao)
                .setMessage(R.string.txtAdvertenciaExcluirContato)
                .setNegativeButton("NÃO", null)
                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        contatosFiltrados.remove(contatoExcluir);
                        dao.excluir(contatoExcluir);
                        listView.invalidateViews();
                    }
                }).create();
        dialog.show();
    }

    public void atualizar(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Contato contatoAtualizar = contatosFiltrados.get(menuInfo.position);
        Intent it = new Intent(this, CadastrarContatos.class);
        it.putExtra("contato", contatoAtualizar);
        startActivity(it);


    }

    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, view, menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_contexto, menu);
    }

    public void procurarContato(String nome){
        contatosFiltrados.clear();
        for(Contato contato : contatos){
            if (contato.getNome().toLowerCase().contains(nome.toLowerCase())){
                contatosFiltrados.add(contato);
            }
        }
        listView.invalidateViews();
    }

    @Override
    public void onResume(){
        super.onResume();
        contatos = dao.obterTodos();
        contatosFiltrados.clear();
        contatosFiltrados.addAll(contatos);
        listView.invalidateViews();
    }


    public void cadastrar(View view){
        Intent intent = new Intent(this, CadastrarContatos.class);
        startActivity(intent);
    }


    public void voltar(View view){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}