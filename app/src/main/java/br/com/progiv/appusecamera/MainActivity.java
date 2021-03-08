package br.com.progiv.appusecamera;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Button btnFoto;
    Button btnGravar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Checar se tenhoh a permissão de Câmera:
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},0);
        }

        //CAST - Parse de componentes em tela para código
        imageView = (ImageView)findViewById(R.id.imageView);
        btnFoto = (Button)findViewById(R.id.btnFoto);
        btnGravar = (Button) findViewById(R.id.btnGravar);

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tirarFoto();
            }
        });

        btnGravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gravarVideo();
            }
        });
    }

    public void tirarFoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,1);
    }

    public void gravarVideo(){
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10); //limitar em 10 seg a gravação
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        /*
        if(requestCode == 1 && resultCode == RESULT_OK){
            Bundle bundle = data.getExtras();
            Bitmap imagem = (Bitmap)bundle.get("data");
            imageView.setImageBitmap(imagem);
        }*/
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case 1:
                    Bundle bundle = data.getExtras();
                    Bitmap imagem = (Bitmap)bundle.get("data");
                    imageView.setImageBitmap(imagem);
                    break;
                case 2:
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    VideoView videoView = new VideoView(this);
                    videoView.setVideoURI(data.getData());
                    videoView.start();
                    builder.setView(videoView).show();
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}