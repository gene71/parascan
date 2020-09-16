package parascan10;
public class ParaMain {

  public static void makeDatabaseFile(String dbName)
  {
    DataAccess da = new DataAccess();
    da.createDatabase(dbName);
  }
  public static void makeFilePathTable(String dbName)
  {
    DataAccess da = new DataAccess();
    da.createFilePathTable(dbName);
    da.createIndicatorTable(dbName);
    da.createFileLineCountsTable(dbName);

  }

  public static void fillFilePathTable(String dbName, String dir) throws Exception
  {
    Util u = new Util();
    u.getFiles(dbName, dir);
    DataAccess da = new DataAccess();
    da.loadFileLineCount(dbName, da.getFilePaths(dbName));
  }

  public static void createFileViews(String dbName){
    DataAccess da = new DataAccess();
    da.createCFilesView(dbName);
    da.createJFilesView(dbName);
    da.createShFilesView(dbName);
    da.createhtmlFilesView(dbName);
    da.createjsFilesView(dbName);
    da.createConfigFilesView(dbName);

  }

  public static void runScan(String dbName){
    ParaScanner ps = new ParaScanner();
    ps.getIndicators(dbName);
  }

//main**********************************************************************//
  public static void main( String args[] ) throws Exception{

    makeDatabaseFile(args[0]);
    makeFilePathTable(args[0]);
    fillFilePathTable(args[0], args[1]);
    createFileViews(args[0]);
    runScan(args[0]);
    System.out.println("end main");

  }//end main**************************************************************//


}//end ParaMain
