import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Controller {
    private View view;

    public static void main(String[] args) {
        new Controller();
    }

    public Controller() {
        this.view = new View(new ActionListener() {
            @Override  
            public void actionPerformed(ActionEvent e) {
                showExplored();
            }
        }, new ActionListener() {
            @Override  
            public void actionPerformed(ActionEvent e) {
                showOptimal();
            }
        });
    }

    public void showExplored() {
        this.view.viewExploredPath();
    }

    public void showOptimal() {
        this.view.viewOptimalPath();
    }
}
 