package com.llo.lafinance.repositorio;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.llo.lafinance.config.Conexao;
import com.llo.lafinance.model.Ativo;

import java.time.LocalDate;
import java.util.ArrayList;

public class AtivoRepository {

    public static final String ID = "id";
    public static final String TABLE_ATIVO = "ativo";
    public static final String NOME = "nome";
    public static final String DESCRICAO = "descricao";
    public static final String DATA_CRIACAO = "dataCriacao";
    public static final String DATA_ATUALIZACAO = "dataAtualizacao";
    private Conexao conexao;
    private SQLiteDatabase banco;

    private static final String[] LINHAS = {ID, NOME, DESCRICAO, DATA_CRIACAO, DATA_ATUALIZACAO};

    public AtivoRepository(Context context) {
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    public long inserir(Ativo ativo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOME, ativo.getNome().toUpperCase());
        contentValues.put(DESCRICAO, ativo.getDescricao().toUpperCase());
        contentValues.put(DATA_CRIACAO, ativo.getDataCriacao().toString());
        return banco.insert(TABLE_ATIVO, null, contentValues);
    }

    @SuppressLint("Range")
    public ArrayList<Ativo> listarAtivos() {
        Cursor cursor = banco.query(TABLE_ATIVO, null, null, null, null, null, null);

        ArrayList<Ativo> ativos = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Ativo ativo = new Ativo();
                ativo.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID))));
                ativo.setNome(cursor.getString(cursor.getColumnIndex(NOME)));
                ativo.setDescricao(cursor.getString(cursor.getColumnIndex(DESCRICAO)));
                ativo.setDataCriacao(LocalDate.parse(cursor.getString(cursor.getColumnIndex(DATA_CRIACAO))));
                ativos.add(ativo);
            } while (cursor.moveToNext());
        }
        return ativos;
    }

    @SuppressLint("Range")
    public Ativo consultarAtivo(String nome) {
        Cursor cursor = banco.query(TABLE_ATIVO, null, NOME + " LIKE " + "'" + nome + "'", null, null, null, null);

        Ativo ativo = null;
        if (cursor.moveToFirst()) {
            ativo = new Ativo();
            ativo.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID))));
            ativo.setNome(cursor.getString(cursor.getColumnIndex(NOME)));
            ativo.setDescricao(cursor.getString(cursor.getColumnIndex(DESCRICAO)));
            ativo.setDataCriacao(LocalDate.parse(cursor.getString(cursor.getColumnIndex(DATA_CRIACAO))));
        }
        return ativo;
    }
}
