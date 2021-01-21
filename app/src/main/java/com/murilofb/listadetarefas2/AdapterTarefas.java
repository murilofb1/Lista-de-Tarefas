package com.murilofb.listadetarefas2;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdapterTarefas extends RecyclerView.Adapter<AdapterTarefas.MyViewHolder> {
    private List<Tarefas> tarefas;

    public AdapterTarefas(List<Tarefas> t) {
        this.tarefas = t;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_tarefa_adapter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Tarefas tarefa = tarefas.get(position);
        holder.nomeItem.setText(tarefa.getNomeTarefa());
    }

    @Override
    public int getItemCount() {
        return this.tarefas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nomeItem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeItem = itemView.findViewById(R.id.text_tarefa);
        }
    }
}
