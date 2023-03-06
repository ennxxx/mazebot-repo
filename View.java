import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class View {
    private Search s = new Search(this);
    
    private JFrame startFrame, mazeFrame;
    private JLabel mazeBotLbl, welcomeLbl;
    private JPanel mazePanel;
    private JButton startBtn, actualBtn, optiBtn;

    boolean pathFound = false;
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
        this.mazeBotLbl.setIcon(new ImageIcon("assets/mazebot.png"));
        this.mazeBotLbl.setBounds(30,20,200,200);
        
        // Place welcome message
        this.welcomeLbl = new JLabel("Welcome, I am MazeBot!");
        this.welcomeLbl.setBounds(260,40,400,100);
        editFont(welcomeLbl,32);

        // Place start button
        Icon start = new ImageIcon("assets/start.png");
        this.startBtn = new JButton(start);
        this.startBtn.setBounds(340,130,150,50);
        this.startBtn.addActionListener(startBListener);
        this.startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                s.initMaze();
            }
        });
        
        // Components of the frame
        this.startFrame.add(mazeBotLbl);
        this.startFrame.add(welcomeLbl);
        this.startFrame.add(startBtn);

        this.startFrame.setVisible(true);
    }

    public void MazeView(ActionListener actualBListener, ActionListener optiBListener) {
        // Get the size of the maze
        int size = getSize();
        
        // Initialize the maze window
        this.mazeFrame = new JFrame();
        this.mazeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mazeFrame.setLayout(null);
        this.mazeFrame.setResizable(false);
        this.mazeFrame.setSize(1180,900);
        this.mazeFrame.getContentPane().setBackground(Color.BLACK);

        // Initialize maze panel
        this.mazePanel = new JPanel();
        this.mazePanel.setLayout(new GridLayout(size, size, 0,0));
        this.mazePanel.setBounds(50,40,800,800);
        this.mazePanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 10));
        
        // Initialize maze blocks
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                System.out.println(size);
                System.out.print(row);
                System.out.print(col);
                JLabel block = makeBlock(s.getBlock(row, col));
                this.mazePanel.add(block);
            }
        }

        Icon actual = new ImageIcon("assets/exp1.png");
        this.actualBtn = new JButton(actual);
        this.actualBtn.setBounds(900,710,210,50);
        this.actualBtn.addActionListener(actualBListener);
        
        Icon optimal = new ImageIcon("assets/opt1.png");
        this.optiBtn = new JButton(optimal);
        this.optiBtn.setBounds(900,780,210,50);
        this.optiBtn.addActionListener(optiBListener);

        this.mazeFrame.add(mazePanel);
        this.mazeFrame.add(actualBtn);
        this.mazeFrame.add(optiBtn);
        this.mazeFrame.setVisible(true);

        this.pathFound = s.search();
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
        File fileInput = new File("maze/maze.txt");
        Scanner scan = null;
        try {
            scan = new Scanner(fileInput);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        int ret = scan.nextInt();
        scan.close();
        return ret;
    }

    private JLabel makeBlock(Block b) {
        JLabel block = new JLabel();


        if (b.isStart) {
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

    public void viewOptimalPath() {
        // MAZE VIEW
        // Get the size of the maze
        int size = getSize();
        // Initialize the maze window

        // Initialize maze blocks
        this.mazeFrame.getContentPane().removeAll();
        this.mazePanel.removeAll();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Block b = s.getBlock(row, col);

                // MAKE BLOCK
                JLabel block = new JLabel();
                int size1 = 800 / getSize();

                if (b.isStart == true) {
                    block.setBackground(Color.BLUE);
                }

                if (b.isGoal == true) {
                    block.setBackground(Color.GREEN);
                }

                if (b.isWall == true)
                    block.setBackground(Color.WHITE);

                if (b.isPath == true)
                    block.setBackground(Color.BLACK);

                if (s.getOptimalPath().contains(b)) {
                    block.setBackground(Color.CYAN);
                    block.setText(String.valueOf(s.getOptimalPath().size() - s.getOptimalPath().indexOf(b)));
                    block.setHorizontalAlignment(JLabel.CENTER);
                }
                block.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                block.setOpaque(true);

                this.mazePanel.add(block);
            }
            }
            this.mazePanel.repaint();
            this.mazePanel.revalidate();
            this.mazeFrame.add(mazePanel);
            this.mazeFrame.add(actualBtn);
            this.mazeFrame.add(optiBtn);
            this.mazeFrame.repaint();
            this.mazeFrame.revalidate();
            String message;
            if(pathFound == true) {
                message = "Path Found!";
            }
            else {
                message = "No path to the goal exists!";
            }
            JOptionPane.showMessageDialog(mazeFrame,
                    message + "\nMoves to Goal: "+ s.getOptimalPath().size(),
                    "Optimal Path",
                    JOptionPane.INFORMATION_MESSAGE);
    }

    public void viewExploredPath() {
        // Get the size of the maze
        int size = getSize();
        // Initialize the maze window

        // Initialize maze blocks
        this.mazeFrame.getContentPane().removeAll();
        this.mazePanel.removeAll();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Block b = s.getBlock(row, col);

                JLabel block = new JLabel();
                int size1 = 800 / getSize();
                if (b.isStart == true) {
                    block.setBackground(Color.BLUE);
                }

                if (b.isGoal == true) {
                    block.setBackground(Color.GREEN);
                }

                if (b.isWall == true)
                    block.setBackground(Color.WHITE);

                if (b.isPath == true)
                    block.setBackground(Color.BLACK);
                List<Block> searchPath = new ArrayList<Block>(s.getSearchPath());
                if (searchPath.contains(b)) {
                    block.setBackground(Color.YELLOW);
                    block.setText(String.valueOf(searchPath.indexOf(b)));
                    block.setHorizontalAlignment(JLabel.CENTER);
                }
                block.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                block.setOpaque(true);

                this.mazePanel.add(block);
            }
        }
        this.mazePanel.repaint();
        this.mazePanel.revalidate();
        this.mazeFrame.add(mazePanel);
        this.mazeFrame.add(actualBtn);
        this.mazeFrame.add(optiBtn);
        this.mazeFrame.repaint();
        this.mazeFrame.revalidate();
        List<Block> searchPath = new ArrayList<Block>(s.getSearchPath());
        int totalPath = searchPath.size();
        String message;
        if(pathFound = true) {
            message = "Path Found!";
        }
        else {
            message = "No path to the goal exists!";
        }


        JOptionPane.showMessageDialog(mazeFrame,
                message + "\nTotal Number of States Explored: "+ totalPath,
                "Search Path",
                JOptionPane.INFORMATION_MESSAGE);
    }

}
