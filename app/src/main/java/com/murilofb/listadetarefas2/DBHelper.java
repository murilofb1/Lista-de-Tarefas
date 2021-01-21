package com.murilofb.listadetarefas2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static int VERSION = 1;
    public static String NOME_DB = "DB_TAREFAS";
    public static String TABELA_TAREFAS = "tarefas";

    // AQUI A GENTE PASSA A VERSÃO, NOME DA DATABASE E O CONTEXTO (POR ESSE MOTIVO SEMPRE QUE PRECISARMOS EXECUTAR MÉTODOS QUE ACESSAM ESSA CLASSE ELES DEVEM TAMBÉM RECUPERAR O CONTEXTO )
    public DBHelper(@Nullable Context context) {
        super(context, NOME_DB, null, VERSION);
    }

    // ESSE onCreate EXECUTA APENAS UMA VEZ ASSIM QUE VOCE INSTALA O APP, A CADA ATUALIZAÇÃO (PARA ISSO BASTA MUDAR O NÚMERO DA VERSÃO) É EXECUTADO O MÉTODO onUpgrade
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "CREATE TABLE IF NOT EXISTS " + TABELA_TAREFAS + " (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT NOT NULL)";

        try {
            sqLiteDatabase.execSQL(sql);
            Log.i("INFO DB", "tabela criada com sucesso");
        } catch (Exception e) {
            Log.i("INFO DB", "nao foi possivel criar a tabela");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
