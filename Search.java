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

    // added
    private Block[][] maze;
    private Block startBlock;
    private Block goalBlock;

    LinkedHashSet searchPath = new LinkedHashSet();
    private ArrayList<Block> optimalPath = new ArrayList<Block>();

    public Search(View view) {
        this.view = view;
    }


    public void initMaze() {
        try {
            // Read file
            String fileStr = Files.readString(Path.of("maze/maze" +  ".txt"));
            Integer firstLine = fileStr.indexOf('\n', 0);
            fileStr = fileStr.substring(firstLine + 1, fileStr.length());

            // Place characters into an array
            char[] chars = fileStr.toCharArray();
            int x = 0;
            int y = 0;

            for (char c : chars) {
                System.out.println("Test");
                if (c == 'S' || c == 'G' || c == '.' || c == '#' || c == '\n'){
                    if (c == '\n'){
                        y = 0; // Reset y value to 0 for a new line
                        x = x + 1;
                    } else {
                        // Initialize a block inside the maze
                        Block block = new Block(x, y, c);

                        // Place blocks in an array
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

    public void search() {

        //Compute the size of the maze
        int mazeSize = (int) Math.sqrt(blocks.size());

        //System.out.println(mazeSize);

        //Transfer blocks to a 2D array for easier search
        maze = new Block[mazeSize][mazeSize];
        int indexCount = 0;

        for (int i = 0; i < mazeSize; i++) {
            for (int j = 0; j < mazeSize; j++) {
                maze[i][j] = blocks.get(indexCount);
                indexCount++;
            }
            //indexCount++;
        }
        System.out.println(maze.length);


        //Finding Start and Goal Block
        for (Block b : blocks) {

            if (b.isStart()) {
                startBlock = b;
            }

            else if (b.isGoal()) {
                goalBlock = b;
            }
        }

        //Setting hScore (heuristic), gScore(actual cost), fScore(h + g)
        for (Block b : blocks) {

            b.computeHScore(goalBlock);

            if (b.isStart) {
                b.setGScore(0);
                b.setFScore(b.hScore);
            }
            else { // setting to max value
                b.setGScore(Integer.MAX_VALUE);
                b.setFScore(Integer.MAX_VALUE);
            }
        }

        //Prioritize based on fScore then hScore
        PriorityQueue<Block> open = new PriorityQueue<Block>(new ScoreComparator());

        open.add(startBlock);
        searchPath.add(startBlock);
        Block currBlock = null;

        //Loops to the maze until no more open blocks to search
        //OR goal is reached
        int exploredStates = 0;

        while (open.size() != 0 && !isGoalReached(currBlock)) {

            exploredStates++;
            //Current block deque from the Priority Q
            currBlock = open.poll();
            searchPath.add(currBlock);
            //TO DO: Update GUI Here
            currBlock.isCurrent = true;
            currBlock.isExplored = true;

            //view.updateMazeFrame();

            /* PRINT STATEMENTS: DELETE AFTER */

            System.out.println("------------------------");
            System.out.println("State [" + exploredStates + "]");
            System.out.println();

            System.out.println("currBlock coordinates: " + currBlock.getX() + " " + currBlock.getY());
            System.out.println("goalBlock coordinates: " + goalBlock.getX() + " " + goalBlock.getY());
            System.out.println();


            System.out.println("gScore: " + currBlock.getGScore());
            System.out.println("hScore: " + currBlock.getHScore());
            System.out.println("fScore: " + currBlock.getFScore());
            System.out.println();

            System.out.println(currBlock);
            printMaze(mazeSize, currBlock);

            /* */

            int x = currBlock.getX(); // Row
            int y = currBlock.getY(); // Col

            // For each possible actions on each direction
            // Left -> 0, Right -> 1, Top -> 2, Down -> 3
            for (int i = 0; i < 4; i++) {

                Block childBlock;

                // Left
                if (i == 0 && y != 0 && !maze[x][y - 1].isWall()) {
                    childBlock = maze[x][y - 1];
                }

                // Right
                else if (i == 1 && y != mazeSize - 1 && !maze[x][y + 1].isWall()) {
                    childBlock = maze[x][y + 1];
                }

                // Top
                else if (i == 2 && x != 0 && !maze[x - 1][y].isWall()) {
                    childBlock = maze[x - 1][y];
                }

                // Bottom
                else if (i == 3 && x != mazeSize - 1 && !maze[x + 1][y].isWall()) {
                    childBlock = maze[x + 1][y];
                }

                else {
                    childBlock = null;
                }

                if (childBlock != null) {
                    int temp_g_score = currBlock.getGScore() + 1; //new gScore of the Child cell
                    int temp_f_score = temp_g_score + childBlock.getHScore(); //new fScore of the Child cell

                    if (temp_f_score < childBlock.getFScore()) { //if fScore is less than already computed fScore, commit
                        childBlock.setGScore(temp_g_score);
                        childBlock.setFScore(temp_f_score);
                        childBlock.setParentBlock(currBlock);

                        open.add(childBlock); //add to priority q
                    }
                }
            }

            currBlock.isCurrent = false;
        }

        //if goal not reached
        if (!isGoalReached(currBlock)) {
            System.out.println("No path exists to the goal!");
        }
        else { //if goal is reached -> there exist a path
            findOptimalPath();
            printOptimalPath(mazeSize);
        }

        System.out.println("Number of explored states: " + exploredStates);
    }

    public boolean isGoalReached(Block currBlock) {

        if (currBlock != null && currBlock.isGoal) {
            return true;
        }

        return false;
    }

    public void printMaze(int mazeNSize, Block currentSearchBlock) {
        for(int i = 0; i < mazeNSize; i++) {
            for(int j = 0; j < mazeNSize; j++) {
                if(currentSearchBlock.getX() == i && currentSearchBlock.getY() == j) {
                    System.out.print("\u001B[31mS\u001B[0m");
                }
                else {
                    if(maze[i][j].isWall) {
                        System.out.print("#");
                    }
                    else if(maze[i][j].isGoal) {
                        System.out.print("\u001B[32mG\u001B[0m");
                    }
                    else {
                        if (maze[i][j].isExplored) {
                            System.out.print("\u001B[34mX\u001B[0m");
                        }
                        else {
                            System.out.print(".");
                        }
                    }
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    // Optimal path printing is simply: Starting from the goalBlock, get their parentBlock
    // until it reached the startBlock (where startBlock.parentBlock = null)

    public void findOptimalPath() {

        Block b = goalBlock;

        while (b.parentBlock != null) {
            optimalPath.add(b);
            b = b.parentBlock;
        }
    }

    public void printOptimalPath(int mazeNSize) {

        System.out.println("------------------------");
        System.out.println("Optimal Path");

        for(int i = 0; i < mazeNSize; i++) {
            for(int j = 0; j < mazeNSize; j++) {

                if (maze[i][j].isStart()) {
                    System.out.print("\u001B[31mS\u001B[0m");
                }
                else if (maze[i][j].isGoal()) {
                    System.out.print("\u001B[32mG\u001B[0m");
                }
                else if (optimalPath.contains(maze[i][j])) {
                    System.out.print("\u001B[35mO\u001B[0m");
                }
                else {
                    if(maze[i][j].isWall) {
                        System.out.print("#");
                    }
                    else {
                        if (maze[i][j].isExplored) {
                            System.out.print("\u001B[34mX\u001B[0m");
                        }
                        else {
                            System.out.print(".");
                        }
                    }
                }
            }
            System.out.println();
        }
        System.out.println();

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