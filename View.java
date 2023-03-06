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
    private JLabel mazeBotLbl, sizeLbl;
    private JPanel mazePanel;
    private JButton actualBtn, optiBtn;
    boolean pathFound = false;

    public View(ActionListener actualBListener, ActionListener optiBListener) {
        
        s.initMaze();
        
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
        this.mazePanel.setBounds(300,30,820,820);

        // Initialize maze blocks
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                JLabel block = makeBlock(s.getBlock(row, col));
                this.mazePanel.add(block);
            }
        }

        // Place maze size
        this.sizeLbl = new JLabel("Size: " + size);
        this.sizeLbl.setBounds(40,-5,400,100);
        editFont(sizeLbl,28);
        
        // Place MazeBot sprite
        this.mazeBotLbl = new JLabel();
        this.mazeBotLbl.setIcon(new ImageIcon("assets/mazebot.png"));
        this.mazeBotLbl.setBounds(50,460,200,200);

        // Initialize explored button
        Icon actual = new ImageIcon("assets/exp1.png");
        this.actualBtn = new JButton(actual);
        this.actualBtn.setBounds(50,705,210,50);
        this.actualBtn.addActionListener(actualBListener);
        
        // Initialize optimal button
        Icon optimal = new ImageIcon("assets/opt1.png");
        this.optiBtn = new JButton(optimal);
        this.optiBtn.setBounds(50,775,210,50);
        this.optiBtn.addActionListener(optiBListener);

        this.mazeFrame.add(sizeLbl);
        this.mazeFrame.add(mazeBotLbl);
        this.mazeFrame.add(actualBtn);
        this.mazeFrame.add(optiBtn);
        this.mazeFrame.add(mazePanel);
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
        String filename = "maze/maze.txt";
        File fileInput = new File(filename);
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

        if (b.isStart) 
            block.setBackground(Color.BLUE);
        
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

        int size = getSize();
       
        // Initialize the maze window
        // Initialize maze blocks
        this.mazeFrame.getContentPane().removeAll();
        this.mazePanel.removeAll();

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Block b = s.getBlock(row, col);

                JLabel block = new JLabel();

                if (b.isStart == true) 
                    block.setBackground(Color.BLUE);
                
                if (b.isGoal == true) 
                    block.setBackground(Color.GREEN);
                
                if (b.isWall == true)
                    block.setBackground(Color.WHITE);

                if (b.isPath == true)
                    block.setBackground(Color.BLACK);

                if (s.getOptimalPath().contains(b)) {
                    block.setBackground(Color.CYAN);
                    block.setText(
                            "<html><span style='font-size:3px'>" +
                                    String.valueOf(s.getOptimalPath().size() - s.getOptimalPath().indexOf(b)) +
                                    "</span></html>");
                    block.setFont(null);
                    block.setPreferredSize(block.getPreferredSize());
                    block.setHorizontalAlignment(JLabel.CENTER);
                }
                block.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                block.setOpaque(true);

                this.mazePanel.add(block);
            }
        }

        optiBtn.setIcon(new ImageIcon("assets/opt2.png"));
        actualBtn.setIcon(new ImageIcon("assets/exp1.png"));
        
        this.mazePanel.repaint();
        this.mazePanel.revalidate();

        this.mazeFrame.add(sizeLbl);
        this.mazeFrame.add(mazeBotLbl);
        this.mazeFrame.add(actualBtn);
        this.mazeFrame.add(optiBtn);
        this.mazeFrame.add(mazePanel);

        this.mazeFrame.repaint();
        this.mazeFrame.revalidate();
        
        String message;
        if (pathFound == true) 
            message = "Path Found!";
        else 
            message = "No path to the goal exists!";

        JOptionPane.showMessageDialog(mazeFrame,
                message + "\nMoves to Goal: "+ s.getOptimalPath().size(),
                "Optimal Path",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void viewExploredPath() {

        int size = getSize();
        
        // Initialize the maze window
        // Initialize maze blocks
        this.mazeFrame.getContentPane().removeAll();
        this.mazePanel.removeAll();

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Block b = s.getBlock(row, col);

                JLabel block = new JLabel();
                
                if (b.isStart == true) 
                    block.setBackground(Color.BLUE);
                
                if (b.isGoal == true) 
                    block.setBackground(Color.GREEN);
                

                if (b.isWall == true)
                    block.setBackground(Color.WHITE);

                if (b.isPath == true)
                    block.setBackground(Color.BLACK);
                
                List<Block> searchPath = new ArrayList<Block>(s.getSearchPath());
                
                if (searchPath.contains(b) && b.isStart == false) {
                    block.setBackground(Color.YELLOW);
                    block.setText(
                        "<html><span style='font-size:3px'>"+
                                    String.valueOf((searchPath.indexOf(b)) +
                                            "</span></html>"));
                    block.setPreferredSize(block.getPreferredSize());
                    block.setHorizontalAlignment(JLabel.CENTER);
                }

                block.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                block.setOpaque(true);

                this.mazePanel.add(block);
            }
        }

        actualBtn.setIcon(new ImageIcon("assets/exp2.png"));
        optiBtn.setIcon(new ImageIcon("assets/opt1.png"));

        this.mazePanel.repaint();
        this.mazePanel.revalidate();

        this.mazeFrame.add(sizeLbl);
        this.mazeFrame.add(mazeBotLbl);
        this.mazeFrame.add(actualBtn);
        this.mazeFrame.add(optiBtn);
        this.mazeFrame.add(mazePanel);

        this.mazeFrame.repaint();
        this.mazeFrame.revalidate();

        List<Block> searchPath = new ArrayList<Block>(s.getSearchPath());
        int totalPath = searchPath.size() - 1;
        
        String message;
        if(pathFound == true) 
            message = "Path Found!";
        else 
            message = "No path to the goal exists!";
        
        JOptionPane.showMessageDialog(mazeFrame,
                message + "\nTotal Number of States Explored: "+ totalPath,
                "Search Path",
                JOptionPane.INFORMATION_MESSAGE);
    }

}
