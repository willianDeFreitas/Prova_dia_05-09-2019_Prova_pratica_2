package com.example.prova2dispositivosmoveis;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    List<Produtos> produtosList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listaDeAlunos = findViewById(R.id.produtosCafe);
        buscaDados();
        listaDeAlunos.setOnItemSelectedListener(this);
    }

    private void buscaDados() {
        RetrofitService.getServico().obterProdutos().enqueue(new Callback<List<Produtos>>() {
            @Override
            public void onResponse(Call<List<Produtos>> call, Response<List<Produtos>> response) {
                produtosList = response.body();
                List<String> produtosNameList = new ArrayList<>();
                for (Produtos produto : produtosList) {
                    produtosNameList.add(produto.getProduto());
                }
            }

            @Override
            public void onFailure(Call<List<Produtos>> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView saida = findViewById(R.id.produto);
        Produtos produto = produtosList.get(position);
        saida.setText("Produto: " + produto.getProduto() + "\n" +
                " Descrição: " + produto.getDescricao() + "\n" +
                " Valor: " + produto.getValor());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
