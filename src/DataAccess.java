package parascan10;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



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

    public void createFileLineCountsTable(String dbName){
           // SQLite connection string
           String url = conString;

           // SQL statement for creating a new table
           String sql = "CREATE TABLE IF NOT EXISTS fileLineCounts ("
                   + "	id integer PRIMARY KEY, filePathId integer NOT NULL,"
                   + " lineCount integer);";

           try (Connection conn = DriverManager.getConnection(url + dbName);
                   Statement stmt = conn.createStatement()) {
               // create a new table
               stmt.execute(sql);
           } catch (SQLException e) {
               System.out.println(e.getMessage());
           }
    }//end createFileLineCountsTable

    public void loadFileLineCount(String dbName, List<FilePathIns> fps){
      // SQLite connection string
      String url = conString;
      int i = 1;

      try{


          Util u = new Util();
          System.out.println("loading line counts..." + fps.size());


          // loop through the result set
          for(FilePathIns fp : fps) {
            System.out.println(fp.id + " " + fp.filePath);
            loadFileLineCount(dbName, i, fp.id, u.getLineCount(fp.filePath));
            i++;

            }//end while
        } catch (Exception e) {
        System.out.println("problem getting getting line counts " + e.getMessage());
    }//end try

    }//end loadFileLineCount

    public List<FilePathIns> getFilePaths(String dbName){

      List<FilePathIns> fps = new ArrayList<FilePathIns>();
      // SQLite connection string
      String url = conString;
      ResultSet rs = null;
      // SQL statement for creating a new table
      String sql = "SELECT id, filePath FROM filePaths;";
      try(Connection conn = DriverManager.getConnection(url + dbName);
              Statement stmt = conn.createStatement()){
        rs = stmt.executeQuery(sql);
        // loop through the result set
        while (rs.next()) {

          FilePathIns fp = new FilePathIns();
          fp.id = rs.getInt(1);
          fp.filePath = rs.getString(2);
          fps.add(fp);
          //System.out.println(rs.getInt(1) + " " + rs.getString(2));


          }//end while

      }catch(SQLException e){
        System.out.println("issue getting filepaths " + e.getMessage());

      }

      return fps;

    }//end getFilePaths




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

    public void loadFileLineCount(String dbName, int id, int filePathId, int lineCount)
    {
       // SQLite connection string
       String url = conString;
       // SQL statement for creating a new table
       String sql = "INSERT into fileLineCounts (id, filePathId, lineCount)"
       + " VALUES (" + id + ", " + filePathId + ", " + lineCount + ");";

       try (Connection conn = DriverManager.getConnection(url + dbName);
               Statement stmt = conn.createStatement()) {
           // create a new table
           stmt.execute(sql);
       } catch (SQLException e) {
         System.out.println(sql);

           System.out.println(e.getMessage());
       }

    }//end loadFileLineCount

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

    //create buffer overflow createCFilesView
    public void setBufferOverFlowSumView(String dbName){
      //Select * from indicators i where cepId  = 1;
      // SQLite connection string
      String url = conString;
      // SQL statement for creating a new table
      String sql = "create view v_buffOverflows as select * from indicators where"
      + " cepid = " + 1 + ";";

      try (Connection conn = DriverManager.getConnection(url + dbName);
              Statement stmt = conn.createStatement()) {
          // create a new table
          stmt.execute(sql);
      } catch (SQLException e) {
        System.out.println(sql);

          System.out.println(e.getMessage());
      }

    }//end setBufferOverFlowSumView

    public void setCommandInjectionView(String dbName){
      // SQLite connection string
      String url = conString;
      // SQL statement for creating a new table
      String sql = "create view v_commandInjection as select * from indicators where"
      + " cepid = " + 2 + ";";

      try (Connection conn = DriverManager.getConnection(url + dbName);
              Statement stmt = conn.createStatement()) {
          // create a new table
          stmt.execute(sql);
      } catch (SQLException e) {
        System.out.println(sql);

          System.out.println(e.getMessage());
      }

    }//end setCommandInjectionView

    public void setSensitiveDataExpo(String dbName){
      // SQLite connection string
      String url = conString;
      // SQL statement for creating a new table
      String sql = "create view v_sensitiveDataExpo as select * from indicators where"
      + " cepid = " + 3 + ";";

      try (Connection conn = DriverManager.getConnection(url + dbName);
              Statement stmt = conn.createStatement()) {
          // create a new table
          stmt.execute(sql);
      } catch (SQLException e) {
        System.out.println(sql);

          System.out.println(e.getMessage());
      }

    }//end setSensitiveDataExpo

    public void setPoorErrorHndl(String dbName){
      // SQLite connection string
      String url = conString;
      // SQL statement for creating a new table
      String sql = "create view v_poorErrorHndl as select * from indicators where"
      + " cepid = " + 4 + ";";

      try (Connection conn = DriverManager.getConnection(url + dbName);
              Statement stmt = conn.createStatement()) {
          // create a new table
          stmt.execute(sql);
      } catch (SQLException e) {
        System.out.println(sql);

          System.out.println(e.getMessage());
      }

    }//end setPoorErrorHndl


}//end DataAccess
