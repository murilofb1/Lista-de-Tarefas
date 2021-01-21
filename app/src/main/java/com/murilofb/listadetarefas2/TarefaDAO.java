package com.murilofb.listadetarefas2;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TarefaDAO implements iTarefaDAO {
    DBHelper dbHelper;
    private SQLiteDatabase write;
    private SQLiteDatabase read;


    public TarefaDAO(Context context) {
        dbHelper = new DBHelper(context);
        write = dbHelper.getWritableDatabase();
        read = dbHelper.getReadableDatabase();
    }

    @Override
    public boolean salvar(Tarefas tarefas) {
        ContentValues cv = new ContentValues();
        cv.put("nome", tarefas.getNomeTarefa());
        try {
            write.insert(dbHelper.TABELA_TAREFAS, null, cv);

        } catch (Exception e) {
            Toast.makeText(new AdicionarTarefa().getApplicationContext(), "Erro", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public boolean atualizar(Tarefas tarefas) {
        ContentValues cv = new ContentValues();
        cv.put("nome", tarefas.getNomeTarefa());
        String[] args = {tarefas.getId().toString()};
        write.update(dbHelper.TABELA_TAREFAS, cv, "id=?", args);
        return true;
    }

    @Override
    public boolean deletar(Tarefas tarefas) {
        String[] args = {tarefas.getId().toString()};
        write.delete(dbHelper.TABELA_TAREFAS, "id=?", args);
        return true;
    }

    @Override
    public List<Tarefas> listar() {
        List<Tarefas> tarefas = new ArrayList<>();
        String sql = "SELECT * FROM " + dbHelper.TABELA_TAREFAS + " ;";
        Cursor cursor = read.rawQuery(sql, null);
        cursor.moveToFirst();

        while (cursor.moveToNext()) {
            Tarefas tarefa = new Tarefas();
            Long id = cursor.getLong(cursor.getColumnIndex("id"));
            String nomeTarefa = cursor.getString(cursor.getColumnIndex("nome"));
            tarefa.setId(id);
            tarefa.setNomeTarefa(nomeTarefa);
            tarefas.add(tarefa);
        }
        return tarefas;
    }
}
