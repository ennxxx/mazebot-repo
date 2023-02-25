import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;

public class Trial {
    
    private static List<Block> blocks = new ArrayList<>();

    public static void main(String[] args) {   

        int x = 0, y = 0;
        
        initMaze();
        System.out.println(getBlock(x,y).isGoal);
    }

    private static void initMaze() {
        
        try {
            // Read file
            String fileStr = Files.readString(Path.of("maze/maze10.txt"));

            // Ignore the first line
            Integer firstLine=fileStr.indexOf('\n',0);
            fileStr = fileStr.substring(firstLine+1, fileStr.length());
            
            // Place characters into an array
            char[] chars = fileStr.toCharArray();
            int x = 0;
            int y = 0;

            // Read characters and assign x and y values
            for(char c : chars) {

                // Initialize a block inside the maze
                Block block = new Block(x, y, c);
                
                // Place blocks in an array
                blocks.add(block);

                x = x + 1;

                if(c == '\n') {
                    x = 0; // Reset x value to 0 for a new line
                    y = y + 1;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Block getBlock(Integer x,Integer y) {

        // Return block according to x and y value
        for(Block block : blocks) {
            if(block.getX().equals(x) && block.getY().equals(y)) {
                return block;
            }
        }

        return null;
    }

}
