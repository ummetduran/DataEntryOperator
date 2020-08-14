/**
 * Created by Ümmet on 21.3.2018.
 */

import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.io.FileNotFoundException;


public class Test {
    public static void main(String[]args){
        int meta_say=0;
        Text.listeyeEkle();
        try {
            Scanner meta = new Scanner(new FileReader("meta.txt"));

            while (meta.hasNext()) {

                String dosya_adi;
                dosya_adi = meta.nextLine();
                Text.girdi_al(dosya_adi);
              meta_say++;

            }
        }catch (FileNotFoundException e){
            System.out.println("Dosya bulunamadı.");
        }

        int toplam_kelime=0;
        int top_dogru_kelime=0;
        int top_duzeltilen_kelime=0;
        float muhendis_dogruyazim_toplami=0;
        float top_algo_basariorani=0;
        for (int i=0;i<meta_say;i++){
            toplam_kelime+= (int) Text.sonuc[i][0];
            top_dogru_kelime+=(int) Text.sonuc[i][1];
            top_duzeltilen_kelime+=(int) Text.sonuc[i][2];
            top_algo_basariorani+=(float)Text.sonuc[i][5];
        }
        System.out.println("Okunan tüm metinlerdeki toplam kelime sayısı: "+toplam_kelime);
        System.out.println("Okunan tüm metinlerdeki toplam doğru yazılmış kelime sayısı: "+top_dogru_kelime);
        System.out.println("Okunan tüm metinlerdeki toplam düzeltilen kelime sayısı: "+top_duzeltilen_kelime);


        for (int j=0;j<Text.muhendisArray.size();j++){
            muhendis_dogruyazim_toplami+=(float)Text.muhendisArray.get(j);
        }
        System.out.println("Mühendislik fakültesinde çalışan veri giriş operatörlerinin doğru yazım oranı ortalaması: " +
                new DecimalFormat("##.##").format( muhendis_dogruyazim_toplami/ Text.muhendisArray.size()));
        float tip_hataliyazim_toplami=0;
        for(int k=0;k<Text.tipArray.size();k++){
            tip_hataliyazim_toplami+=(float) Text.tipArray.get(k);
        }
        System.out.println("Tıp fakültesinde çalışan veri giriş operatörlerinin hatalı yazım oranı ortalaması: " +
                new DecimalFormat("##.##").format(tip_hataliyazim_toplami/Text.tipArray.size()));

        System.out.println("Okunan tüm metinler için algoritmanın başarı oranı ortalaması: "+
                new DecimalFormat("##.##").format(top_algo_basariorani/meta_say));


    }
}
