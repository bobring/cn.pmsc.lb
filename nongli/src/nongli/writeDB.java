package nongli;

import java.sql.Connection;  
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class writeDB {
	public void getConnectionSqlServer() {  
		  
        String driverName = "com.microsoft.jdbc.sqlserver.SQLServerDriver";  
        String dbURL = "jdbc:sqlserver://10.15.33.56:1433;databasename=HF"; // 1433是端口，"USCSecondhandMarketDB"是数据库名称  
        String userName = "sa"; // 用户名  
        String userPwd = "sa123"; // 密码  
  
        Connection dbConn = null;  
        try {  
  
            Class.forName(driverName).newInstance();  
        } catch (Exception ex) {  
            System.out.println("驱动加载失败");  
            ex.printStackTrace();  
        }  
        try {  
            dbConn = DriverManager.getConnection(dbURL, userName, userPwd);  
            System.out.println("成功连接数据库！");
            String str = "select * from BookMessage";
            PreparedStatement sm = dbConn.prepareStatement(str);
            ResultSet rs = sm.executeQuery();  //执行完的结果赋给  ResultSet
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
  
            try {  
                if (dbConn != null)  
                    dbConn.close();  
            } catch (SQLException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        }  
    }  
  
    public static void main(String[] args) {  
    	writeDB getConn = new writeDB();  
        getConn.getConnectionSqlServer();  
  
    }  

}
