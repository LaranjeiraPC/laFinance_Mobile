package com.llo.lafinance.repositorio;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.llo.lafinance.config.Conexao;
import com.llo.lafinance.model.Compra;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

public class CompraRepository {

    public static final String ID = "id";
    public static final String TABLE_COMPRA = "compra";
    public static final String DATA_CRIACAO = "dataCriacao";

    public static final String DATA_ATUALIZACAO = "dataAtualizacao";
    public static final String PRECO_TOTAL = "precoTotal";
    public static final String PRECO_UNITARIO = "precoUnitario";
    public static final String QUANTIDADE = "quantidade";
    public static final String STATUS = "status";
    public static final String ATIVO = "ativo";
    private Conexao conexao;
    private SQLiteDatabase banco;

    private static final String[] LINHAS = {ID, ATIVO, STATUS, QUANTIDADE, PRECO_UNITARIO, PRECO_TOTAL, DATA_CRIACAO, DATA_ATUALIZACAO};

    public CompraRepository(Context context) {
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    public long inserir(Compra compra) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ATIVO, compra.getAtivo().toUpperCase());
        contentValues.put(STATUS, "S");
        contentValues.put(QUANTIDADE, compra.getQuantidade());
        contentValues.put(PRECO_UNITARIO, compra.getPrecoUnitario().toString());
        contentValues.put(PRECO_TOTAL, compra.getPrecoTotal().toString());
        contentValues.put(DATA_CRIACAO, compra.getDataCriacao().toString());
        return banco.insert(TABLE_COMPRA, null, contentValues);
    }

    public long atualizar(Compra compra) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(QUANTIDADE, compra.getQuantidade());
        contentValues.put(PRECO_UNITARIO, compra.getPrecoUnitario().toString());
        contentValues.put(PRECO_TOTAL, compra.getPrecoTotal().toString());
        contentValues.put(DATA_ATUALIZACAO, compra.getDataAtualizacao().toString());
        return banco.update(TABLE_COMPRA, contentValues, ID + " = ?", new String[]{compra.getId().toString()});
    }

    @SuppressLint("Range")
    public ArrayList<Compra> consultarComprasAtivas() {
        Cursor cursor = banco.query(TABLE_COMPRA, null, null, null, null, null, null);

        ArrayList<Compra> compras = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Compra compra = new Compra();
                compra.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID))));
                compra.setAtivo(cursor.getString(cursor.getColumnIndex(ATIVO)));
                compra.setQuantidade(Integer.parseInt(cursor.getString(cursor.getColumnIndex(QUANTIDADE))));
                compra.setPrecoUnitario(new BigDecimal(cursor.getString(cursor.getColumnIndex(PRECO_UNITARIO))));
                compra.setPrecoTotal(new BigDecimal(cursor.getString(cursor.getColumnIndex(PRECO_TOTAL))));
                compra.setStatus(cursor.getString(cursor.getColumnIndex(STATUS)));
                compra.setDataCriacao(LocalDate.parse(cursor.getString(cursor.getColumnIndex(DATA_CRIACAO))));
                compras.add(compra);
            } while (cursor.moveToNext());
        }
        return compras;
    }

    @SuppressLint("Range")
    public Boolean consultarComprasPorNomeAtivo(String nomeAtivo) {
        Cursor cursor = banco.query(TABLE_COMPRA, null, ATIVO + " LIKE " + "'" + nomeAtivo + "'", null, null, null, null);

        ArrayList<Compra> compras = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Compra compra = new Compra();
                compra.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID))));
                compras.add(compra);
            } while (cursor.moveToNext());
        }
        return compras.size() > 0;
    }

    @SuppressLint("Range")
    public Compra consultarCompraPorId(Integer id) {
        Cursor cursor = banco.query(TABLE_COMPRA, null, ID + "=" + id, null, null, null, null);

        Compra compra = new Compra();
        if (cursor.moveToFirst()) {
            do {
                compra.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID))));
                compra.setAtivo(cursor.getString(cursor.getColumnIndex(ATIVO)));
                compra.setQuantidade(Integer.parseInt(cursor.getString(cursor.getColumnIndex(QUANTIDADE))));
                compra.setPrecoUnitario(new BigDecimal(cursor.getString(cursor.getColumnIndex(PRECO_UNITARIO))));
                compra.setPrecoTotal(new BigDecimal(cursor.getString(cursor.getColumnIndex(PRECO_TOTAL))));
                compra.setStatus(cursor.getString(cursor.getColumnIndex(STATUS)));
                compra.setDataCriacao(LocalDate.parse(cursor.getString(cursor.getColumnIndex(DATA_CRIACAO))));
            } while (cursor.moveToNext());
        }
        return compra;
    }

    public long deletar(Integer compra) {
        return banco.delete(TABLE_COMPRA, ID + "=" + compra, null);
    }
}
