package stoktakipotomasyonu;
import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DbHelper {
    //Static kullanımı
    static String username="root";
    static String password="1234";
    static String dbUrl="jdbc:mysql://localhost:3306/stoktakip";
    public Connection getConnection() throws SQLException
    {
        return(Connection) DriverManager.getConnection(dbUrl,username,password);
    }
    public void ShowError(SQLException exception){
        System.out.println("Error:" + exception.getMessage());
        System.out.println("Error Code:" + exception.getErrorCode());
    }
}