package parascan10;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileFinder {
    List<String> Files = new ArrayList<String>();
    public List<String> walk( String path ) throws Exception {
        try
        {

            File root = new File( path );
            File[] list = root.listFiles();



            for (File f : list) {
                if (f.isDirectory()) {
                    walk(f.getAbsolutePath());//recurse

                } else {
                    //gets top level
                    //System.out.println("File:" + f.getAbsoluteFile());
                    Files.add(f.getPath());
                }
            }

            return Files;
        }catch (Exception e){
            throw new Exception("Error FileFinder: " + e.getMessage());

        }


    }

}
