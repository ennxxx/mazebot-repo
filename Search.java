import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.PriorityQueue;

public class Search {

    private View view;
    private final List<Block> blocks = new ArrayList<>();
    private ArrayList<Block> optimalPath = new ArrayList<Block>();

    private Block[][] maze;
    private Block startBlock;
    private Block goalBlock;

    LinkedHashSet searchPath = new LinkedHashSet();
    
    public Search(View view) {
        this.view = view;
    }

    public void initMaze() {
        try {
            // To change size, rename the desired text file
            String fileStr = Files.readString(Path.of("maze/maze.txt"));
            Integer firstLine = fileStr.indexOf('\n', 0);
            fileStr = fileStr.substring(firstLine + 1, fileStr.length());

            // Place characters into an array
            char[] chars = fileStr.toCharArray();
            int x = 0;
            int y = 0;

            // For each character, initialize its respective block
            for (char c : chars) {
                if (c == 'S' || c == 'G' || c == '.' || c == '#' || c == '\n'){
                    if (c == '\n'){
                        y = 0; // Reset y value to 0 for a new line
                        x = x + 1;
                    } else {
                        Block block = new Block(x, y, c);
                        blocks.add(block);
                        // Assign x and y values
                        y = y + 1;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Block getBlock(Integer x, Integer y) {
        for (Block block : blocks) {
            if (block.getX() == x && block.getY() == y) {
                return block;
            }
        }

        return null;
    }

    public boolean search() {

        // Create a 2D block array for easier search
        int mazeSize = (int) Math.sqrt(blocks.size());
        maze = new Block[mazeSize][mazeSize];
        int indexCount = 0;

        for (int i = 0; i < mazeSize; i++) {
            for (int j = 0; j < mazeSize; j++) {
                maze[i][j] = blocks.get(indexCount);
                indexCount++;
            }
        }

        // Find the goal and start blocks
        for (Block b : blocks) {
            if (b.isStart()) 
                startBlock = b;
            else if (b.isGoal()) 
                goalBlock = b;
        }

        // Set the hScore (heuristic), gScore(actual cost), fScore(h + g)
        for (Block b : blocks) {

            b.computeHScore(goalBlock);

            if (b.isStart) {
                b.setGScore(0);
                b.setFScore(b.hScore);
            }
            else { // Set to maximum value
                b.setGScore(Integer.MAX_VALUE);
                b.setFScore(Integer.MAX_VALUE);
            }
        }

        // Prioritize based on fScore then hScore
        PriorityQueue<Block> open = new PriorityQueue<Block>(new ScoreComparator());

        open.add(startBlock);
        searchPath.add(startBlock);
        Block currBlock = null;

        // Loops to the maze until no more open blocks to search
        // OR goal is reached
        int exploredStates = 0;

        while (open.size() != 0 && !isGoalReached(currBlock)) {

            exploredStates++;
            
            // Current block deque from the Priority Q
            currBlock = open.poll();
            searchPath.add(currBlock);

            currBlock.isCurrent = true;
            currBlock.isExplored = true;

            int x = currBlock.getX(); // Row
            int y = currBlock.getY(); // Col

            // For each possible actions on each direction
            // Left -> 0, Right -> 1, Top -> 2, Down -> 3
            for (int i = 0; i < 4; i++) {

                Block childBlock;

                // Left
                if (i == 0 && y != 0 && !maze[x][y - 1].isWall()) 
                    childBlock = maze[x][y - 1];
                // Right
                else if (i == 1 && y != mazeSize - 1 && !maze[x][y + 1].isWall()) 
                    childBlock = maze[x][y + 1];
                // Top
                else if (i == 2 && x != 0 && !maze[x - 1][y].isWall()) 
                    childBlock = maze[x - 1][y];
                // Bottom
                else if (i == 3 && x != mazeSize - 1 && !maze[x + 1][y].isWall()) 
                    childBlock = maze[x + 1][y];
                
                else {
                    childBlock = null;
                }

                if (childBlock != null) {
                    int temp_g_score = currBlock.getGScore() + 1; // New gScore of the Child cell
                    int temp_f_score = temp_g_score + childBlock.getHScore(); // New fScore of the Child cell
                    
                    // If fScore is less than already computed fScore, commit
                    if (temp_f_score < childBlock.getFScore()) { 
                        childBlock.setGScore(temp_g_score);
                        childBlock.setFScore(temp_f_score);
                        childBlock.setParentBlock(currBlock);
                        // Add to priority queue
                        open.add(childBlock); 
                    }
                }
            }

            currBlock.isCurrent = false;
        }

        // If goal not reached
        if (!isGoalReached(currBlock)) {
            System.out.println("No path exists to the goal!");
            return false;
        }
        // Else, a path exists
        else { 
            findOptimalPath();
            return true;
        }
    }

    public boolean isGoalReached(Block currBlock) {
        if (currBlock != null && currBlock.isGoal) 
            return true;
        
        return false;
    }

    // Starting from the goal, trace back the parentBlocks until you reach the start
    public void findOptimalPath() {

        Block b = goalBlock;

        while (b.parentBlock != null) {
            optimalPath.add(b);
            b = b.parentBlock;
        }
    }

    public List<Block> getBlocks() {
        return this.blocks;
    }

    public LinkedHashSet getSearchPath() {
        return searchPath;
    }

    public ArrayList<Block> getOptimalPath() {
        return optimalPath;
    }
}