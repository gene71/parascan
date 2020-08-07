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
  }

  public static void fillFilePathTable(String dbName, String dir) throws Exception
  {
    Util u = new Util();
    u.getFiles(dbName, dir);
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

//main**********************************************************************//
  public static void main( String args[] ) throws Exception{

    makeDatabaseFile(args[0]);
    makeFilePathTable(args[0]);
    fillFilePathTable(args[0], args[1]);
    createFileViews(args[0]);
    System.out.println("end main");

  }//end main**************************************************************//


}//end ParaMain
