package com.llo.lafinance.repositorio;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.llo.lafinance.config.Conexao;
import com.llo.lafinance.model.Configuracao;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ConfiguracaoRepository {

    public static final String ID = "id";
    public static final String TABLE_CONFIGURACAO = "configuracao";
    public static final String NOME_USUARIO = "nomeUsuario";
    public static final String META_LUCRO_LIQUIDO_MENSAL = "metaLucroLiquidoMensal";
    public static final String DATA_CRIACAO = "dataCriacao";
    public static final String DATA_ATUALIZACAO = "dataAtualizacao";
    private final SQLiteDatabase banco;

    public ConfiguracaoRepository(Context context) {
        Conexao conexao = new Conexao(context);
        this.banco = conexao.getWritableDatabase();
    }

    public void inserir(Configuracao configuracao) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOME_USUARIO, configuracao.getNomeUsuario().toUpperCase());
        contentValues.put(META_LUCRO_LIQUIDO_MENSAL, configuracao.getMetaLucroLiquidoMensal().toString());
        contentValues.put(DATA_CRIACAO, LocalDate.now().toString());
        this.banco.insert(TABLE_CONFIGURACAO, null, contentValues);
    }

    public void atualizar(Configuracao configuracao) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOME_USUARIO, configuracao.getNomeUsuario().toUpperCase());
        contentValues.put(META_LUCRO_LIQUIDO_MENSAL, configuracao.getMetaLucroLiquidoMensal().toString());
        contentValues.put(DATA_ATUALIZACAO, configuracao.getDataAtualizacao().toString());
        this.banco.update(TABLE_CONFIGURACAO, contentValues, ID + " = ?", new String[]{configuracao.getId().toString()});
    }

    @SuppressLint("Range")
    public Configuracao consultarConfiguracao() {
        Cursor cursor = this.banco.query(TABLE_CONFIGURACAO, null, null, null, null, null, null);

        Configuracao configuracao = null;
        if (cursor.moveToFirst()) {
            configuracao = new Configuracao();
            configuracao.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID))));
            configuracao.setNomeUsuario(cursor.getString(cursor.getColumnIndex(NOME_USUARIO)));
            configuracao.setMetaLucroLiquidoMensal(new BigDecimal(cursor.getString(cursor.getColumnIndex(META_LUCRO_LIQUIDO_MENSAL))));
        }
        cursor.close();
        return configuracao;
    }
}