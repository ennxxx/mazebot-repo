import javax.swing.JLabel;
/*
 * Initializes values of a block.
 */
public class Block {
    
    int x, y;

    boolean isExplored = false;

    boolean isCurrent = false;

    boolean isWall = false;
    boolean isStart = false;
    boolean isGoal = false;
    boolean isPath = false;
    int gScore; // Cost from start to n
    int hScore; // Heuristic score
    int fScore; // g() + h()

    Block parentBlock = null;


    public Block(Integer x, Integer y, char c) {
        this.x = x;
        this.y = y;

        if(c == '#')
            this.isWall = true;

        if(c == 'S')
            this.isStart = true;

        if(c == 'G')
            this.isGoal = true;

        if(c == '.')
            this.isPath = true;
    }

    public int getGScore() {
        return gScore;
    }
    public int getHScore() {
        return hScore;
    }
    public int getFScore() {
        return fScore;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public boolean isWall() {
        return isWall;
    }

    public boolean isStart() {
        return isStart;
    }

    public boolean isGoal() {
        return isGoal;
    }

    public boolean isPath() {
        return isPath;
    }

    public void setGScore(int gScore) {
        this.gScore = gScore;
    }

    public void setFScore(int fScore) {
        this.fScore = fScore;
    }

    public void setParentBlock(Block b) {
        this.parentBlock = b;
    }

    @Override
    public String toString() {
        return "[" + String.valueOf(this.x) +", "+ String.valueOf(this.y) +"]";
    }

    public void computeHScore(Block b) {
        int xVal = Math.abs(this.x - b.getX());
        int yVal = Math.abs(this.y - b.getY());

        this.hScore = xVal + yVal;
    }
}

/*
[0, 15]
[1, 15]
[2, 15]
[3, 15]
[3, 14]
[3, 13]
[2, 13]
[1, 13]
[0, 13]
[0, 12]
[0, 11]
[0, 10]
[0, 9]
[0, 8]
[0, 7]
[1, 7]
[2, 7]
[3, 7]
[4, 7]
[4, 6]
[4, 5]
[5, 5]
[6, 5]
[6, 6]
[6, 7]
[7, 7]
[8, 7]
[8, 8]
[8, 9]
[7, 9]
[6, 9]
[5, 9]
[5, 10]
[5, 11]
[5, 12]
[5, 13]
[5, 14]
[5, 15]
[6, 15]
[7, 15]
[8, 15]
[9, 15]
[10, 15]
[11, 15]
[12, 15]
[13, 15]
[14, 15]
[15, 15]
[15, 14]
[15, 13]
[15, 12]
[15, 11]
[14, 11]
[13, 11]
[13, 12]

[4, 7]
[5, 7]
[6, 7]
[6, 6]
[6, 5]
[6, 4]
[6, 3]
[5, 3]
[4, 3]
[3, 3]
[2, 3]
[1, 3]
[0, 3]
[0, 2]
[0, 1]
 */