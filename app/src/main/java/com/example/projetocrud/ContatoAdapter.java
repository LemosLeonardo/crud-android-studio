package com.example.projetocrud;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.BaseAdapter;
import org.w3c.dom.Text;

import java.util.List;

public class ContatoAdapter extends BaseAdapter {
    private List<Contato> contatos;
    private Activity activity;

    public ContatoAdapter(Activity activity, List<Contato> contatos)  {
        this.activity = activity;
        this.contatos = contatos;
    }

    @Override
    public int getCount(){
        return contatos.size();
    }

    @Override
    public Object getItem(int i){
        return contatos.get(i);
    }

    @Override
    public long getItemId(int i){
        return contatos.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup){

        View v = activity.getLayoutInflater().inflate(R.layout.item, viewGroup, false);

        TextView nome = v.findViewById(R.id.text_nome);
        TextView telefone = v.findViewById(R.id.text_telefone);
        TextView email = v.findViewById(R.id.text_email);

        Contato contato = contatos.get(i);
        nome.setText(contato.getNome());
        telefone.setText(contato.getTelefone());
        email.setText(contato.getEmail());

        return v;
    }
}