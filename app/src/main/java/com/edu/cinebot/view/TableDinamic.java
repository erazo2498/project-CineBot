package com.edu.cinebot.view;

import android.content.Context;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class TableDinamic {
    private TableLayout tableLayout;
    private Context context;
    private String[] header;
    private ArrayList<String[]> data;
    private TableRow tableRow;
    private TextView txtCell;
    private int indexColumn;
    private int indexRow;
    private boolean multiColor=false;
    public int firstColor, secondColor,txtColor;

    public TableDinamic(TableLayout tableLayout,Context context){
        this.tableLayout=tableLayout;
        this.context=context;
    }
    public void addHeader(String[] header){
        this.header=header;
        createHeader();
    }
    public void addData(ArrayList<String[]> data){
        this.data=data;
        createDataTable();
    }
    private void newRow(){
        tableRow=new TableRow(context);
    }
    private void newCell(){
        txtCell= new TextView(context);
        txtCell.setGravity(Gravity.CENTER);
        txtCell.setTextSize(25);
    }
    private void createHeader(){
        indexColumn=0;
        newRow();
        while (indexColumn<header.length){
            newCell();
            txtCell.setText(header[indexColumn++]);
            tableRow.addView(txtCell,newTableRowsParams());
        }
        this.tableLayout.addView(tableRow);
    }
    private void createDataTable(){
        String info;
        for(indexRow=1; indexRow<=header.length; indexRow++){
            newRow();
            for(indexColumn=0; indexColumn<=header.length; indexColumn++){
                newCell();
                String[] row = (data.get(indexRow-1));
                info=(indexColumn<row.length)?row[indexColumn]:"";
                txtCell.setText(info);
                tableRow.addView(txtCell,newTableRowsParams());
            }
            this.tableLayout.addView(tableRow);
        }
    }
    private TableRow getRow(int index){
        return (TableRow) tableLayout.getChildAt(index);
    }
    private TextView getCell(int rowIndex,int columnIndex){
        tableRow=getRow(rowIndex);
        return (TextView) tableRow.getChildAt(columnIndex);
    }
    public void backGroundHaeder(int color){
        indexColumn=0;
        while (indexColumn<header.length){
            txtCell=getCell(0,indexColumn++);
            txtCell.setBackgroundColor(color);
        }
    }
    public void backGroundData(int firstColor,int secondColor){
        for(indexRow=1; indexRow<=header.length; indexRow++){
            //Cambia de false a true y al sentido contrario
            multiColor=!multiColor;
            for(indexColumn=0; indexColumn<=header.length; indexColumn++){
                txtCell=getCell(indexRow,indexColumn);
                txtCell.setBackgroundColor((multiColor)?firstColor:secondColor);
            }
        }
        this.firstColor=firstColor;
        this.secondColor=secondColor;
    }
    public void txtColorData(int color){
        for(indexRow=1; indexRow<=header.length; indexRow++){
            for(indexColumn=0; indexColumn<=header.length; indexColumn++){
                getCell(indexRow,indexColumn).setTextColor(color);
            }
        }
        this.txtColor=color;
    }
    public void txtColorHeader(int color){
        indexColumn=0;
        while (indexColumn<header.length){
            getCell(0,indexColumn++).setTextColor(color);
        }
    }
    public void lineColor(int color){
        indexRow=0;
        while (indexRow<data.size()){
            getRow(indexRow++).setBackgroundColor(color);
        }
    }

    public void reColoring(){
        indexColumn=0;
        multiColor=!multiColor;
        while (indexColumn<header.length){
            txtCell=getCell(data.size()-1,indexColumn++);
            txtCell.setBackgroundColor((multiColor)?firstColor:secondColor);
            txtCell.setTextColor(txtColor);
        }
    }
    private TableRow.LayoutParams newTableRowsParams(){
        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.setMargins(1,1,1,1);
        params.weight=1;
        return params;
    }
    public void addItems(String[] item){
        String info;
        data.add(item);
        indexColumn=0;
        newRow();
        while (indexColumn<header.length){
            newCell();
            info=(indexColumn<item.length)?item[indexColumn++]:"";
            txtCell.setText(info);
            tableRow.addView(txtCell,newTableRowsParams());
        }
        tableLayout.addView(tableRow,data.size()-1);
        reColoring();
    }

}
