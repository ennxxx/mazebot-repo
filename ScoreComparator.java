import java.util.Comparator;

public class ScoreComparator implements Comparator<Block> {

    @Override
    public int compare(Block o1, Block o2) {

        if (o1.getFScore() < o2.getFScore()) {
            return -1; //
        }
        else if (o1.getFScore() > o2.getFScore()) {
            return 1;
        }
        else {

            if (o1.getHScore() < o2.getHScore()) {
                return -1;
            }
            else if (o1.getHScore() > o2.getHScore()) {
                return 1;
            }
            return 0; // no swap
        }
    }
}
