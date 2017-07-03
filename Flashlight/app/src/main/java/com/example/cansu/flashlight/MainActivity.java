package com.example.cansu.flashlight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton btnSwitch;
    private Camera camera;
    private boolean flashAcik;
    private boolean flashVarmı;
    Parameters params;
    MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSwitch = (ImageButton) findViewById(R.id.btnSwitch); //ImageButton'u tanımladık
//İlk işlem kameranın flash'ının olup olmadığı
        flashVarmı = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if (!flashVarmı) {
//FlashYoksa alertDialog ile ekrana flash'ın olmadığını çıkardık
            AlertDialog alert = new AlertDialog.Builder(MainActivity.this).create();
            alert.setTitle("HATA");
            alert.setMessage("Bu aygıt Flash desteklemiyor");
            alert.setButton("Tamam", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
// Tamam'a basınca uygulamayı kapatıyoruz.
                    finish();
                }
            });
            alert.show();
            return;
        }
// camera'ya erişiyoruz
        cameraAc();
//Flash'ın durumuna göre button'un resmini ayarlıyoruz
        toggleButtonImage();
/*
* Button'a her tıklandığında olacaklar. Flash açıksa kapat, Kapalıysa aç
*/
        btnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flashAcik) { // Flash Acik true dönücek. Eğer Flash Açıksa Kapatıcak
                    flashKapat();
                } else {
// Açık değilse button'a basınca açıcak
                    flashAc();
                }
            }
        });
    }
    /*
    * Get the camera
    */
    private void cameraAc() {
        if (camera == null) {
            camera = Camera.open();
            params = camera.getParameters();
        }
    }
    /*
    * Flash'ı açma kısmı burada
    */
    private void flashAc() {
        if (!flashAcik) {
            if (camera == null || params == null) {
                return;
            }
            params = camera.getParameters();
            params.setFlashMode(Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
            flashAcik = true;
// Flash Button'u Değişiyor
            toggleButtonImage();
        }
    }
    /*
    * Flash'ı kapatma kısmı
    */
    private void flashKapat() {
        if (flashAcik) {
            if (camera == null || params == null) {
                return;
            }
            params = camera.getParameters();
            params.setFlashMode(Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();
            flashAcik = false;
// changing button/switch image
            toggleButtonImage();
        }
    }
    /*
    * Button'un değiştirmesi
    * */
    private void toggleButtonImage(){
        if(flashAcik){
            btnSwitch.setImageResource(R.drawable.flash_on);
        }else{
            btnSwitch.setImageResource(R.drawable.flash_off);
        }
    }
}
