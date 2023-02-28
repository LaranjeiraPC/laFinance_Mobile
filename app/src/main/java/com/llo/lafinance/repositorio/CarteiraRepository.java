package com.llo.lafinance.repositorio;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.llo.lafinance.config.Conexao;
import com.llo.lafinance.model.Carteira;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CarteiraRepository {

    public static final String ID = "id";
    public static final String TABLE_CARTEIRA = "carteira";
    public static final String TOTAL_INVESTIDO = "totalInvestido";
    public static final String LUCRO_LIQUIDO_TOTAL_ANO = "lucroLiquidoTotalAno";
    public static final String MES_LUCRO_LIQUIDO = "mesLucroLiquido";
    public static final String VALOR_MES_LUCRO_LIQUIDO = "valorMesLucroLiquido";
    public static final String ANO_VIGOR = "anoVigor";
    public static final String DATA_ATUALIZACAO = "dataAtualizacao";
    public static final String DATA_CRIACAO = "dataCriacao";
    private final SQLiteDatabase banco;

    public CarteiraRepository(Context context) {
        Conexao conexao = new Conexao(context);
        this.banco = conexao.getWritableDatabase();
    }

    public void inserir(Carteira carteira) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TOTAL_INVESTIDO, carteira.getTotalInvestido().toString());
        contentValues.put(LUCRO_LIQUIDO_TOTAL_ANO, carteira.getLucroLiquidoTotalAno().toString());
        contentValues.put(MES_LUCRO_LIQUIDO, carteira.getMesLucroLiquido());
        contentValues.put(VALOR_MES_LUCRO_LIQUIDO, carteira.getValorMesLucroLiquido().toString());
        contentValues.put(ANO_VIGOR, carteira.getAnoVigor().toString());
        contentValues.put(DATA_CRIACAO, carteira.getDataCriacao().toString());
        this.banco.insert(TABLE_CARTEIRA, null, contentValues);
    }

    public void atualizar(Carteira carteira) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TOTAL_INVESTIDO, carteira.getTotalInvestido().toString());
        contentValues.put(LUCRO_LIQUIDO_TOTAL_ANO, carteira.getLucroLiquidoTotalAno().toString());
        contentValues.put(MES_LUCRO_LIQUIDO, carteira.getMesLucroLiquido());
        contentValues.put(VALOR_MES_LUCRO_LIQUIDO, carteira.getValorMesLucroLiquido().toString());
        contentValues.put(ANO_VIGOR, carteira.getAnoVigor().toString());
        contentValues.put(DATA_ATUALIZACAO, carteira.getDataAtualizacao().toString());
        this.banco.update(TABLE_CARTEIRA, contentValues, ID + " = ?", new String[]{carteira.getId().toString()});
    }

    @SuppressLint("Range")
    public Carteira consultarCarteira(Integer ano) {
        Cursor cursor = this.banco.query(TABLE_CARTEIRA, null, ANO_VIGOR + "=" + ano, null, null, null, null);

        Carteira carteira = new Carteira();
        if (cursor.moveToFirst()) {
            do {
                carteira.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID))));
                carteira.setAnoVigor(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ANO_VIGOR))));
                carteira.setValorMesLucroLiquido(new BigDecimal(cursor.getString(cursor.getColumnIndex(VALOR_MES_LUCRO_LIQUIDO))));
                carteira.setMesLucroLiquido(cursor.getString(cursor.getColumnIndex(MES_LUCRO_LIQUIDO)));
                carteira.setTotalInvestido(new BigDecimal(cursor.getString(cursor.getColumnIndex(TOTAL_INVESTIDO))));
                carteira.setLucroLiquidoTotalAno(new BigDecimal(cursor.getString(cursor.getColumnIndex(LUCRO_LIQUIDO_TOTAL_ANO))));
                carteira.setDataCriacao(LocalDate.parse(cursor.getString(cursor.getColumnIndex(DATA_CRIACAO))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return carteira;
    }
}
