import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;


/**
 * Created by Ümmet on 21.3.2018.
 */
public class Text {
    private DataEntryOperator yazar;
    private String content;

    public Text() {
        yazar = null;
        content = "";
    }

    public Text(DataEntryOperator yazar, String content) {
        this.yazar = yazar;
        this.content = content;
    }

    /**
     * @return the yazar
     */
    public DataEntryOperator getYazar() {
        return yazar;
    }

    /**
     * @param yazar the yazar to set
     */
    public void setYazar(DataEntryOperator yazar) {
        this.yazar = yazar;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    public static ArrayList<String> sozluk_str = new ArrayList<>();
    public static ArrayList<String> girdi_list = new ArrayList<>();
    public static ArrayList<Text> TextArray = new ArrayList<Text>();
    public static ArrayList<String> wrong_words = new ArrayList<>();
    public static ArrayList<String> düzeltilen_kelime=new ArrayList<>();
    public static ArrayList<Object> muhendisArray=new ArrayList<>();
    public static ArrayList<Object> tipArray=new ArrayList<>();
    public static int metasayac=0;
    public static Object [][] sonuc= new Object [5][6];
    public static void listeyeEkle() {
        try {
            Scanner word = new Scanner(new FileReader("words.txt"));
            String kelime = "";

            while (word.hasNext()) {
                kelime = word.nextLine();
                sozluk_str.add(kelime);

            }
        } catch (FileNotFoundException e) {
            System.out.println("Dosya bulunamadı");
        }
    }

    public static void girdi_al(String dosya_adi) {


        try {
            int i = 0;
            Text text = null;
            düzeltilen_kelime.clear();
            Scanner girdi = new Scanner(new FileReader(dosya_adi));
            while (girdi.hasNext()) {
                i++;
                String satir;
                satir = girdi.nextLine();
                String ayrac = ".,; ";
                StringTokenizer token = new StringTokenizer(satir, ayrac);

                if (i == 2) {

                    text = new Text(DEO_Nesnesi_Olustur(), satir);
                    TextArray.add(text);

                }

                while (token.hasMoreTokens()) {
                    satir = token.nextToken();
                    girdi_list.add(satir);

                }

            }
            girdiYazdir(dosya_adi, text);

            kıyasla();
            String content = "";
            int kelime_say =0;
            for (int a = 4; a < girdi_list.size(); a++) {
                content += girdi_list.get(a) + " ";
                kelime_say++;
            }
            sonuc[metasayac][0]=kelime_say;
            text.setContent(content);

            sonuc[metasayac][1]=girdi_list.size()-4-wrong_words.size();
            sonuc[metasayac][2]=düzeltilen_kelime.size();
            sonuc[metasayac][3]=wrong_words.size()-düzeltilen_kelime.size();
            sonuc[metasayac][4]=(float)(girdi_list.size()-4-wrong_words.size())/(float)kelime_say;
            sonuc[metasayac][5]=(float)(düzeltilen_kelime.size())/(float)( düzeltilen_kelime.size()+(wrong_words.size()-düzeltilen_kelime.size()));
            int hatali_yazim;
            hatali_yazim = kelime_say-(Integer) sonuc[metasayac][1];
            if (DEO_Nesnesi_Olustur().getDepartman().equals("MuhendislikFakultesi")){
                muhendisArray.add(sonuc[metasayac][4]);
            }
            if (DEO_Nesnesi_Olustur().getDepartman().equals("TipFakultesi")){
                tipArray.add((float)hatali_yazim/kelime_say);
            }

            girdi_list.clear();
            wrong_words.clear();
            metasayac++;
        } catch (FileNotFoundException e) {
            System.out.println("Dosya bulunamadı.");
        }
    }

    public static DataEntryOperator DEO_Nesnesi_Olustur() {
        return new DataEntryOperator(Integer.parseInt(girdi_list.get(0)), girdi_list.get(1) + " " + girdi_list.get(2), girdi_list.get(3));

    }

    public static void girdiYazdir(String dosya_adi, Text text) {
        System.out.println("");
        System.out.println(dosya_adi + " dosyası içeriği:");
        System.out.println(DEO_Nesnesi_Olustur());
        System.out.println(text.getContent());
        System.out.println("");
    }


    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static void kıyasla() {
        System.out.println("Hatalı kelimeler");
        System.out.println("-----------------");

        for (int i = 4; i < girdi_list.size(); i++) {
            if (sozluk_str.contains(girdi_list.get(i).toLowerCase()) == false && isInteger(girdi_list.get(i)) == false) {
                System.out.println(girdi_list.get(i));
                wrong_words.add(girdi_list.get(i).toLowerCase());

            }

        }
        misspelling1(wrong_words);
        misspelling2(wrong_words);
        misspelling3(wrong_words);
    }


    public static void misspelling1(ArrayList<String> wrong_words) {

        String word;
        wordloop:
        for (int i = 0; i < wrong_words.size(); i++) {
            word = wrong_words.get(i);
            String temp;
            splitloop:
            for (int j = 0; j < word.length() - 1; j++) {
                String[] split = word.split("");
                temp = split[j];
                split[j] = split[j + 1];
                split[j + 1] = temp;

                if (kontrolEt(i,word,kelimeUret(word,split))==true) {
                    break splitloop;
                }

                }
            }
        }


    public static void misspelling2(ArrayList<String> wrong_words) {
        String word;
        for (int i = 0; i < wrong_words.size(); i++) {
            word = wrong_words.get(i);
            splitloop:
            for (int j = 0; j < word.length() - 1; j++) {
                String[] split = word.split("");
                if (split[j].equalsIgnoreCase(split[j + 1])) {
                    split[j] = "";

                    kontrolEt(i,word,kelimeUret(word,split));
                    break splitloop;


                }
            }
        }
    }

    public static boolean kontrolEt(int i,String word,String new_word) {

        if (sozluk_str.contains(new_word) == true) {
            System.out.println("Hatalı yazılan kelime: " + word);
            System.out.println("Kelimenin doğru yazımı: " + new_word);
            girdi_list.add(girdi_list.indexOf(wrong_words.get(i)), new_word);
            girdi_list.remove(wrong_words.get(i));

            düzeltilen_kelime.add(new_word);


            return true;
        } else {
            new_word="";
            return false;
        }
    }
    public static final String[][] keyboard = new String[][]{
            {"q", "w", "e", "r", "t", "y", "u", "ı", "o", "p", "ğ", "ü"},
            {"a", "s", "d", "f", "g", "h", "j", "k", "l", "ş", "i"},
            {"z", "x", "c", "v", "b", "n", "m", "ö", "ç"}
    };

    public static String kelimeUret(String word,String[] split){
        String new_word="";
        for (int k = 0; k < word.length(); k++) {
            new_word += "" + split[k];
        }
        return  new_word;
    }

    public static void misspelling3(ArrayList<String> wrong_words) {

        String word;
        for (int i = 0; i < wrong_words.size(); i++) {         //kelime
            word = wrong_words.get(i);
            splitloop:
            for (int j = 0; j < word.length(); j++) {           //harf
                String[] split = word.split("");
                dikeyloop:
                for (int t = 0; t < 3; t++) {  //dikey
                    yatayloop:
                    for (int y=0;y<keyboard[t].length;y++){     //yatay
                     if (split[j].equalsIgnoreCase(keyboard[t][y])) {
                         if (y == 0) {
                             split[j] = keyboard[t][y + 1];

                            if (kontrolEt(i,word,kelimeUret(word,split))==true) {
                                break splitloop;
                            }else {
                                split[j]=keyboard[t][y];
                                break dikeyloop;
                            }

                         }else if (y==keyboard[t].length-1){
                             split[j]=keyboard[t][y-1];
                             if (kontrolEt(i,word,kelimeUret(word,split))==true){
                                 break splitloop;
                             }else {
                                 split[j] = keyboard[t][y];
                                 break dikeyloop;
                             }
                         }else {
                             split[j]=keyboard[t][y+1];
                             if (kontrolEt(i,word,kelimeUret(word,split))==true){
                                 break splitloop;
                             }else {
                                 split[j] = keyboard[t][y];

                             }
                             split[j]=keyboard[t][y-1];
                             if (kontrolEt(i,word,kelimeUret(word,split))==true){
                                 break splitloop;
                             }else {
                                 split[j] = keyboard[t][y];
                                 break dikeyloop;
                             }
                         }
                     }
                    }
                }
            }
        }
    }
}

