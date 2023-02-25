import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Search {
    
    private Controller c;
    private List<Block> blocks = new ArrayList<>();

    private Block startBlock;
    private Block goalBlock;
    private Block currentSearchBlock;

    ArrayList<Block> searchPath = new ArrayList<>();
    ArrayList<Block> actualPath = new ArrayList<>();
    ArrayList<Block> frontier = new ArrayList<>();

    public void initMaze() {

        try {
            // Read file
            // Replace 8
            String fileStr = Files.readString(Path.of("maze/maze" + 8 + ".txt"));

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

    public Block getBlock(Integer x, Integer y) {

        // Return block according to x and y value
        for(Block block : blocks) {
            if(block.getX() == x && block.getY() == y) {
                return block;
            }
        }

        return null;
    }
}
