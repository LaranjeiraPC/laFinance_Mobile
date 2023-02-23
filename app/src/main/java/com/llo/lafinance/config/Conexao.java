package com.llo.lafinance.config;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Conexao extends SQLiteOpenHelper {

    private static final String name = "banco.db";
    private static final int version = 1;

    public Conexao(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.criarTabelaCompra(db);
        this.criarTabelaAtivo(db);
        this.criarTabelaVenda(db);
        this.criarTabelaCarteira(db);
    }

    private void criarTabelaCompra(SQLiteDatabase db) {
        db.execSQL("create table compra(id integer primary key autoincrement, " +
                " ativo varchar(6), status varchar(1), quantidade integer, " +
                " precoUnitario decimal, precoTotal decimal, dataCriacao date," +
                " dataAtualizacao date)");
    }

    private void criarTabelaAtivo(SQLiteDatabase db) {
        db.execSQL("create table ativo(id integer primary key autoincrement, " +
                " nome varchar(5), descricao varchar(30), dataCriacao date," +
                " dataAtualizacao date)");
    }

    private void criarTabelaVenda(SQLiteDatabase db) {
        db.execSQL("create table venda(id integer primary key autoincrement, " +
                " compra integer, quantidade integer, precoUnitario decimal, " +
                " precoTotal decimal, lucroTotal decimal, dataCriacao date, dataAtualizacao date)");
    }

    private void criarTabelaCarteira(SQLiteDatabase db) {
        db.execSQL("create table carteira(id integer primary key autoincrement, " +
                " totalInvestido decimal, lucroLiquidoTotalAno decimal, mesLucroLiquido varchar(5), anoVigor integer, " +
                " valorMesLucroLiquido decimal, dataCriacao date, dataAtualizacao date)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
