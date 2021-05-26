
package stoktakipotomasyonu;
class Personel {
    
    //Özel (private) tanımlı değişkenlere erişimi örnekleyiniz. (10 puan)
    private int id_personel,maas;
    private String telefon;
    private String ad,soyad,departman;
    
    public Personel(int id_personel, String ad,String soyad,String departman,int maas,String telefon){
        //Kullanılan değişkenlere ilk değer ataması işlemi için yapıcı metot kullanımını örnekleyiniz. 
        this.id_personel=id_personel;
        this.ad=ad;
        this.soyad=soyad;
        this.departman=departman;
        this.maas=maas;
        this.telefon=telefon;
    }
    
    public int getid_personel()
    {
        return id_personel;
    }
    
    public String getad()
    {
        return ad;
    }
    
    public String getsoyad()
    {
        return soyad;
    }
    
    public String getdepartman()
    {
        return departman;
    }
    
    public int getmaas()
    {
        return maas;
    }
    
    public String gettelefon()
    {
        return telefon;
    }
    
      
    
}
