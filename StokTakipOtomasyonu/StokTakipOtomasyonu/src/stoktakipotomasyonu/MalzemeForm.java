package stoktakipotomasyonu;

import java.awt.Container;
import java.awt.Toolkit;
import java.sql.Connection;  //Veri tabanına bağlantı için
import java.sql.DriverManager; // Veri tabanı sürücüleri listesi
import java.sql.ResultSet; // Sorgudan dönen sonuçlar
import java.sql.SQLException; //Veri tabanı uygulamasında ortaya çıkan hataları ele alır.
import java.sql.Statement; // Sql ifadeleri buradan üretilen nesneler ile yollanır.
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane; // Mesaj paneli kütüphanesi
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class MalzemeForm extends javax.swing.JFrame implements Intrerface2,Interface3 {

    public static Connection baglan;
    private ResultSet res;
    
    //final kullanımı
    static final String JDBCDRIVER = "com.mysql.jdbc.Driver";
    static final String DATABASEURL = "jdbc:mysql://localhost:3306/stoktakip";
    static final String USERNAME = "root";
    static final String PASSWORD = "1234";
    
    //Statik değişken kullanımı
    static int EkranX, EkranY;
    
    Toolkit kit = Toolkit.getDefaultToolkit();
    Container c;

    public MalzemeForm() {
        initComponents();
        baglantiAc();
        listele();
        
        c = this.getContentPane();
        EkranX = (int) kit.getScreenSize().width; //Ekran boyutunun genişliğini alıyoruz...
        EkranY = (int) kit.getScreenSize().height;//Ekran boyutunun yüksekliğini alıyoruz...
        this.setSize(1790, 700); // Pencere boyutunu belirliyoruz...
        this.setLocation((EkranX - 1790) / 2, (EkranY - 700) / 2); // Pencerenin ekranın tam ortasında açılması için pencere yüksekliği ve genişliğinin yarısı kadar geri çekme işlemi uyguluyoruz...
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        setVisible(true);
        
        //Dahili sınıf (Inner Classes) kullanımını örnekleyiniz.
        InnerClass.InnerClass2 myInner=new InnerClass.InnerClass2();
        myInner.gecis("Malzeme");
    }

    public static void baglantiAc() // Veri tabanına bağlantı oluşturuldu
    {
        try {
            Class.forName(JDBCDRIVER);
            String baglanti = DATABASEURL;
            String kullanci = USERNAME;
            String sifre = PASSWORD;
            baglan = DriverManager.getConnection(baglanti, kullanci, sifre);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Veri tabanına bağlantı başarısız !" + e);
        }
    }

    public static void baglantiKapat() //Veri tabanından bağlantı kesildi
    {
        try {
            baglan.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Bağlantı kapatılamadı" + e);
        }
    }

    public void ekle() {

        String ad, kategori;
        int alis, satis, adet, toplam;
        ad = txtAd.getText();
        kategori = txtKategori.getText();
        alis = Integer.parseInt(txtAlis.getText());
        satis = Integer.parseInt(txtSatis.getText());
        adet = Integer.parseInt(txtAdet.getText());
        toplam = (satis - alis) * adet;

        String sqlEkle = "insert into stoktakip.malzeme(ad,kategori,alisFiyat,satisFiyat,adet,toplamKarZarar) values('" + ad + "','" + kategori + "'," + alis + "," + satis + "," + adet + "," + toplam + ")";

        try {
            Statement sorgu = baglan.createStatement();
            sorgu.execute(sqlEkle);
            JOptionPane.showMessageDialog(null, "Kayıt ekleme başarılı.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Kayıt ekleme başarısız.");
            System.out.println(e);
        }

    }

    public void sil() {
        try {
            String sqlSil = "delete from stoktakip.malzeme where id_malzeme=" + txt_sId.getText();
            Statement statement = baglan.createStatement();
            statement.execute(sqlSil);
            JOptionPane.showMessageDialog(null, "Kayıt silme başarılı.");
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, "Kayıt silme başarısız !");
        }
    }

    public void bul() {
        try {

            String sqlBul = "select * from stoktakip.malzeme where id_malzeme=" + txt_bId.getText();
            Statement statement = baglan.createStatement();

            res = statement.executeQuery(sqlBul);
            res.next();

            txt_bAd.setText(res.getString("ad"));
            txt_bKategori.setText(res.getString("kategori"));
            txt_bAlisFiyati.setText(res.getString("alisFiyat"));
            txt_bSatisFiyati.setText(res.getString("satisFiyat"));
            txt_bAdet.setText(res.getString("adet"));

        } catch (Exception e) {
            System.out.println(e.toString());
            JOptionPane.showConfirmDialog(null, e.toString(), "Malzeme tablosu", JOptionPane.PLAIN_MESSAGE);
        }
    }

    public void getir() {
        try {
            String sqlGetir = "select * from stoktakip.malzeme where id_malzeme=" + txt_gId.getText();
            Statement statement = baglan.createStatement();
            res = statement.executeQuery(sqlGetir);
            res.next();

            txt_gAd.setText(res.getString("ad"));
            txt_gKategori.setText(res.getString("kategori"));
            txt_gAlisFiyati.setText(res.getString("alisFiyat"));
            txt_gSatisFiyati.setText(res.getString("satisFiyat"));
            txt_gAdet.setText(res.getString("adet"));
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e.toString(), "malzeme tablosu", JOptionPane.PLAIN_MESSAGE);
        }

    }

    // Güncelleme hatalı düzelt 
    public void guncelle() {
        try {
            String ad, kategori;
            int alisFiyati, satisFiyati, adet,toplam;
            ad = txt_gAd.getText();
            kategori = txt_gKategori.getText();
            alisFiyati = Integer.parseInt(txt_gAlisFiyati.getText());
            satisFiyati = Integer.parseInt(txt_gSatisFiyati.getText());
            adet = Integer.parseInt(txt_gAdet.getText());
            toplam = (satisFiyati - alisFiyati) * adet;

            String sqlGuncelle = "update stoktakip.malzeme set ad='" + txt_gAd.getText() + "', kategori='" + txt_gKategori.getText() + "' , alisFiyat= '" + txt_gAlisFiyati.getText() + "',satisFiyat='" + txt_gSatisFiyati.getText() + "', adet='" + txt_gAdet.getText() + "',toplamKarZarar='"+toplam+"' where id_malzeme='" + txt_gId.getText() + "'";

            Statement statement = baglan.createStatement();
            statement.execute(sqlGuncelle);
            JOptionPane.showConfirmDialog(null, "Güncelleme başarılı.", "malzeme tablosu", JOptionPane.PLAIN_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e.toString(), "malzeme tablosu", JOptionPane.PLAIN_MESSAGE);
            System.out.println(e);
        }
    }

    public ArrayList<Malzeme> mList() {
        ArrayList<Malzeme> malzemeList = new ArrayList<>();
        try {
            String sqlListele = "select * from stoktakip.malzeme";
            Statement statement = baglan.createStatement();
            res = statement.executeQuery(sqlListele);

            Malzeme malzeme;
            while (res.next()) {
                malzeme = new Malzeme(res.getInt("id_malzeme"), res.getString("ad"), res.getString("kategori"), res.getInt("alisFiyat"), res.getInt("satisFiyat"), res.getInt("adet"), res.getInt("toplamKarZarar"));
                malzemeList.add(malzeme);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return malzemeList;
    }

    public void listele() {
        ArrayList<Malzeme> list = mList();
        DefaultTableModel model = (DefaultTableModel) tbl_malzeme.getModel();
        Object[] row = new Object[7];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getid_mazleme();
            row[1] = list.get(i).getad();
            row[2] = list.get(i).getkategori();
            row[3] = list.get(i).getalisFiyat();
            row[4] = list.get(i).getsatisFiyat();
            row[5] = list.get(i).getadet();
            row[6] = list.get(i).gettoplamKarZarar();
            model.addRow(row);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        panelEkle = new javax.swing.JPanel();
        txtSatis = new javax.swing.JTextField();
        txtAdet = new javax.swing.JTextField();
        btnEkle = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtAd = new javax.swing.JTextField();
        txtKategori = new javax.swing.JTextField();
        txtAlis = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        btnBul = new javax.swing.JButton();
        txt_bId = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txt_bAd = new javax.swing.JTextField();
        txt_bKategori = new javax.swing.JTextField();
        txt_bAlisFiyati = new javax.swing.JTextField();
        txt_bSatisFiyati = new javax.swing.JTextField();
        txt_bAdet = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        txt_gAd = new javax.swing.JTextField();
        txt_gKategori = new javax.swing.JTextField();
        txt_gAlisFiyati = new javax.swing.JTextField();
        txt_gSatisFiyati = new javax.swing.JTextField();
        txt_gAdet = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txt_gId = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        btnGuncelle = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        btn_Getir = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        btnSil = new javax.swing.JButton();
        txt_sId = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_malzeme = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(51, 51, 51));

        jPanel4.setBackground(new java.awt.Color(0, 0, 0));
        jPanel4.setPreferredSize(new java.awt.Dimension(1000, 1500));

        panelEkle.setBackground(new java.awt.Color(0, 204, 0));

        txtSatis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSatisActionPerformed(evt);
            }
        });

        btnEkle.setBackground(new java.awt.Color(102, 255, 51));
        btnEkle.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnEkle.setText("Ekle");
        btnEkle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEkleActionPerformed(evt);
            }
        });

        jLabel8.setBackground(new java.awt.Color(102, 255, 0));
        jLabel8.setFont(new java.awt.Font("Tahoma", 3, 26)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("MALZEME EKLE");

        jLabel1.setFont(new java.awt.Font("Tahoma", 3, 13)); // NOI18N
        jLabel1.setText("Ad");

        jLabel2.setFont(new java.awt.Font("Tahoma", 3, 13)); // NOI18N
        jLabel2.setText("Kategori");

        jLabel3.setFont(new java.awt.Font("Tahoma", 3, 13)); // NOI18N
        jLabel3.setText("Alış Fiyat");

        jLabel4.setFont(new java.awt.Font("Tahoma", 3, 13)); // NOI18N
        jLabel4.setText("Satış Fiyat");

        jLabel5.setFont(new java.awt.Font("Tahoma", 3, 13)); // NOI18N
        jLabel5.setText("Adet");

        txtKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKategoriActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelEkleLayout = new javax.swing.GroupLayout(panelEkle);
        panelEkle.setLayout(panelEkleLayout);
        panelEkleLayout.setHorizontalGroup(
            panelEkleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEkleLayout.createSequentialGroup()
                .addContainerGap(52, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
            .addGroup(panelEkleLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(panelEkleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelEkleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEkleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnEkle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtSatis)
                    .addComponent(txtAlis)
                    .addComponent(txtKategori)
                    .addComponent(txtAd)
                    .addComponent(txtAdet, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelEkleLayout.setVerticalGroup(
            panelEkleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEkleLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel8)
                .addGap(26, 26, 26)
                .addGroup(panelEkleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelEkleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(21, 21, 21)
                .addGroup(panelEkleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAlis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(panelEkleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSatis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelEkleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAdet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addComponent(btnEkle)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(0, 102, 255));

        jLabel6.setBackground(new java.awt.Color(255, 255, 51));
        jLabel6.setFont(new java.awt.Font("Tahoma", 3, 26)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("MAZLEME BUL");

        btnBul.setBackground(new java.awt.Color(153, 204, 255));
        btnBul.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnBul.setText("Bul");
        btnBul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBulActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 3, 15)); // NOI18N
        jLabel7.setText("Malzeme Id");

        jLabel9.setFont(new java.awt.Font("Tahoma", 3, 15)); // NOI18N
        jLabel9.setText("Ad");

        jLabel10.setFont(new java.awt.Font("Tahoma", 3, 15)); // NOI18N
        jLabel10.setText("Kategori");

        jLabel11.setFont(new java.awt.Font("Tahoma", 3, 15)); // NOI18N
        jLabel11.setText("Alış Fiyatı");

        jLabel12.setFont(new java.awt.Font("Tahoma", 3, 15)); // NOI18N
        jLabel12.setText("Satış Fiyatı");

        jLabel13.setFont(new java.awt.Font("Tahoma", 3, 15)); // NOI18N
        jLabel13.setText("Adet");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(72, 72, 72)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(13, 13, 13))
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txt_bId, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(27, 27, 27)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnBul)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(160, 160, 160)))
                        .addGap(35, 35, 35))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txt_bAd, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txt_bKategori, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_bAlisFiyati, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_bSatisFiyati, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_bAdet, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(24, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_bId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(btnBul))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_bAd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_bKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_bAlisFiyati, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_bSatisFiyati, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_bAdet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(73, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 102, 0));

        jLabel21.setBackground(new java.awt.Color(255, 255, 51));
        jLabel21.setFont(new java.awt.Font("Tahoma", 3, 26)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("MALZEME GÜNCELLE");

        jLabel22.setFont(new java.awt.Font("Tahoma", 3, 15)); // NOI18N
        jLabel22.setText("Mazleme Id");

        btnGuncelle.setBackground(new java.awt.Color(255, 204, 153));
        btnGuncelle.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnGuncelle.setText("Guncelle");
        btnGuncelle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuncelleActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 3, 15)); // NOI18N
        jLabel16.setText("Ad");

        jLabel17.setFont(new java.awt.Font("Tahoma", 3, 15)); // NOI18N
        jLabel17.setText("Kategori");

        jLabel18.setFont(new java.awt.Font("Tahoma", 3, 15)); // NOI18N
        jLabel18.setText("Alış Fiyatı");

        jLabel19.setFont(new java.awt.Font("Tahoma", 3, 15)); // NOI18N
        jLabel19.setText("Satış Fiyatı");

        jLabel20.setFont(new java.awt.Font("Tahoma", 3, 15)); // NOI18N
        jLabel20.setText("Adet");

        btn_Getir.setBackground(new java.awt.Color(255, 204, 153));
        btn_Getir.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btn_Getir.setText("Getir");
        btn_Getir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_GetirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(69, 69, 69)
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(241, 241, 241))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_gId, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_Getir))
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(48, 48, 48)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(txt_gAd, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_gKategori, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_gAlisFiyati, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnGuncelle))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_gSatisFiyati, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_gAdet, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel21)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_gId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(btn_Getir))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_gAd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_gKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_gAlisFiyati, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_gSatisFiyati, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_gAdet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGuncelle)
                .addGap(18, 18, 18))
        );

        jPanel1.setBackground(new java.awt.Color(255, 0, 102));
        jPanel1.setForeground(new java.awt.Color(255, 51, 0));

        jLabel14.setBackground(new java.awt.Color(255, 255, 51));
        jLabel14.setFont(new java.awt.Font("Tahoma", 3, 26)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("MALZEME SİL");

        btnSil.setBackground(new java.awt.Color(255, 153, 153));
        btnSil.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnSil.setText("Sil");
        btnSil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSilActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 3, 15)); // NOI18N
        jLabel15.setText("Malzeme Id");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_sId)
                .addGap(26, 26, 26)
                .addComponent(btnSil)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_sId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(btnSil))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jLabel23.setFont(new java.awt.Font("Tahoma", 3, 36)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 153, 0));
        jLabel23.setText("MALZEME İŞLEMLERİ");

        jPanel7.setBackground(new java.awt.Color(153, 0, 0));

        jLabel25.setBackground(new java.awt.Color(255, 255, 51));
        jLabel25.setFont(new java.awt.Font("Tahoma", 3, 26)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("MALZEME LİSTESİ");

        tbl_malzeme.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Ürün adı", "Kategori", "Alış Fiyatı", "Satış Fiyatı", "Adet", "Toplam Kazanç"
            }
        ));
        jScrollPane2.setViewportView(tbl_malzeme);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(214, 214, 214)
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 690, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );

        jButton1.setBackground(new java.awt.Color(0, 0, 0));
        jButton1.setFont(new java.awt.Font("Tahoma", 3, 16)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 153, 0));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stoktakipotomasyonu/backicon.png"))); // NOI18N
        jButton1.setMaximumSize(new java.awt.Dimension(65, 65));
        jButton1.setMinimumSize(new java.awt.Dimension(65, 65));
        jButton1.setPreferredSize(new java.awt.Dimension(65, 65));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(492, 492, 492)
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelEkle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(panelEkle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGap(18, 18, 18)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 472, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 1769, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEkleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEkleActionPerformed
        ekle();
        // Tablo verileri silme islemi
        DefaultTableModel model = (DefaultTableModel) tbl_malzeme.getModel();
        model.getDataVector().removeAllElements();
        revalidate();
        listele();
        
        txtAd.setText("");
        txtKategori.setText("");
        txtAlis.setText("");
        txtSatis.setText("");
        txtAdet.setText("");
    }//GEN-LAST:event_btnEkleActionPerformed

    private void txtKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKategoriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKategoriActionPerformed

    private void btnBulActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBulActionPerformed
        bul();
    }//GEN-LAST:event_btnBulActionPerformed

    private void btnGuncelleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuncelleActionPerformed
        guncelle();
        // Tablo verileri silme islemi
        DefaultTableModel model = (DefaultTableModel) tbl_malzeme.getModel();
        model.getDataVector().removeAllElements();
        revalidate();
        listele();
    }//GEN-LAST:event_btnGuncelleActionPerformed

    private void btn_GetirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_GetirActionPerformed
        getir();
    }//GEN-LAST:event_btn_GetirActionPerformed

    private void btnSilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSilActionPerformed
        sil();
        // Tablo verileri silme islemi
        DefaultTableModel model = (DefaultTableModel) tbl_malzeme.getModel();
        model.getDataVector().removeAllElements();
        revalidate();
        listele();
    }//GEN-LAST:event_btnSilActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        LoginForm gecis = null;
        gecis = new LoginForm();
        gecis.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtSatisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSatisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSatisActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MalzemeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MalzemeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MalzemeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MalzemeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MalzemeForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBul;
    private javax.swing.JButton btnEkle;
    private javax.swing.JButton btnGuncelle;
    private javax.swing.JButton btnSil;
    private javax.swing.JButton btn_Getir;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel panelEkle;
    private javax.swing.JTable tbl_malzeme;
    private javax.swing.JTextField txtAd;
    private javax.swing.JTextField txtAdet;
    private javax.swing.JTextField txtAlis;
    private javax.swing.JTextField txtKategori;
    private javax.swing.JTextField txtSatis;
    private javax.swing.JTextField txt_bAd;
    private javax.swing.JTextField txt_bAdet;
    private javax.swing.JTextField txt_bAlisFiyati;
    private javax.swing.JTextField txt_bId;
    private javax.swing.JTextField txt_bKategori;
    private javax.swing.JTextField txt_bSatisFiyati;
    private javax.swing.JTextField txt_gAd;
    private javax.swing.JTextField txt_gAdet;
    private javax.swing.JTextField txt_gAlisFiyati;
    private javax.swing.JTextField txt_gId;
    private javax.swing.JTextField txt_gKategori;
    private javax.swing.JTextField txt_gSatisFiyati;
    private javax.swing.JTextField txt_sId;
    // End of variables declaration//GEN-END:variables

    private void Close() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
