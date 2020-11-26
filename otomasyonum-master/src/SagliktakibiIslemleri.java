
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SagliktakibiIslemleri {
    private Connection con = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    
    public ArrayList<Hasta> hastalariGetir() {
    ArrayList<Hasta> cikti1 = new ArrayList<Hasta>();
    
        try {
            statement = con.createStatement();
            String sorgu = "Select * From sagliktakibi";
            
            ResultSet rs = statement.executeQuery(sorgu);
           while(rs.next()) {
               int id = rs.getInt("id");
               String ad = rs.getString("ad");
               String soyad = rs.getString("soyad");
               String tc = rs.getString("tc");
               cikti1.add(new Hasta(id,ad,soyad,tc));
               
           }
           return cikti1; 
            
            
        } catch (SQLException ex) {
            Logger.getLogger(SagliktakibiIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        return null;
        }
    
}
    public void hastaEkle(String ad, String soyad, String tc) {
        String sorgu = "Insert Into sagliktakibi(ad,soyad,tc) VALUES(?,?,?)";
        try {
            preparedStatement =con.prepareStatement(sorgu);
            preparedStatement.setString(1,ad);
            preparedStatement.setString(2,soyad);
            preparedStatement.setString(3,tc);
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SagliktakibiIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void calisanSil( int id) {
     String sorgu = "Delete from sagliktakibi where id = ?";
        try {
            preparedStatement = con.prepareStatement(sorgu);
               preparedStatement.setInt(1,id );
               preparedStatement.executeUpdate(); 
               
        } catch (SQLException ex) {
            Logger.getLogger(SagliktakibiIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
 
    
    }
    public boolean GirisYap1(String kullanici_adi,String parola) {
    String sorgu = "Select * From kullanicigirisi where username = ? and password = ?";
        try {
            preparedStatement = con.prepareStatement(sorgu);
            preparedStatement.setString(1,kullanici_adi);
            preparedStatement.setString(2,parola);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
            
        } catch (SQLException ex) {
            Logger.getLogger(SagliktakibiIslemleri.class.getName()).log(Level.SEVERE, null, ex);
       return false;
        }
        
        
        
        
    }
public SagliktakibiIslemleri() {
    String url = "jdbc:mysql://" + Databese1.host + ":" + Databese1.port + "/" + Databese1.db_ismi;    
    try {
        Class.forName("com.mysql.jdbc.Driver");
    } catch (ClassNotFoundException ex) {
        System.out.println("driver bulunamadı");
    }
    try {   
        con = DriverManager.getConnection(url, Databese1.kullanici_adi, Databese1.parola);
        System.out.println("baglantı basarılı");
    } catch (SQLException ex) {
        System.out.println("baglantı başarısız");
}   
}
}


