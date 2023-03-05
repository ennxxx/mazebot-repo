import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;
import java.io.InputStream;

import javax.swing.*;
import java.awt.*;

public class View {
    private Search s = new Search(this);
    
    private JFrame startFrame, mazeFrame;
    private JLabel mazeBotLbl, welcomeLbl, insLbl;
    private JPanel mazePanel;
    private JComboBox sizeBox;
    private JButton startBtn;

    public View(ActionListener startBListener) {
        // Initialize the start window
        this.startFrame = new JFrame("MazeBot");
        this.startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.startFrame.setLayout(null);
        this.startFrame.setResizable(false);
        this.startFrame.setSize(650,280);
        this.startFrame.getContentPane().setBackground(Color.BLACK);

        // Place MazeBot sprite
        this.mazeBotLbl = new JLabel();
        this.mazeBotLbl.setIcon(new ImageIcon("mazebot.png"));
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
        Integer[] mazeSize = {4, 6, 8, 10, 16, 20, 24};
        this.sizeBox = new JComboBox<Integer>(mazeSize);
        this.sizeBox.setSelectedIndex(2);
        this.sizeBox.setBounds(340,105,150,50);

        // Place start button
        Icon start = new ImageIcon("start.png");
        this.startBtn = new JButton(start);
        this.startBtn.setBounds(340,160,150,50);
        this.startBtn.addActionListener(startBListener);
        this.startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                s.initMaze(getSize());
            }
        });
        
        // Components of the frame
        this.startFrame.add(mazeBotLbl);
        this.startFrame.add(welcomeLbl);
        this.startFrame.add(insLbl);
        this.startFrame.add(sizeBox);
        this.startFrame.add(startBtn);

        this.startFrame.setVisible(true);
    }

    public void MazeView() {
        // Get the size of the maze
        int size = getSize();
        
        // Initialize the maze window
        this.mazeFrame = new JFrame();
        this.mazeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mazeFrame.setLayout(null);
        this.mazeFrame.setResizable(false);
        this.mazeFrame.setSize(900,900);
        this.mazeFrame.getContentPane().setBackground(Color.BLACK);

        // Initialize maze panel
        this.mazePanel = new JPanel();
        this.mazePanel.setLayout(new GridLayout(size, size, 0,0));
        this.mazePanel.setBounds(50,40,800,800);
        this.mazePanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 10));
        
        // Initialize maze blocks
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                JLabel block = makeBlock(s.getBlock(row, col));
                this.mazePanel.add(block);
            }
        }

        this.mazeFrame.add(mazePanel);
        this.mazeFrame.setVisible(true);

        s.search();
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

    public int getSize() {
        int size = Integer.parseInt(sizeBox.getSelectedItem().toString());
        return size;
    }

    private JLabel makeBlock(Block b) {
        int size = 800 / getSize();
        JLabel block = new JLabel();


        if (b.isStart) {
            ImageIcon mazebotIcon = new ImageIcon("mazebot.png");
            Image mazebot = mazebotIcon.getImage().getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH);
            mazebotIcon = new ImageIcon(mazebot);
            block.setIcon(mazebotIcon);
            block.setBackground(Color.BLUE);
        }

        if (b.isGoal)
            block.setBackground(Color.GREEN);

        if (b.isWall)
            block.setBackground(Color.WHITE);

        if (b.isPath)
            block.setBackground(Color.BLACK);


        block.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        block.setOpaque(true);
        return block;
    }

    //NOT WORKING
    public void updateMazeFrame() {

        Component[] components = mazeFrame.getContentPane().getComponents();

        int size = getSize();
        int index = 0;

        for (Component component : components) {
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;

                Block b = s.getBlocks().get(index);

                if (b.isCurrent) {
                    ImageIcon mazebotIcon = new ImageIcon("mazebot.png");
                    Image mazebot = mazebotIcon.getImage().getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH);
                    mazebotIcon = new ImageIcon(mazebot);
                    label.setIcon(mazebotIcon);
                }

                if (b.isGoal)
                    label.setBackground(Color.GREEN);

                if (b.isWall)
                    label.setBackground(Color.WHITE);

                if (b.isPath) {
                    if (b.isExplored) {
                        label.setBackground(Color.GRAY);
                    }
                    else {
                        label.setBackground(Color.BLACK);
                    }
                }

                index++;
            }
        }
    }
}