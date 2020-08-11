import java.sql.*;

public class DataAccess{
  String conString = "jdbc:sqlite:";

  public void createDatabase(String dbName){
      Connection c = null;

      try {
         Class.forName("org.sqlite.JDBC");
         c = DriverManager.getConnection(conString + dbName);

      } catch ( Exception e ) {
         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
         System.exit(0);
      }
      System.out.println("Opened database successfully");
  }//end createDatabase()

  public void createIndicatorTable(String dbName){
         // SQLite connection string
         String url = conString;

         // SQL statement for creating a new table
         String sql = "CREATE TABLE IF NOT EXISTS indicators ("
                 + "	id integer PRIMARY KEY,"
                 + "	npatternId integer NOT NULL,"
                 + "	cepId integer NOT NULL,"
                 + "	filePathsId integer NOT NULL,"
                 + "	lineNumer integer NOT NULL,"
                 + "	line text NOT NULL"
                 + ");";

         try (Connection conn = DriverManager.getConnection(url + dbName);
                 Statement stmt = conn.createStatement()) {
             // create a new table
             stmt.execute(sql);
         } catch (SQLException e) {
             System.out.println(e.getMessage());
         }
    }//end createIndicatorTable

    public void createFilePathTable(String dbName){
           // SQLite connection string
           String url = conString;

           // SQL statement for creating a new table
           String sql = "CREATE TABLE IF NOT EXISTS filePaths ("
                   + "	id integer PRIMARY KEY, filePath text NOT NULL,"
                   + " md5hash text);";

           try (Connection conn = DriverManager.getConnection(url + dbName);
                   Statement stmt = conn.createStatement()) {
               // create a new table
               stmt.execute(sql);
           } catch (SQLException e) {
               System.out.println(e.getMessage());
           }
    }//end createFilePathTable

    public void loadFilePath(String dbName, int id, String filePath,
     String md5hash){
       // SQLite connection string
       String url = conString;
       // SQL statement for creating a new table
       String sql = "INSERT into filePaths (id, filePath, md5hash)"
       + " VALUES (" + id + ", '" + filePath + "', '" + md5hash + "');";

       try (Connection conn = DriverManager.getConnection(url + dbName);
               Statement stmt = conn.createStatement()) {
           // create a new table
           stmt.execute(sql);
       } catch (SQLException e) {
         System.out.println(sql);

           System.out.println(e.getMessage());
       }

    }//end loadFilePath

    public void createCFilesView(String dbName){
      // SQLite connection string
      String url = conString;
      // SQL statement for creating a new table
      String sql = "create view v_cfiles as select id, filePath from filePaths fp where"
      + " filePath like '%.h' or"
      + " filePath like '%.H' or"
      + " filePath like '%.c' or"
      + " filePath like '%.cc' or"
      + " filePath like '%.cpp' or"
      + " filePath like '%.C' or"
      + " filePath like '%.CPP' or"
      + " filePath like '%.hpp' or"
      + " filePath like '%.cpp';";

      try (Connection conn = DriverManager.getConnection(url + dbName);
              Statement stmt = conn.createStatement()) {
          // create a new table
          stmt.execute(sql);
      } catch (SQLException e) {
        System.out.println(sql);

          System.out.println(e.getMessage());
      }

    }//end createCFilesView

    public void createJFilesView(String dbName){
      // SQLite connection string
      String url = conString;
      // SQL statement for creating a new table
      String sql = "create view v_jfiles as select id, filePath from filePaths fp where"
      + " filePath like '%.java';";

      try (Connection conn = DriverManager.getConnection(url + dbName);
              Statement stmt = conn.createStatement()) {
          // create a new table
          stmt.execute(sql);
      } catch (SQLException e) {
        System.out.println(sql);

          System.out.println(e.getMessage());
      }

    }//end createJFilesView

    public void createShFilesView(String dbName){
      // SQLite connection string
      String url = conString;
      // SQL statement for creating a new table
      String sql = "create view v_shfiles as select id, filePath from filePaths fp where"
      + " filePath like '%.sh';";

      try (Connection conn = DriverManager.getConnection(url + dbName);
              Statement stmt = conn.createStatement()) {
          // create a new table
          stmt.execute(sql);
      } catch (SQLException e) {
        System.out.println(sql);

          System.out.println(e.getMessage());
      }

    }//end createShFilesView

    public void createjsFilesView(String dbName){
      // SQLite connection string
      String url = conString;
      // SQL statement for creating a new table
      String sql = "create view v_jsfiles as select id, filePath from filePaths fp where"
      + " filePath like '%.js';";

      try (Connection conn = DriverManager.getConnection(url + dbName);
              Statement stmt = conn.createStatement()) {
          // create a new table
          stmt.execute(sql);
      } catch (SQLException e) {
        System.out.println(sql);

          System.out.println(e.getMessage());
      }

    }//end createjsFilesView

    public void createhtmlFilesView(String dbName){
      // SQLite connection string
      String url = conString;
      // SQL statement for creating a new table
      String sql = "create view v_htmlfiles as select id, filePath from filePaths fp where"
      + " filePath like '%.htm' or filePath like '%.html';";

      try (Connection conn = DriverManager.getConnection(url + dbName);
              Statement stmt = conn.createStatement()) {
          // create a new table
          stmt.execute(sql);
      } catch (SQLException e) {
        System.out.println(sql);

          System.out.println(e.getMessage());
      }

    }//end createjsFilesView


    public void createConfigFilesView(String dbName){
      // SQLite connection string
      String url = conString;
      // SQL statement for creating a new table
      String sql = "create view v_configfiles as select id, filePath from filePaths fp where"
      + " filePath like '%.config' or"
      + " filePath like '%.xml' or"
      + " filePath like '%.properties' or"
      + " filePath like '%.txt' or"
      + " filepath like '%.conf';";

      try (Connection conn = DriverManager.getConnection(url + dbName);
              Statement stmt = conn.createStatement()) {
          // create a new table
          stmt.execute(sql);
      } catch (SQLException e) {
        System.out.println(sql);

          System.out.println(e.getMessage());
      }

    }//end createConfigFilesView

    public void deleteIndicators(String dbName){

      // SQLite connection string
      String url = conString;
      // SQL statement for creating a new table
      String sql = "DELETE FROM indicators;";


      try (Connection conn = DriverManager.getConnection(url + dbName);
          Statement stmt = conn.createStatement()) {
          stmt.execute(sql);
      } catch (SQLException e) {
        System.out.println(sql);

          System.out.println(e.getMessage());
      }
    }//end deleteIndicators

    
    public void insertIndicator(PIndicator pi, String dbName){
      // SQLite connection string
      String url = conString;
      // SQL statement for creating a new table
      String sql = "INSERT into indicators (id, npatternid, cepid, filePathsid, lineNumer, line)"
      + " VALUES ("+ pi.id + ", "
      + pi.npatternid + ", " + pi.cepid + ", " + pi.filePathsid
      + ", " + pi.lineNumber + ", '" + pi.line + "');";

      try (Connection conn = DriverManager.getConnection(url + dbName);
          Statement stmt = conn.createStatement()) {
          stmt.execute(sql);
      } catch (SQLException e) {
        System.out.println(sql);

          System.out.println(e.getMessage());
      }
    }//end insertIndicator

    public int getNPatternId(String npattern, String dbName){
      // SQLite connection string
      String url = conString;
      int id = 0;

      // SQL statement for creating a new table
      String sql = "SELECT id FROM npatterns WHERE pattern = '" + npattern + "';";

      try (Connection conn = DriverManager.getConnection(url + dbName);
              Statement stmt = conn.createStatement()) {

          // execute querry
          ResultSet rs    = stmt.executeQuery(sql);

          // loop through the result set
          while (rs.next()) {

            id = rs.getInt("id");


          }//end while


      } catch (SQLException e) {
          System.out.println("problem getting npattern id " + e.getMessage());
      }//end try v_cfiles
      return id;
    }

}//end DataAccess
