package com.tolentino.dragonsis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ManagerGeneratePdfReport extends AppCompatActivity {

    Button btn_gnr_sales;
    Button btn_gnr_inv;
    Button btn_gnr_full;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_generate_pdf_report);

        ActivityCompat.requestPermissions(ManagerGeneratePdfReport.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        btn_gnr_sales = findViewById(R.id.btn_gnr_sales);
        btn_gnr_inv = findViewById(R.id.btn_gnr_inv);
        btn_gnr_full = findViewById(R.id.btn_gnr_full);

        btn_gnr_sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create Sales Report
                createSalesPdf();
                Toast.makeText(ManagerGeneratePdfReport.this, "Sales Report Generated", Toast.LENGTH_SHORT).show();
            }
        });

        btn_gnr_inv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create Inventory Report
                createInvPdf();
                Toast.makeText(ManagerGeneratePdfReport.this, "Inventory Report Generated", Toast.LENGTH_SHORT).show();
            }
        });

        btn_gnr_full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create Full Report
                createFullPdf();
                Toast.makeText(ManagerGeneratePdfReport.this, "Full Report Generated", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createFullPdf() {
        PdfDocument myPdf = new PdfDocument();
        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        PdfDocument.Page myPage = myPdf.startPage(myPageInfo);

        Paint myPaint = new Paint();
        String reportTitle = "Dragon M Full Report";
        int x = 85;
        int y = 25;
        myPage.getCanvas().drawText(reportTitle, 93, 25, myPaint);

        String cTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        String cDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        Log.i("Current Time", cTime.toString());
        Log.i("Current Time", cDate.toString());
        myPage.getCanvas().drawText(cDate+", "+cTime, 95, 45, myPaint);

        myPdf.finishPage(myPage);

        String myFilePath = Environment.getExternalStorageDirectory().getPath() + "/DMFull_"+cDate+"_"+cTime+".pdf";
        File myFile = new File(myFilePath);

        try {
            myPdf.writeTo(new FileOutputStream(myFile));
        }catch (Exception e){
            String filePath = Environment.getExternalStorageDirectory().getPath().toString();
            Log.i("External Storage Path", filePath);
            Toast.makeText(ManagerGeneratePdfReport.this, "Report Generation Unsuccessful", Toast.LENGTH_SHORT).show();

        }

        myPdf.close();
    }

    private void createInvPdf() {
        PdfDocument myPdf = new PdfDocument();
        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        PdfDocument.Page myPage = myPdf.startPage(myPageInfo);

        Paint myPaint = new Paint();
        String reportTitle = "Dragon M Inventory Report";
        int x = 85;
        int y = 25;
        myPage.getCanvas().drawText(reportTitle, 75, 25, myPaint);

        String cTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        String cDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        Log.i("Current Time", cTime.toString());
        Log.i("Current Time", cDate.toString());
        myPage.getCanvas().drawText(cDate+", "+cTime, 95, 45, myPaint);

        myPdf.finishPage(myPage);

        String myFilePath = Environment.getExternalStorageDirectory().getPath() + "/DMInventory_"+cDate+"_"+cTime+".pdf";
        File myFile = new File(myFilePath);

        try {
            myPdf.writeTo(new FileOutputStream(myFile));
        }catch (Exception e){
            String filePath = Environment.getExternalStorageDirectory().getPath().toString();
            Log.i("External Storage Path", filePath);
            Toast.makeText(ManagerGeneratePdfReport.this, "Report Generation Unsuccessful", Toast.LENGTH_SHORT).show();

        }

        myPdf.close();
    }

    public void createSalesPdf() {
        PdfDocument myPdf = new PdfDocument();
        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        PdfDocument.Page myPage = myPdf.startPage(myPageInfo);

        Paint myPaint = new Paint();
        String reportTitle = "Dragon M Sales Report";
        int x = 85;
        int y = 25;
        myPage.getCanvas().drawText(reportTitle, 85, 25, myPaint);

        String cTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        String cDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        Log.i("Current Time", cTime.toString());
        Log.i("Current Time", cDate.toString());
        myPage.getCanvas().drawText(cDate+", "+cTime, 95, 45, myPaint);

        myPdf.finishPage(myPage);

        String myFilePath = Environment.getExternalStorageDirectory().getPath() + "/DMSales_"+cDate+"_"+cTime+".pdf";
        File myFile = new File(myFilePath);

        try {
            myPdf.writeTo(new FileOutputStream(myFile));
        }catch (Exception e){
            String filePath = Environment.getExternalStorageDirectory().getPath().toString();
            Log.i("External Storage Path", filePath);
            Toast.makeText(ManagerGeneratePdfReport.this, "Report Generation Unsuccessful", Toast.LENGTH_SHORT).show();

        }

        myPdf.close();

    }
}