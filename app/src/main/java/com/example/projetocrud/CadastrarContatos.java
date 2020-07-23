package com.example.projetocrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CadastrarContatos extends AppCompatActivity {
    private EditText nome;
    private EditText telefone;
    private EditText email;
    private ContatoDAO dao;
    private Contato contato = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_contatos);
        nome = (EditText) findViewById(R.id.editTextNome);
        telefone = (EditText) findViewById(R.id.editTextTelefone);
        email = (EditText) findViewById(R.id.editTextEmail);
        dao = new ContatoDAO(this);

        Intent it = getIntent();

        if(it.hasExtra("contato")){
            contato = (Contato) it.getSerializableExtra("contato");
            nome.setText(contato.getNome());
            telefone.setText(contato.getTelefone());
            email.setText(contato.getEmail());
        }
    }

    public void cadastrar(View view){
            if (contato == null){

                Contato contato = new Contato();

                contato.setNome(nome.getText().toString());
                contato.setTelefone(telefone.getText().toString());
                contato.setEmail(email.getText().toString());
                long id = dao.inserir(contato);
                Toast.makeText(this, getString(R.string.txtContatoCadastradoSucesso) + id, Toast.LENGTH_SHORT).show();
        }else{
                contato.setNome(nome.getText().toString());
                contato.setTelefone(telefone.getText().toString());
                contato.setEmail(email.getText().toString());
                dao.atualizar(contato);
                Toast.makeText(this, R.string.txtContatoAtualizado, Toast.LENGTH_SHORT).show();
            }
    }


    public void voltar(View view){
        Intent intent = new Intent(this, Contatos.class);
        startActivity(intent);
    }

}