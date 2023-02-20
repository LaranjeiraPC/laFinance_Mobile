package com.llo.lafinance.repositorio;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.llo.lafinance.config.Conexao;
import com.llo.lafinance.model.Venda;

public class VendaRepository {

    public static final String TABLE_VENDA = "venda";
    public static final String ID = "id";
    public static final String COMPRA = "compra";
    public static final String QUANTIDADE = "quantidade";
    public static final String PRECO_UNITARIO = "precoUnitario";
    public static final String PRECO_TOTAL = "precoTotal";
    public static final String DATA_CRIACAO = "dataCriacao";

    public static final String DATA_ATUALIZACAO = "dataAtualizacao";
    private Conexao conexao;
    private SQLiteDatabase banco;

    public VendaRepository(Context context) {
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    public long inserir(Venda venda) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COMPRA, venda.getCompra());
        contentValues.put(QUANTIDADE, venda.getQuantidade());
        contentValues.put(PRECO_UNITARIO, venda.getPrecoUnitario().toString());
        contentValues.put(PRECO_TOTAL, venda.getPrecoTotal().toString());
        contentValues.put(DATA_CRIACAO, venda.getDataCriacao().toString());
        return banco.insert(TABLE_VENDA, null, contentValues);
    }

    public long atualizar(Venda venda) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(QUANTIDADE, venda.getQuantidade());
        contentValues.put(PRECO_UNITARIO, venda.getPrecoUnitario().toString());
        contentValues.put(PRECO_TOTAL, venda.getPrecoTotal().toString());
        contentValues.put(DATA_ATUALIZACAO, venda.getDataAtualizacao().toString());
        return banco.update(TABLE_VENDA, contentValues, ID + " = ?", new String[]{venda.getId().toString()});
    }

    public long deletar(Integer venda) {
        return banco.delete(TABLE_VENDA, ID + "=" + venda, null);
    }
}
