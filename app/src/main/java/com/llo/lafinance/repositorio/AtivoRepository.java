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
import java.util.Objects;

public class AtivoRepository {

    public static final String ID = "id";
    public static final String TABLE_ATIVO = "ativo";
    public static final String NOME = "nome";
    public static final String DESCRICAO = "descricao";
    public static final String DATA_CRIACAO = "dataCriacao";
    public static final String DATA_ATUALIZACAO = "dataAtualizacao";
    private final SQLiteDatabase banco;

    public AtivoRepository(Context context) {
        Conexao conexao = new Conexao(context);
        this.banco = conexao.getWritableDatabase();
    }

    public void inserir(Ativo ativo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOME, ativo.getNome().toUpperCase());
        contentValues.put(DESCRICAO, ativo.getDescricao().toUpperCase());
        contentValues.put(DATA_CRIACAO, ativo.getDataCriacao().toString());
        this.banco.insert(TABLE_ATIVO, null, contentValues);
    }

    public void atualizar(Ativo ativo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOME, ativo.getNome().toUpperCase());
        contentValues.put(DESCRICAO, ativo.getDescricao().toUpperCase());
        contentValues.put(DATA_ATUALIZACAO, ativo.getDataAtualizacao().toString());
        this.banco.update(TABLE_ATIVO, contentValues, ID + " = ?", new String[]{ativo.getId().toString()});
    }

    @SuppressLint("Range")
    public Ativo consultarAtivo(String nome) {
        Cursor cursor = this.banco.query(TABLE_ATIVO, null, NOME + " LIKE " + "'" + nome + "'", null, null, null, null);

        Ativo ativo = null;
        if (cursor.moveToFirst()) {
            ativo = new Ativo();
            ativo.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID))));
            ativo.setNome(cursor.getString(cursor.getColumnIndex(NOME)));
            ativo.setDescricao(cursor.getString(cursor.getColumnIndex(DESCRICAO)));
            ativo.setDataCriacao(LocalDate.parse(cursor.getString(cursor.getColumnIndex(DATA_CRIACAO))));
        }
        cursor.close();
        return ativo;
    }

    @SuppressLint("Range")
    public Ativo consultarAtivoPorId(Integer id) {
        Cursor cursor = this.banco.query(TABLE_ATIVO, null, ID + " LIKE " + "'" + id + "'", null, null, null, null);

        Ativo ativo = null;
        if (cursor.moveToFirst()) {
            ativo = new Ativo();
            ativo.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID))));
            ativo.setNome(cursor.getString(cursor.getColumnIndex(NOME)));
            ativo.setDescricao(cursor.getString(cursor.getColumnIndex(DESCRICAO)));
            ativo.setDataCriacao(LocalDate.parse(cursor.getString(cursor.getColumnIndex(DATA_CRIACAO))));
        }
        cursor.close();
        return ativo;
    }

    @SuppressLint("Range")
    public ArrayList<Ativo> consultarAtivos() {
        Cursor cursor = this.banco.query(TABLE_ATIVO, null, null, null, null, null, null);

        ArrayList<Ativo> ativos = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Ativo ativo = new Ativo();
                ativo.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID))));
                ativo.setNome(cursor.getString(cursor.getColumnIndex(NOME)));
                ativo.setDescricao(cursor.getString(cursor.getColumnIndex(DESCRICAO)));

                if (Objects.nonNull(cursor.getString(cursor.getColumnIndex(DATA_ATUALIZACAO))))
                    ativo.setDataAtualizacao(LocalDate.parse(cursor.getString(cursor.getColumnIndex(DATA_ATUALIZACAO))));

                ativo.setDataCriacao(LocalDate.parse(cursor.getString(cursor.getColumnIndex(DATA_CRIACAO))));
                ativos.add(ativo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return ativos;
    }

    public void deletar(Integer ativo) {
        this.banco.delete(TABLE_ATIVO, ID + "=" + ativo, null);
    }
}