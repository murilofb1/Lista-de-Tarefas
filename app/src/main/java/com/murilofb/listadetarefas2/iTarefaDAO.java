package com.murilofb.listadetarefas2;

import java.util.List;

public interface iTarefaDAO {
    public boolean salvar(Tarefas tarefas);

    public boolean atualizar(Tarefas tarefas);

    public boolean deletar(Tarefas tarefas);

    public List<Tarefas> listar();

}
