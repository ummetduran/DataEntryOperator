/**
 * Created by Ümmet on 21.3.2018.
 */
public class DataEntryOperator {
    private int ID;
    private String adSoyad;
    private String departman;

    public DataEntryOperator(){
        ID=0;
        adSoyad="";
        departman="";
    }
    public DataEntryOperator(int ID,String adSoyad,String departman){
        this.ID=ID;
        this.adSoyad=adSoyad;
        this.departman=departman;
    }

    @Override
    public String toString(){
        return "ID: "+getID()+"\n"+"Ad ve Soyad: "+getAdSoyad()+"\n"+"Departman: "+getDepartman();
    }

    /**
     * @return the ID
     */
    public int getID() {
        return ID;
    }

    /**
     * @param ID the ID to set
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * @return the adSoyad
     */
    public String getAdSoyad() {
        return adSoyad;
    }

    /**
     * @param adSoyad the adSoyad to set
     */
    public void setAdSoyad(String adSoyad) {
        this.adSoyad = adSoyad;
    }

    /**
     * @return the departman
     */
    public String getDepartman() {
        return departman;
    }

    /**
     * @param departman the departman to set
     */
    public void setDepartman(String departman) {
        this.departman = departman;
    }
}
