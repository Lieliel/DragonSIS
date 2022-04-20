package com.tolentino.dragonsis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class ManagerGeneratePdfReport extends AppCompatActivity {

    Button btn_gnr_sales;
    Button btn_gnr_inv;
    Button btn_gnr_full;
    Button btn_back_report;
    DbManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_generate_pdf_report);

        ActivityCompat.requestPermissions(ManagerGeneratePdfReport.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        btn_gnr_sales = findViewById(R.id.btn_gnr_sales);
        btn_gnr_inv = findViewById(R.id.btn_gnr_inv);
        btn_gnr_full = findViewById(R.id.btn_gnr_full);
        btn_back_report = findViewById(R.id.btn_back_report);
        db = new DbManager(this);

        btn_gnr_sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create Sales Report
                try {
                    createSalesPdf();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        btn_gnr_inv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create Inventory Report
                try {
                    createInvPdf();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });

        btn_gnr_full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create Full Report
                try {
                    createFullPdf();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });

        //Back to Manager Menu
        btn_back_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ManagerGeneratePdfReport.this, ManagerMenu.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void createFullPdf() throws FileNotFoundException {

        //Specifying where to put and what File Name to use
        String cTime = new SimpleDateFormat("HH:mm a", Locale.getDefault()).format(new Date());
        String cDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String myFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        //Log.i("AYO", myFilePath);
        File myFile = new File(myFilePath, "DMFull_"+cDate+"_"+cTime+".pdf");
        OutputStream outputStream = new FileOutputStream(myFile);

        PdfWriter writer = new PdfWriter(myFile);
        PdfDocument pdfDocument = new PdfDocument(writer);
        pdfDocument.setDefaultPageSize(PageSize.LETTER);
        Rectangle pageSize = pdfDocument.getDefaultPageSize();
        Document doc = new Document(pdfDocument);

        //Header Texts
        Text Title = new Text("Dragon M Inventory and Sales Report");
        Text dateAndTime = new Text(cDate + ", " + cTime);
        Paragraph titleP = new Paragraph();
        Paragraph dateAndTimeP = new Paragraph();
        titleP.add(Title).setBold().setFontSize(35);
        dateAndTimeP.add(dateAndTime).setItalic().setFontSize(29);

        doc.showTextAligned(titleP,pageSize.getWidth()/2, pageSize.getHeight() - 70, TextAlignment.CENTER);
        doc.showTextAligned(dateAndTimeP,pageSize.getWidth()/2,pageSize.getHeight() - 110, TextAlignment.CENTER);

        //Start of Inventory Table
        float invColWidth[] = {80f, 420f, 200f, 150f, 170f, 180f};
        Table invTable = new Table(invColWidth);
        invTable.addCell("ID");
        invTable.addCell("NAME");
        invTable.addCell("CATEGORY");
        invTable.addCell("CRITICAL NUMBER");
        invTable.addCell("TOTAL QUANTITY");
        invTable.addCell("PRICE IN PHP");

        ArrayList<HashMap<String, String>> inventoryList = db.getProducts();

        int tablePosition = 200;
        for (int i = 0; i < inventoryList.size(); i++) {
            HashMap<String, String> inventoryRecord = inventoryList.get(i);
            if(Integer.parseInt(inventoryRecord.get("prod_total_quantity"))>=Integer.parseInt(inventoryRecord.get("prod_critical_num"))){
                invTable.addCell(inventoryRecord.get("prod_ID")).setPadding(10);
                invTable.addCell(inventoryRecord.get("prod_name")).setPadding(10);
                invTable.addCell(inventoryRecord.get("prod_category")).setPadding(10);
                invTable.addCell(inventoryRecord.get("prod_critical_num")).setPadding(10);
                invTable.addCell(new Cell().setBackgroundColor(ColorConstants.GREEN).add(new Paragraph(inventoryRecord.get("prod_total_quantity")))).setPadding(10);
                invTable.addCell(inventoryRecord.get("prod_price")).setPadding(10);
                tablePosition += 18;
            }else{
                invTable.addCell(inventoryRecord.get("prod_ID")).setPadding(10);
                invTable.addCell(inventoryRecord.get("prod_name")).setPadding(10);
                invTable.addCell(inventoryRecord.get("prod_category")).setPadding(10);
                invTable.addCell(inventoryRecord.get("prod_critical_num")).setPadding(10);
                invTable.addCell(new Cell().setBackgroundColor(ColorConstants.RED).add(new Paragraph(inventoryRecord.get("prod_total_quantity")))).setPadding(10);
                invTable.addCell(inventoryRecord.get("prod_price")).setPadding(10);
                tablePosition += 18;
            }
        }
        invTable.setFixedPosition(25, pageSize.getTop() - tablePosition,pageSize.getWidth()-50);
        doc.add(invTable);
        doc.add(new AreaBreak());

        //Start of Sales Table
        float salesColWidth[] = {80f, 420f, 200f, 150f, 170f, 180f};
        Table salTable = new Table(salesColWidth);
        salTable.addCell("ID");
        salTable.addCell("PRODUCT");
        salTable.addCell("SALES DATE");
        salTable.addCell("SALES TIME");
        salTable.addCell("NO. OF ITEM");
        salTable.addCell("SALES AMOUNT");

        ArrayList<HashMap<String, String>> salesList = db.getSales();

        for (int i = 0; i < salesList.size(); i++) {
            HashMap<String, String> salesRecord = salesList.get(i);
            salTable.addCell(salesRecord.get("sales_ID")).setPadding(10);
            salTable.addCell(salesRecord.get("product_sold")).setPadding(10);
            salTable.addCell(salesRecord.get("sales_dates")).setPadding(10);
            salTable.addCell(salesRecord.get("sales_time")).setPadding(10);
            salTable.addCell(salesRecord.get("items_sold")).setPadding(10);
            salTable.addCell(salesRecord.get("sales_amount")).setPadding(10);
            //tablePosition += 18;
        }

        doc.add(salTable);
        doc.close();
        Toast.makeText(this, "Full Report Generated", Toast.LENGTH_SHORT).show();
    }

    private void createInvPdf() throws FileNotFoundException{
        //Specifying where to put and what File Name to use
        String cTime = new SimpleDateFormat("HH:mm a", Locale.getDefault()).format(new Date());
        String cDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String myFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File myFile = new File(myFilePath, "DMInventory_"+cDate+"_"+cTime+".pdf");
        OutputStream outputStream = new FileOutputStream(myFile);

        PdfWriter writer = new PdfWriter(myFile);
        PdfDocument pdfDocument = new PdfDocument(writer);
        pdfDocument.setDefaultPageSize(PageSize.LETTER);
        Rectangle pageSize = pdfDocument.getDefaultPageSize();
        Document doc = new Document(pdfDocument);

        //Header Texts
        Text Title = new Text("Dragon M Inventory Report");
        Text dateAndTime = new Text(cDate + ", " + cTime);
        Paragraph titleP = new Paragraph();
        Paragraph dateAndTimeP = new Paragraph();
        titleP.add(Title).setBold().setFontSize(40);
        dateAndTimeP.add(dateAndTime).setItalic().setFontSize(30);

        doc.showTextAligned(titleP,pageSize.getWidth()/2, pageSize.getHeight() - 70, TextAlignment.CENTER);
        doc.showTextAligned(dateAndTimeP,pageSize.getWidth()/2,pageSize.getHeight() - 110, TextAlignment.CENTER);

        float colWidth[] = {80f, 420f, 200f, 150f, 170f, 180f};
        Table invTable = new Table(colWidth);
        invTable.addCell("ID");
        invTable.addCell("NAME");
        invTable.addCell("CATEGORY");
        invTable.addCell("CRITICAL NUMBER");
        invTable.addCell("TOTAL QUANTITY");
        invTable.addCell("PRICE IN PHP");

        ArrayList<HashMap<String, String>> inventoryList = db.getProducts();

        int tablePosition = 200;
        for (int i = 0; i < inventoryList.size(); i++) {
            HashMap<String, String> inventoryRecord = inventoryList.get(i);
            if(Integer.parseInt(inventoryRecord.get("prod_total_quantity"))>=Integer.parseInt(inventoryRecord.get("prod_critical_num"))){
                invTable.addCell(inventoryRecord.get("prod_ID")).setPadding(10);
                invTable.addCell(inventoryRecord.get("prod_name")).setPadding(10);
                invTable.addCell(inventoryRecord.get("prod_category")).setPadding(10);
                invTable.addCell(inventoryRecord.get("prod_critical_num")).setPadding(10);
                invTable.addCell(new Cell().setBackgroundColor(ColorConstants.GREEN).add(new Paragraph(inventoryRecord.get("prod_total_quantity")))).setPadding(10);
                invTable.addCell(inventoryRecord.get("prod_price")).setPadding(10);
                tablePosition += 18;
            }else{
                invTable.addCell(inventoryRecord.get("prod_ID")).setPadding(10);
                invTable.addCell(inventoryRecord.get("prod_name")).setPadding(10);
                invTable.addCell(inventoryRecord.get("prod_category")).setPadding(10);
                invTable.addCell(inventoryRecord.get("prod_critical_num")).setPadding(10);
                invTable.addCell(new Cell().setBackgroundColor(ColorConstants.RED).add(new Paragraph(inventoryRecord.get("prod_total_quantity")))).setPadding(10);
                invTable.addCell(inventoryRecord.get("prod_price")).setPadding(10);
                tablePosition += 18;
            }
        }
        invTable.setFixedPosition(25, pageSize.getTop() - tablePosition,pageSize.getWidth()-50);
        doc.add(invTable);

        doc.close();
        Toast.makeText(this, "Inventory Report Generated", Toast.LENGTH_SHORT).show();
    }

    public void createSalesPdf() throws FileNotFoundException{
        //Specifying where to put and what File Name to use
        String cTime = new SimpleDateFormat("HH:mm a", Locale.getDefault()).format(new Date());
        String cDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String myFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        //Log.i("AYO", myFilePath);
        File myFile = new File(myFilePath, "DMSales_"+cDate+"_"+cTime+".pdf");
        OutputStream outputStream = new FileOutputStream(myFile);

        PdfWriter writer = new PdfWriter(myFile);
        PdfDocument pdfDocument = new PdfDocument(writer);
        pdfDocument.setDefaultPageSize(PageSize.LETTER);
        Rectangle pageSize = pdfDocument.getDefaultPageSize();
        Document doc = new Document(pdfDocument);

        //Header Texts
        Text Title = new Text("Dragon M Sales Report");
        Text dateAndTime = new Text(cDate + ", " + cTime);
        Paragraph titleP = new Paragraph();
        Paragraph dateAndTimeP = new Paragraph();
        titleP.add(Title).setBold().setFontSize(40);
        dateAndTimeP.add(dateAndTime).setItalic().setFontSize(30);

        doc.showTextAligned(titleP,pageSize.getWidth()/2, pageSize.getHeight() - 70, TextAlignment.CENTER);
        doc.showTextAligned(dateAndTimeP,pageSize.getWidth()/2,pageSize.getHeight() - 110, TextAlignment.CENTER);

        float salesColWidth[] = {80f, 420f, 200f, 150f, 170f, 180f};
        Table salTable = new Table(salesColWidth);
        salTable.addCell("ID");
        salTable.addCell("PRODUCT");
        salTable.addCell("SALES DATE");
        salTable.addCell("SALES TIME");
        salTable.addCell("NO. OF ITEM");
        salTable.addCell("SALES AMOUNT");

        ArrayList<HashMap<String, String>> salesList = db.getSales();

        int tablePosition = 200;
        int totalSales = 0;
        for (int i = 0; i < salesList.size(); i++) {
            HashMap<String, String> salesRecord = salesList.get(i);
            salTable.addCell(salesRecord.get("sales_ID")).setPadding(10);
            salTable.addCell(salesRecord.get("product_sold")).setPadding(10);
            salTable.addCell(salesRecord.get("sales_dates")).setPadding(10);
            salTable.addCell(salesRecord.get("sales_time")).setPadding(10);
            salTable.addCell(salesRecord.get("items_sold")).setPadding(10);
            salTable.addCell(salesRecord.get("sales_amount")).setPadding(10);
            totalSales = totalSales + Integer.parseInt(salesRecord.get("sales_amount"));
            tablePosition += 18;
            Log.i("SALES TAG", String.valueOf(totalSales));
        }

        salTable.addCell(new Cell(1,6).add(new Paragraph("Total Sales Amount: " + String.valueOf(totalSales)))).setTextAlignment(TextAlignment.CENTER);

        salTable.setFixedPosition(25, pageSize.getTop() - tablePosition,pageSize.getWidth()-50);

        doc.add(salTable);
        doc.close();
        Toast.makeText(this, "Sales Report Generated", Toast.LENGTH_SHORT).show();

    }
}