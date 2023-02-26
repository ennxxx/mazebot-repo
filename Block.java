/*
 * Initializes values of a block.
 */
public class Block {
    
    Integer x, y;
    Boolean isWall = false;
    Boolean isStart = false;
    Boolean isGoal = false;
    Boolean isPath = false;
    Double gScore; // Cost from start to n
    Double hScore; // Heuristic score
    Double fScore; // g() + h()

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

    public Double getGScore() {
        return gScore;
    }

    public void setGScore(Double gScore) {
        this.gScore = gScore;
    }

    public Double getHScore() {
        return hScore;
    }

    public void setHScore(Double hScore) {
        this.hScore = hScore;
    }

    public Double getFScore() {
        return fScore;
    }

    public void setFScore(Double fScore) {
        this.fScore = fScore;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "[" + String.valueOf(this.x) +", "+ String.valueOf(this.y) +"]";
    }
}
