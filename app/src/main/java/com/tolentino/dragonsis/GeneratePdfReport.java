package com.tolentino.dragonsis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class GeneratePdfReport extends AppCompatActivity {

    Button btn_gnr_sales;
    Button btn_gnr_inv;
    Button btn_gnr_full;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_pdf_report);

        ActivityCompat.requestPermissions(GeneratePdfReport.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        btn_gnr_sales = findViewById(R.id.btn_gnr_sales);
        btn_gnr_inv = findViewById(R.id.btn_gnr_inv);
        btn_gnr_full = findViewById(R.id.btn_gnr_full);

        btn_gnr_sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create Sales Report
                createSalesPdf();
                Toast.makeText(GeneratePdfReport.this, "Generate Sales PDF", Toast.LENGTH_SHORT).show();
            }
        });

        btn_gnr_inv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create Inventory Report
                Toast.makeText(GeneratePdfReport.this, "Generate Inventory PDF", Toast.LENGTH_SHORT).show();
            }
        });

        btn_gnr_full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create Full Report
                Toast.makeText(GeneratePdfReport.this, "Generate Full PDF", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void createSalesPdf() {

        PdfDocument mySalesPdf = new PdfDocument();
        PdfDocument.PageInfo mySalesPageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        PdfDocument.Page mySalesPage = mySalesPdf.startPage(mySalesPageInfo);

        Paint mySalesPaint = new Paint();
        String mySalesString = "Sales Sample";
        int x = 10;
        int y = 25;
        mySalesPage.getCanvas().drawText(mySalesString, x, y, mySalesPaint);
        mySalesPdf.finishPage(mySalesPage);

        String myFilePath = Environment.getExternalStorageDirectory().getPath() + "/mySalesPdf.pdf";
        File myFile = new File(myFilePath);

        try {
            mySalesPdf.writeTo(new FileOutputStream(myFile));
        }catch (Exception e){
            Toast.makeText(GeneratePdfReport.this, "Di gumana Sales LMAO", Toast.LENGTH_SHORT).show();

        }

        mySalesPdf.close();


    }
}