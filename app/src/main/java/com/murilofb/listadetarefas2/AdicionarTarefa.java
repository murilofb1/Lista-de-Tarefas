package com.murilofb.listadetarefas2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AdicionarTarefa extends AppCompatActivity {
    private EditText tarefa;
    // VARIÁVEL tarefaAtual para quando precisarmos verificar se a gente selecionou algum item já preenchido
    private Tarefas tarefaAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);
        tarefa = findViewById(R.id.edt_tarefa);
        // ELE RECEBE O ITEM QUE A GENTE PASSOU LÁ NO MÉTODO onItemClick DA RecyclerView da MainActivity
        tarefaAtual = (Tarefas) getIntent().getSerializableExtra("tarefaSelecionada");
        if (tarefaAtual != null) {
            // E A GENTE SETA O TEXTO DO EDIT TEXT COMO O VALOR DA TAREFA SELECIONADA
            tarefa.setText(tarefaAtual.getNomeTarefa());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
        String nomeTarefa = tarefa.getText().toString();
        // A GENTE SALVA A TAREFA CASO ELA NÃO ESTEHJA VAZIA
        if (tarefaAtual != null) {
            if (!nomeTarefa.isEmpty()) {
                Tarefas tarefas = new Tarefas();
                tarefas.setNomeTarefa(tarefa.getText().toString());
                // COMO A GENTE ESTÁ EDITANDO UM ITEM JÁ EXISTENTE, AGENTE PRECISA PASSAR SEU ID PARA QUE NÃO SEJA CRIADO OUTRO ITEM NA DATABASE
                tarefas.setId(tarefaAtual.getId());
                tarefaDAO.atualizar(tarefas);
            }
        } else {

            if (!nomeTarefa.isEmpty()) {
                // COMO O ID É AUTOINCREMENTADO A GENTE NÃO PRECISA PASSAR ELE PARA O OBJETO TAREFA
                Tarefas task = new Tarefas();
                task.setNomeTarefa(tarefa.getText().toString());
                tarefaDAO.salvar(task);
            }

        }
        this.finish();


        return super.onOptionsItemSelected(item);
    }
}