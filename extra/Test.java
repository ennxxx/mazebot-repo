package extra;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Test {

	public static void main(String[] args) {
		
        BufferedReader reader;

		try {
			reader = new BufferedReader(new FileReader("maze/maze10.txt"));
			
            reader.readLine();
            int c = 0;
            while (((c = reader.read()) != -1)) {
                char block = (char) c;
                if (block != '\n') {
					System.out.println(block);
				}
            }
			reader.close();
		
        } catch (IOException e) {
			e.printStackTrace();
		}
	}

}