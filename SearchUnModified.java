/*import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

// issues with generate actions

public class Search {

    private Controller c;
    private List<Block> blocks = new ArrayList<>();

    // added
    private Block [][] maze;
    private Block startBlock;
    private Block goalBlock;
    private Block currentSearchBlock;

    ArrayList<Block> searchPath = new ArrayList<>();
    ArrayList<Block> actualPath = new ArrayList<>();
    ArrayList<Block> frontier = new ArrayList<>();

    public void initMaze(int size) {
        try {
            // Read file
            String fileStr = Files.readString(Path.of("maze/maze" + String.valueOf(size) + ".txt"));
            Integer firstLine=fileStr.indexOf('\n',0);
            fileStr = fileStr.substring(firstLine+1, fileStr.length());

            // Place characters into an array
            char[] chars = fileStr.toCharArray();
            int x = 0;
            int y = 0;

            for(char c : chars) {
                // Initialize a block inside the maze
                Block block = new Block(x, y, c);
                // Place blocks in an array
                blocks.add(block);
                // Assign x and y values
                y = y + 1;
                if(c == '\n') {
                    y = 0; // Reset y value to 0 for a new line
                    x = x + 1;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        search();
    }

    public Block getBlock(Integer x, Integer y) {
        for(Block block : blocks) {
            if(block.getX() == x && block.getY() == y) {
                return block;
            }
        }

        return null;
    }

    public void search() {

        int mazeSize = (int) Math.sqrt(blocks.size());

        System.out.println(mazeSize);

        maze = new Block[mazeSize][mazeSize];
        int indexCount = 0;

        //transfer blocks to a 2D array for ez search
        for (int i = 0; i < mazeSize; i++) {
            for (int j = 0; j < mazeSize; j++) {
                maze[i][j] = blocks.get(indexCount);
                indexCount++;
            }
            indexCount++;
        }
        System.out.println(maze.length);


        for (Block b : blocks) {
            //setting the start block
            if (b.isStart) {
                startBlock = b;
            }

            //setting the goal block
            else if (b.isGoal) {
                goalBlock = b;
            }
        }

        //updating the lists
        frontier.add(startBlock);
        searchPath.add(startBlock);
        actualPath.add(startBlock);

        //updating the statuses of startBlock
        updateFScore(startBlock, 0);
        startBlock.setIsExplored(true);
        currentSearchBlock = getLowestFScoreFromFrontier();

        // BEGIN SEARCH
        while(!isEnd()) { // Check for end game conditions

            // Generate all possible actions from currentSearchBlock, update fscore, add to frontier, add to searchPath
            generatePossibleActions();

            // Remove currentSearchBlock from frontier
            frontier.remove(currentSearchBlock);

            // If frontier is empty, maze can not be completed
            if(frontier.size() == 0) {
                System.out.println("Solution cannot be found :(\n");
                return;
            }

            // Update value of currentSearchBlock to be the block in frontier that has the lowest fscore
            currentSearchBlock = getLowestFScoreFromFrontier();
            currentSearchBlock.isExplored = true;

            // Add to actualPath
            actualPath.add(currentSearchBlock);
            printMaze((int)Math.sqrt(blocks.size()));
        }


    }

    Block getLowestFScoreFromFrontier() {
        Block lowest = frontier.get(0);
        for(Block b : frontier) {
            if(b.getFScore() < lowest.getFScore()) {
                lowest = b;
            }
        }

        return lowest;
    }

    void updateFScore(Block b, double gScore) {
        float h  = calculateDistanceBetweenPoints(b.getX(), b.getY(), goalBlock.getX(), goalBlock.getY());
        b.setFScore(h + gScore);
    }

    public float calculateDistanceBetweenPoints(int x1, int y1, int x2, int y2) {
        return (float) Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }

    public void generatePossibleActions() {
        int x = currentSearchBlock.getX(); // Row
        int y = currentSearchBlock.getY(); // Col
        int sizeOfMaze = maze.length;

        // Left
        if(y != 0 && !maze[x][y - 1].isWall() &&  !maze[x][y - 1].isExplored) {
            updateFScore(maze[x][y - 1], currentSearchBlock.getGScore() + 1);
            frontier.add(maze[x][y - 1]);
            searchPath.add(maze[x][y - 1]);
        }
        // Right
        // Replace 2 = (int) Math.sqrt(maze.length) with n size of maze
        if (y != sizeOfMaze && !maze[x][y + 1].isWall() &&  !maze[x][y + 1].isExplored) {
            updateFScore(maze[x][y + 1], currentSearchBlock.getGScore() + 1);
            frontier.add(maze[x][y + 1]);
            searchPath.add(maze[x][y + 1]);
        }

        // Top
        if (x != 0 && !maze[x - 1][y].isWall() &&  !maze[x - 1][y].isExplored) {
            updateFScore(maze[x - 1][y], currentSearchBlock.getGScore() + 1);
            frontier.add(maze[x - 1][y]);
            searchPath.add(maze[x - 1][y]);
        }

        // Bottom
        // Replace 2 = (int) Math.sqrt(maze.length) with n size of maze
        if (x != sizeOfMaze && !maze[x + 1][y].isWall() &&  !maze[x + 1][y].isExplored) {
            updateFScore(maze[x + 1][y], currentSearchBlock.getGScore() + 1);
            frontier.add(maze[x + 1][y]);
            searchPath.add(maze[x + 1][y]);
        }
    }

    public boolean isEnd() {
        return currentSearchBlock == goalBlock || frontier.size() == 0;
    }

    void printMaze(int mazeNSize) {
        for(int i = 0; i < mazeNSize; i++) {
            for(int j = 0; j < mazeNSize; j++) {
                if(currentSearchBlock.getX() == i && currentSearchBlock.getY() == j) {
                    System.out.print("B");
                }
                else {
                    if(maze[i][j].isWall) {
                        System.out.print("#");
                    }
                    else {
                        System.out.print(".");
                    }
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
*/
