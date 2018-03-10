package c.r.musicro;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.media.MediaPlayer;
import android.net.Uri;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.provider.DocumentFile;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ListView;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    Uri dirUri =null;
    SharedPreferences sp;
    final MediaPlayer mp=new MediaPlayer();
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar tb = findViewById(R.id.tb);
        setSupportActionBar(tb);

        //restore list
        sp = getPreferences(Context.MODE_PRIVATE);
        String savedDir = sp.getString("dir",null);
        if (savedDir!= null) {
            dirUri = Uri.parse(savedDir);
            makeList();
        }
    }

    public void btFolder(View view) {
        Intent get = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        startActivityForResult(get, 0);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent selected) {
        if (selected != null) {
            dirUri = selected.getData();

            SharedPreferences.Editor editor = sp.edit();
            editor.putString("dir",dirUri.toString());
            editor.apply();
            makeList();
        }
    }

    public void btPlay(View view)
        { FloatingActionButton btplay = findViewById(R.id.play);
        if (mp.isPlaying())
            { mp.pause();
                btplay.setImageResource(R.drawable.play);}
        else {
            mp.start();
            btplay.setImageResource(R.drawable.pause); } }

    // fill list
    void makeList(){
        final DocumentFile[] FileList;

        ListView lv = findViewById(R.id.lv);

        FileList =  DocumentFile.fromTreeUri(MainActivity.this, dirUri).listFiles();
        String[] NameList = new String[FileList.length];
        for (int i=0;i<FileList.length;i++) NameList[i] = FileList[i].getName();
        ArrayAdapter<String> Adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                NameList);
        lv.setAdapter(Adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView parent,View v,final int position,long id){

                new Thread(new Runnable() {
                    public void run() {
                        mp.reset();
                        try{mp.setDataSource(
                                MainActivity.this,FileList[position].getUri());}
                        catch(IOException e){e.printStackTrace();}
                        try{
                            mp.prepare();
                        }catch(IOException e){ e.printStackTrace(); }
                        mp.start();
                    }
                }).start();
    }
    });}
}


