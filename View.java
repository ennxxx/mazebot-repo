import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;

public class View {

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
        this.startBtn.addActionListener(startBListener);
        
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
        int size = Integer.parseInt(sizeBox.getSelectedItem().toString());
        
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
        
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                JLabel block = makeBlock(mazeArray(size, row, col));
                this.mazePanel.add(block);
            }
        }

        this.mazeFrame.add(mazePanel);
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

    private char mazeArray(int size, int row, int col) {

        ArrayList<char[]> array = new ArrayList<char[]>();
		char [][] maze = null;
		BufferedReader reader;

		try {
			reader = new BufferedReader(new FileReader("maze/maze" + size + ".txt"));
			
            reader.readLine();
			String data;
            
			// Reads file
			while ((data = reader.readLine()) != null) {
                // Converts data into a char array
				array.add(data.toCharArray());
            }
			reader.close();

			// Create a 2D char array
			maze = new char[array.size()][];

			// Convert into 2D array
			for (int i = 0; i < array.size(); i++) {
				maze[i] = (char[])array.get(i);
			}
		
        } catch (IOException e) {
			e.printStackTrace();
		}

        return maze[row][col];
    }

    private JLabel makeBlock(char c) {
        
        // Get the size of the maze
        int size = Integer.parseInt(sizeBox.getSelectedItem().toString());
        int sizeImg = 800/size;

        JLabel block = new JLabel();
        switch(c) {
            case 'S': // Start
                ImageIcon mazebotIcon = new ImageIcon("assets/mazebot.png");
                Image mazebot = mazebotIcon.getImage().getScaledInstance(sizeImg, sizeImg, java.awt.Image.SCALE_SMOOTH); 
                mazebotIcon = new ImageIcon(mazebot);
                block.setIcon(mazebotIcon);
                break;
            case 'G': // Goal
                block.setBackground(Color.GREEN);
                break;
            case '.': // Path
                block.setBackground(Color.BLACK);
                break;
            case '#': // Wall
                block.setBackground(Color.WHITE);
                break;
        }
        
        block.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        block.setOpaque(true);
        return block;
    }
}