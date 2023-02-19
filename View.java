import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.InputStream;
import javax.swing.*;

public class View {

    private JFrame startFrame, mazeFrame;
    private JLabel mazeBotLbl, welcomeLbl, insLbl;
    private JComboBox sizeBox;
    private JButton startBtn;

    public View(ActionListener startBtnListener) {
        
        // Initialize the start window
        this.startFrame = new JFrame("MazeBot");
        this.startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.startFrame.setLayout(null);
        this.startFrame.setResizable(false);
        this.startFrame.setSize(650,280);
        this.startFrame.getContentPane().setBackground(Color.BLACK);

        // Place MazeBot sprite
        this.mazeBotLbl = new JLabel();
        this.mazeBotLbl.setIcon(new ImageIcon("assets/mazebot.png"));
        this.mazeBotLbl.setBounds(30,20,200,200);
        
        // Place welcome message
        this.welcomeLbl = new JLabel("Welcome, I am MazeBot!");
        this.welcomeLbl.setBounds(260,10,400,100);
        editFont(welcomeLbl,32);

        // Place instructions
        this.insLbl = new JLabel("Please pick the size of your maze:");
        this.insLbl.setBounds(280,40,400,100);
        editFont(insLbl,18);
        
        // Place maze sizes
        Integer[] mazeSize = {4, 6, 8, 10, 20};
        this.sizeBox = new JComboBox<Integer>(mazeSize);
        this.sizeBox.setSelectedIndex(2);
        this.sizeBox.setBounds(340,105,150,50);

        // Place start button
        Icon start = new ImageIcon("assets/start.png");
        this.startBtn = new JButton(start);
        this.startBtn.setBounds(340,160,150,50);
        this.startBtn.addActionListener(startBtnListener);
        
        // Components of the frame
        this.startFrame.add(mazeBotLbl);
        this.startFrame.add(welcomeLbl);
        this.startFrame.add(insLbl);
        this.startFrame.add(sizeBox);
        this.startFrame.add(startBtn);

        this.startFrame.setVisible(true);
    }

    public void MainView() {
        
        // Initialize the maze window
        this.mazeFrame = new JFrame();
        this.mazeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mazeFrame.setLayout(null);
        this.mazeFrame.setResizable(false);
        this.mazeFrame.setSize(800,800);
        this.mazeFrame.getContentPane().setBackground(Color.BLACK);

        // Place maze components
        this.mazeFrame.setVisible(true);
    }

    public void disposeStart() {
        this.startFrame.dispose();
    }

    public void editFont(JLabel labelName, int size) {
        try {
            InputStream is = View.class.getResourceAsStream("assets/Undertale.ttf");
            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            labelName.setForeground(Color.WHITE);
            labelName.setFont(font.deriveFont(Font.PLAIN, size));
        }
        catch(Exception e){}
    }
}