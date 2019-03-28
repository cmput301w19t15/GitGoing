/*
 * Class Name: ScanBarCode
 *
 * Version: 1.0
 *
 * Copyright 2019 TEAM GITGOING
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.example.cmput301w19t15.Functions;
//:)
/**
 * Enables scanning book to confirm exchange
 * @author Yourui, Anjesh
 * @version 1.0
 * @since 1.0
 */

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.Toast;

import com.example.cmput301w19t15.R;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

import info.androidhive.barcode.BarcodeReader;

/**
 * The type Scan barcode.
 */
public class ScanBarcode extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener{
    private BarcodeReader barcodeReader;

    /**
     * use library to read barcode
     * @reuse https://github.com/ravi8x/Barcode-Reader
     * @param savedInstanceState - saved instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //https://github.com/ravi8x/Barcode-Reader
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_barcode);
        barcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_fragment);
    }

    /**
     * On scanned.
     *
     * @param barcode the barcode
     */
    @Override
    public void onScanned(Barcode barcode) {
        barcodeReader.playBeep();
        Intent returnIntent = new Intent();
        returnIntent.putExtra("ISBN",barcode.displayValue.toString());
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    /**
     * On scanned multiple.
     *
     * @param barcodes the barcodes
     */
    @Override
    public void onScannedMultiple(List<Barcode> barcodes) {
        String codes = "";
        for (Barcode barcode : barcodes) {
            codes += barcode.displayValue + ", ";
        }

        final String finalCodes = codes;
         ScanBarcode.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ScanBarcode.this, "Barcodes: " + finalCodes, Toast.LENGTH_SHORT).show();
            }
        });
        Intent returnIntent = new Intent();
        returnIntent.putExtra("ISBN",finalCodes);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();

    }

    /**
     * On bitmap scanned.
     *
     * @param sparseArray the sparse array
     */
    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    /**
     * On scan error.
     *
     * @param errorMessage the error message
     */
    @Override
    public void onScanError(String errorMessage) {

    }

    /**
     * On camera permission denied.
     */
    @Override
    public void onCameraPermissionDenied() {
        Toast.makeText(getApplicationContext(), "Camera permission denied!", Toast.LENGTH_LONG).show();
    }
}
