package com.llo.lafinance.repositorio;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.llo.lafinance.config.Conexao;
import com.llo.lafinance.model.Venda;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

public class VendaRepository {

    public static final String TABLE_VENDA = "venda";
    public static final String ID = "id";
    public static final String COMPRA = "compra";
    public static final String QUANTIDADE = "quantidade";
    public static final String PRECO_UNITARIO = "precoUnitario";
    public static final String PRECO_TOTAL = "precoTotal";
    public static final String LUCRO_TOTAL = "lucroTotal";
    public static final String DATA_CRIACAO = "dataCriacao";

    public static final String DATA_ATUALIZACAO = "dataAtualizacao";
    private final SQLiteDatabase banco;

    public VendaRepository(Context context) {
        Conexao conexao = new Conexao(context);
        this.banco = conexao.getWritableDatabase();
    }

    public void inserir(Venda venda) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COMPRA, venda.getCompra());
        contentValues.put(QUANTIDADE, venda.getQuantidade());
        contentValues.put(PRECO_UNITARIO, venda.getPrecoUnitario().toString());
        contentValues.put(PRECO_TOTAL, venda.getPrecoTotal().toString());
        contentValues.put(LUCRO_TOTAL, venda.getLucroTotal().toString());
        contentValues.put(DATA_CRIACAO, venda.getDataCriacao().toString());
        this.banco.insert(TABLE_VENDA, null, contentValues);
    }

    public void atualizar(Venda venda) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(QUANTIDADE, venda.getQuantidade());
        contentValues.put(PRECO_UNITARIO, venda.getPrecoUnitario().toString());
        contentValues.put(PRECO_TOTAL, venda.getPrecoTotal().toString());
        contentValues.put(LUCRO_TOTAL, venda.getLucroTotal().toString());
        contentValues.put(DATA_ATUALIZACAO, venda.getDataAtualizacao().toString());
        this.banco.update(TABLE_VENDA, contentValues, ID + " = ?", new String[]{venda.getId().toString()});
    }

    @SuppressLint("Range")
    public ArrayList<Venda> consultarVendas() {
        Cursor cursor = this.banco.query(TABLE_VENDA, null, null, null, null, null, null);

        ArrayList<Venda> vendas = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Venda venda = new Venda();
                venda.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID))));
                venda.setCompra(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COMPRA))));
                venda.setQuantidade(Integer.parseInt(cursor.getString(cursor.getColumnIndex(QUANTIDADE))));
                venda.setPrecoUnitario(new BigDecimal(cursor.getString(cursor.getColumnIndex(PRECO_UNITARIO))));
                venda.setPrecoTotal(new BigDecimal(cursor.getString(cursor.getColumnIndex(PRECO_TOTAL))));
                venda.setLucroTotal(new BigDecimal(cursor.getString(cursor.getColumnIndex(LUCRO_TOTAL))));
                venda.setDataCriacao(LocalDate.parse(cursor.getString(cursor.getColumnIndex(DATA_CRIACAO))));
                vendas.add(venda);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return vendas;
    }

    public void deletar(Integer venda) {
        this.banco.delete(TABLE_VENDA, ID + "=" + venda, null);
    }

    @SuppressLint("Range")
    public Venda consultarTopEmpresaAno(Integer ano) {
        String[] columns = new String[]{
                COMPRA,
                "sum(" + LUCRO_TOTAL + ") as tmp_lucro_total_venda",
                DATA_CRIACAO,
        };
        Cursor cursor = this.banco.query(TABLE_VENDA, columns, null, null, "date(" + DATA_CRIACAO + ")", null, LUCRO_TOTAL + " DESC");

        ArrayList<Venda> vendas = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Venda venda = new Venda();
                venda.setCompra(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COMPRA))));
                venda.setLucroTotal(new BigDecimal(cursor.getString(cursor.getColumnIndex("tmp_lucro_total_venda"))));
                venda.setDataCriacao(LocalDate.parse(cursor.getString(cursor.getColumnIndex(DATA_CRIACAO))));
                vendas.add(venda);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return vendas.isEmpty() ? null : vendas.get(0);
    }
}
