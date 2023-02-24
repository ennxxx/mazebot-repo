package extra;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Array {

	public static void main(String[] args) {
		
        ArrayList<char[]> array = new ArrayList<char[]>();
		char [][] maze = null;
		BufferedReader reader;

		try {
			reader = new BufferedReader(new FileReader("maze/maze4.txt"));
			
            reader.readLine();
			String data;
            
			// Reads file
			while ((data = reader.readLine()) != null) {
                // Converts data into a char array
				array.add(data.toCharArray());
            }
			reader.close();

			// Create a 2D char array
			maze = new char[array.size()][];

			// Convert into 2D array
			for (int i = 0; i < array.size(); i++) {
				maze[i] = (char[])array.get(i);
			}
		
        } catch (IOException e) {
			e.printStackTrace();
		}
	}
}