package com.appscreat.testmodminecraft;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class FileManager {

    private final static String TAG = "FileManager";
    private static ProgressDialogManager progressDialog;

    public static class DownloadFileAsync extends AsyncTask<String, String, String> {

        private Context context;
        private File file;
        private File dir;
        private File sdPath;
        private String fileExt;
        private String fileName = null;
        private String as[] = null;
        private String pathSave;
        private OutputStream output;
        private InputStream input;

        public DownloadFileAsync(Context context, String pathSave) {
            this.context = context;
            this.pathSave = pathSave;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Создаем диалог загрузки
            progressDialog = new ProgressDialogManager(context, ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.showProgressDialog();
        }

        @Override
        protected String doInBackground(String... aurl) {
            int count;

            try {
                URL url = new URL(aurl[0]);
                Log.d(TAG, "doInBackground: URL " + url);

                URLConnection conexion = url.openConnection();
                conexion.connect();
                int lenghtOfFile = conexion.getContentLength();

                Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);
                input = new BufferedInputStream(url.openStream());
                String path = url.getPath();

                //Расширение файла
                fileExt = path.substring(path.lastIndexOf('.') + 1);
                Log.d(TAG, "doInBackground: File Extension " + fileExt);

                //Полное имя файла
                fileName = path.substring(path.lastIndexOf('/') + 1);
                Log.d(TAG, "doInBackground: File Name " + fileName);

                //Корневая директория внешней карты мобильного устройства
                sdPath = Environment.getExternalStorageDirectory();

                //Указываем директорию для сохранения
                dir = new File(sdPath.getAbsolutePath() + pathSave);

                //Если директория не существует, создаем ее
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                //Создаем файл
                file = new File(dir, fileName);

                //Записываем файл
                output = new FileOutputStream(file);
                byte data[] = new byte[1024];
                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();

                if (file != null) {
                    as = new String[1];
                    as[0] = file.toString();

                    //Обновляем файловый сканер
                    MediaScannerConnection.scanFile(context, as, null, new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String s1, Uri uri) {
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }

        protected void onProgressUpdate(String... progress) {
            Log.d("ANDRO_ASYNC", progress[0]);
            progressDialog.setProgressDialog(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(String unused) {

            //Убираем диалог загрузки
            progressDialog.dismissProgressDialog();

        }
    }
}
