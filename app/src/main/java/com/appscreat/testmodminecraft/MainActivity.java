package com.appscreat.testmodminecraft;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button button;

    //Для версий 0.15.7, 0.15.6, 0.15.4, 0.15.0, 0.14.3
    String url = "https://storage.googleapis.com/json-data-base.appspot.com/apps/modsforminecraft/mods/screenshots/UpdateMods/PixelmonMod.modpkg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button1);
        button.setText("Download Mod");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Скачивание мода
                new FileManager.DownloadFileAsync(MainActivity.this, "/Downloads/Mods/").execute(url);

                //После скачивания
                installModToMinecraft();
            }
        });
    }

    public void installModToMinecraft() {
        button.setText("Instal Mod");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Установить мод

                //После установки мода
                openMinecraft();
            }
        });
    }

    public void openMinecraft() {
        button.setText("Launch Minecraft");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Запустить Minecraft PE с активированным модом
                //Minecraft PE должен быть установлен (Можно пиратку)
                openApp("com.mojang.minecraftpe");
            }
        });
    }

    public void openApp(String packageName) {
        try {
            Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
            if (intent == null) {
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=" + packageName));
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (android.content.ActivityNotFoundException e) {
            e.printStackTrace();
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + packageName)));
        }
    }


}
