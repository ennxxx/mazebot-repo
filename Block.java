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

