package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OthellowithAI extends JFrame
{
    OtloGameBoard gameBoard=new OtloGameBoard();
    JButton btngobacktomenu = new JButton("Back To Menu");
    JButton btnrestart = new JButton("Restart");
    JLabel bg = new JLabel();

    public OthellowithAI()
    {
        setTitle("Othello 1V1 Vs PC Mode");
        setVisible(true);
        setResizable(false);
        gameBoard.setBounds(40,20,575,575);
        setLayout(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(680,680);
        btngobacktomenu.setBounds(112,600,130,30);
        btnrestart.setBounds(412,600,130,30);
        bg.setBounds(0,0,680,680);
        ImageIcon abackgroundpic = new ImageIcon("woodenbg.jpg");
        Image abg=abackgroundpic.getImage();
        Image bgscale = abg.getScaledInstance(680,680,Image.SCALE_SMOOTH);//resize pic
        ImageIcon bgc=new ImageIcon(bgscale);
        bg.setIcon(bgc);
        add(bg);
        gameBoard.iswithai=true;

        bg.add(btngobacktomenu);
        bg.add(btnrestart);
        bg.add(gameBoard);
        repaint();



        btnrestart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameBoard.restartOtloBoard();
            }
        });
        btngobacktomenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Menu_();
                dispose();
            }
        });


    }
}
