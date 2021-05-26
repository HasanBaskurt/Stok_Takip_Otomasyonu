package stoktakipotomasyonu;

import javax.swing.JOptionPane;

//Dahili sınıf (Inner Classes) kullanımını örnekleyiniz.
public class InnerClass {
    static class InnerClass2{
        public void gecis(String deger)
        {
            JOptionPane.showMessageDialog(null, deger+" işlemlerine geçiş yapıldı.");
        }
    }
}
