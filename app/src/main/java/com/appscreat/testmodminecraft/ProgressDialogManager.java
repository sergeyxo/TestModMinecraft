package com.appscreat.testmodminecraft;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDialogManager {

    private ProgressDialog progressDialog;

    public ProgressDialogManager(Context context) {
        if (progressDialog == null) {
            initProgressDialog(context);
            progressDialog.setMessage("Loading");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
    }

    public ProgressDialogManager(Context context, int style) {
        if (progressDialog == null) {
            initProgressDialog(context);
            progressDialog.setMessage("Loading");
            progressDialog.setProgressStyle(style);
        }
    }

    public ProgressDialogManager(Context context, int style, String message) {
        if (progressDialog == null) {
            initProgressDialog(context);
            progressDialog.setMessage(message);
            progressDialog.setProgressStyle(style);
        }
    }

    public void dismissProgressDialog() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            progressDialog = null;
        }
    }

    public void showProgressDialog() {
        try {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setProgressDialog(int value){
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.setProgress(value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initProgressDialog(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(true);
//        progressDialog.setIndeterminate(true);
    }
}
