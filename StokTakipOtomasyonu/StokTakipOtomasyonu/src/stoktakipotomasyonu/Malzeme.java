
package stoktakipotomasyonu;
class Malzeme  {  
     //Özel (private) tanımlı değişkenlere erişimi örnekleyiniz. 
    private String ad,kategori;
    private int id_malzeme,alisFiyat,satisFiyat,adet,toplamKarZarar;
    
    public Malzeme(int id_malzeme, String ad,String kategori,int alisFiyat,int satisFiyat,int adet , int toplamKarZarar){
        
        //Kullanılan değişkenlere ilk değer ataması işlemi için yapıcı metot kullanımını örnekleyiniz. 

        this.id_malzeme=id_malzeme;
        this.ad=ad;
        this.kategori=kategori;
        this.alisFiyat=alisFiyat;
        this.satisFiyat=satisFiyat;
        this.adet=adet;
        this.toplamKarZarar=toplamKarZarar;
    }
    
    public int getid_mazleme()
    {
        return id_malzeme;
    }
    
    public String getad()
    {
        return ad;
    }
    
    public String getkategori()
    {
        return kategori;
    }
    
    public int getalisFiyat()
    {
        return alisFiyat;
    }
    
    public int getsatisFiyat()
    {
        return satisFiyat;
    }
    
    public int getadet()
    {
        return adet;
    }
    
    public int gettoplamKarZarar()
    {
        return toplamKarZarar;
    }
}
