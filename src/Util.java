public class Util{
  public void showFilesR(String path) throws Exception{
    FileFinder ff = new FileFinder();
    for(String s : ff.walk("/home/geo/source")){
      System.out.println(s);
    }
  }//end showFilesR

  public void md5hashFilesR(String path) throws Exception{
    FileFinder ff = new FileFinder();
    for(String s : ff.walk("/home/geo/source")){
      System.out.println(MD5Hasher.getMD5Checksum(s));
    }//end for loop

  }//end md5hashFilesR

  public void getFiles(String dbName, String dir) throws Exception{
    int i = 1;
    FileFinder ff = new FileFinder();
    DataAccess da = new DataAccess();
    for(String s : ff.walk(dir)){
      da.loadFilePath(dbName, i, s, MD5Hasher.getMD5Checksum(s));
      i++;
    }//end for loop

  }

}//end Util
