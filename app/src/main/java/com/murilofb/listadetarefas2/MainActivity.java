package com.murilofb.listadetarefas2;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdapterTarefas tarefas;
    private List<Tarefas> tarefasList = new ArrayList<>();
    private Toast tst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tst = new Toast(getApplicationContext());
        recyclerView = findViewById(R.id.recycler_lista_tarefas);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // RECUPERAR TAREFA PARA A EDIÇÃO
                Tarefas tarefaSelecionada = tarefasList.get(position);
                Intent i = new Intent(MainActivity.this, AdicionarTarefa.class);
                i.putExtra("tarefaSelecionada", tarefaSelecionada);
                startActivity(i);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                final Tarefas t = tarefasList.get(position);

                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Confirmar Exclusão");
                dialog.setMessage("Realmente deseja excluir a mensagem: " + t.getNomeTarefa() + "?");
                dialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TarefaDAO tarefaDAO = new TarefaDAO(MainActivity.this);
                        tarefaDAO.deletar(t);
                        Toast.makeText(MainActivity.this, "Anotação deletada com sucesso", Toast.LENGTH_SHORT).show();
                        carregarTarefas();
                    }
                });
                dialog.setNegativeButton("Negar", null);
                dialog.create();
                dialog.show();


            }

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        }
        ));


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AdicionarTarefa.class);
                startActivity(i);

            }
        });
    }

    @Override
    protected void onStart() {
        carregarTarefas();
        super.onStart();
    }

    public void carregarTarefas() {
        //Listar Tarefas
        TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
        tarefasList = tarefaDAO.listar();
        SQLiteDatabase tarefasDB = openOrCreateDatabase("appDB", 0, null);

        //Configurando Adapter
        tarefas = new AdapterTarefas(tarefasList);


        // Configurando RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(tarefas);
    }


}