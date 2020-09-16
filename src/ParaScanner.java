package parascan10;

import java.sql.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

public class ParaScanner{
  int indicatorFoundId = 1;
  String conString = "jdbc:sqlite:";
  String parameta = "res/parameta_1_0";//rule db
  List<vFilePath> Files = new ArrayList<vFilePath>();
  List<ParaPattern> ParaPatterns = new ArrayList<ParaPattern>();
  List<PIndicator> Indicators = new ArrayList<PIndicator>();
  public void getIndicators(String dbName){

    //load lists for scan
    getvCFiles(dbName);
    getcVPatterns(parameta);
    //scan lists
    scanIndicators();
    //update db
    loadIndicators(dbName);

  }

  void getvCFiles(String dbName){
    // SQLite connection string
    String url = conString;

    // SQL statement for creating a new table
    String sql = "SELECT * FROM v_cfiles;";

    try (Connection conn = DriverManager.getConnection(url + dbName);
            Statement stmt = conn.createStatement()) {
        // execute querry
        ResultSet rs    = stmt.executeQuery(sql);
        // loop through the result set
        while (rs.next()) {
          vFilePath vf = new vFilePath();
          vf.id = rs.getInt("id");
          vf.filePath = rs.getString("filePath");
          Files.add(vf);

        }//end while



    } catch (SQLException e) {
        System.out.println("problem getting v_cfiles " + e.getMessage());
    }//end try v_cfiles

  }//end getvCFiles

  void getcVPatterns(String dbName){
    // SQLite connection string
    String url = conString;

    // SQL statement for creating a new table
    String sql = "SELECT * FROM v_cnpatterns;";

    try (Connection conn = DriverManager.getConnection(url + dbName);
            Statement stmt = conn.createStatement()) {

        // execute querry
        ResultSet rs    = stmt.executeQuery(sql);

        // loop through the result set
        while (rs.next()) {
          ParaPattern p = new ParaPattern();
          p.cepid = rs.getInt("cepid");
          p.pattern = rs.getString("pattern");
          ParaPatterns.add(p);

        }//end while


    } catch (SQLException e) {
        System.out.println("problem getting v_cnpatterns " + e.getMessage());
    }//end try v_cfiles


  }//end getcVPatterns

  void scanIndicators(){

    //check that you got lists setup
    for(vFilePath f : Files){
      //System.out.println(f.id + " " + f.filePath);
      int i = 1;
      BufferedReader reader;
  		try {
  			reader = new BufferedReader(new FileReader(f.filePath));
  			String line = reader.readLine();
  			while (line != null) {

          //System.out.println(line);
          scanLine(line, f, i);

          // read next line
  				line = reader.readLine();
          i++;
  			}//end reading file
  			reader.close();
  		} catch (IOException e) {
  			e.printStackTrace();
  		}//end try read file

    }// end for Files list loop


  }

  void scanLine(String line, vFilePath f, int lineNumber){
    DataAccess da = new DataAccess();

    //check that you got lists setup
    for(ParaPattern p : ParaPatterns){
      //System.out.println(p.pattern + " " + p.cepid);
      // Create a Pattern object
      Pattern r = Pattern.compile(p.pattern);
      // Now create matcher object.
      Matcher m = r.matcher(line);
      if (m.find( )) {
        //make PIndicator
        PIndicator pi = new PIndicator();
        pi.id = indicatorFoundId;
        pi.npatternid = da.getNPatternId(p.pattern, parameta);
        pi.cepid = p.cepid;
        pi.filePathsid = f.id;
        pi.lineNumber = lineNumber;
        pi.line = line;
        Indicators.add(pi);
        indicatorFoundId++;

      }
    }//end ParaPattern loop
  }//end scanLine

  void loadIndicators(String dbName){
    //insert Indicators
    DataAccess da = new DataAccess();
    da.deleteIndicators(dbName);
    //check for indicators first
    System.out.println("scanning");
    for(PIndicator pi : Indicators){

      da.insertIndicator(pi, dbName);
      // System.out.println(pi.id + " " + pi.npatternid + " " + pi.cepid
      // + " " + pi.filePathsid + " " + pi.lineNumber + " " + pi.line);
    }

    //make views
    da.setBufferOverFlowSumView(dbName);
    da.setCommandInjectionView(dbName);
    da.setSensitiveDataExpo(dbName);
    da.setPoorErrorHndl(dbName);

  }





}
