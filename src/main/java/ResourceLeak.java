import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ResourceLeak {


    public void leak() throws Exception {
        File file = new File( "." );
        BufferedReader reader = new BufferedReader( new FileReader( file ) );

        reader.read();
    }

}
