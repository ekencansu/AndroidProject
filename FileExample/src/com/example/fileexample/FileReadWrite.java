package com.example.fileexample;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
 
import android.util.Log;
 
public class FileReadWrite {
     public FileReadWrite() {
 
        }
 
     public Boolean Write(String disim, String dicerik){
            try {
 
                String fpath = "/sdcard/"+disim+".txt";//olusturulan dosya icin yol belirlendi.
 
                File file = new File(fpath);//tanımlanan yol uzerinden bir file nesnesi tanımlandı.
 
             // Eger dosya olusturulmamıssa dosyayı olusturur.
                if (!file.exists()) {
                    file.createNewFile();
                }
 
                FileWriter dosyaoku = new FileWriter(file.getAbsoluteFile());
                //dosyaya veri yazabilmek için
                //FileWriter sınıfından nesne tanımı yapıldı.
                //Yani burada FileWriter sınıfı ile yazma işlemi yapılacak dosya belirlenir.
                //getAbsolute() metodu geçerli dosya nesnesinin mutlak yolunu kullanarak yeni bir dosya nesnesi dondurur.
                
                //"FileWriter dosyaoku = new FileWriter(file.getAbsoluteFile());" yerine "FileWriter dosyaoku = new FileWriter(fpath);" kullanılabilir.
                
                BufferedWriter buffer = new BufferedWriter(dosyaoku);
                //BufferedWriter sınıfından nesne tanımı yapıldı.
                //Yazma işlemi yapılacak olan dosyayı hazır hale getirir. 
                //Asıl yazma işlemini BufferedWriter nesnesi yapar.
                
                buffer.write(dicerik);//Write metodu ile dicerik dosyaya yazıldı.
                buffer.close();//BufferedWriter kapatıldı.
 
                Log.d("Suceess","Sucess");//logcat.debug
                return true;
 
            } catch (IOException e) {
                e.printStackTrace();//hatanın detaylı bir şekilde konsola yazılmasını sağlar.
                return false;
                //write komutu dosyaya yazma islemini yapamazsa bir IOException hatası fırlatılır. 
                //Bu da catch bloğu içerisinde yakalanır.
            }
 
     }
 
     public String Read(String disim){
 
         BufferedReader buf = null;
         String res = null;
 
            try {
 
                StringBuffer reader = new StringBuffer();
                //Birleştirilmek istenilen verileri depolamak için reader nesnesi olusturuldu.
                
                String fpath = "/sdcard/"+disim+".txt";//olusturulan dosya icin yol belirlendi.
 
                buf = new BufferedReader(new FileReader(fpath));
              //tanımlanan yol uzerinden okuma işlemi yapılacak dosya hazır hale getirildi.
                //Asıl okuma işlemini BufferedReader sınıfı gerçekleştirir.
                
                String line = "";
                
                while ((line = buf.readLine()) != null) {
                	//null'a kadar  buf.readLine() metodu ile dosya içerisindeki satırlar okunur.
                    reader.append(line +"");//reader'a okunan satırlar eklenir.
                }
                res = reader.toString();//eklenen satırlar res değişkenine aktarılarak yazdırılır.
 
            } catch (IOException e) {
                e.printStackTrace();
                return null;
 
            }
            return res;//res'i dondurur.
 
     }
}
