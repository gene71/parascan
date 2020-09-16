package parascan10;

import java.io.BufferedReader;
import java.io.FileReader;

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

  public int getLineCount(String filePath){

    BufferedReader reader;
    int i = 1;
		try {
			reader = new BufferedReader(new FileReader(filePath));
			String line = reader.readLine();
			while (line != null) {
					line = reader.readLine();
          i++;
			}//end while
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

    return i;
  }//end getLineCount

}//end Util
