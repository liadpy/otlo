package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;


public class Menu_ extends JFrame
{
    JButton goto1v1 = new JButton("1V1");
    JButton goto1v1withAI = new JButton("1V1 Against PC");
    JButton gotoonline = new JButton("Online");

    public Menu_()
    {
        setVisible(true);
        setTitle("Menu");
        setLayout(null);
        setSize(680,680);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        goto1v1.setBounds(240 , 140,200,60);
        goto1v1withAI.setBounds(240 , 250,200,60);
        gotoonline.setBounds(240 , 370,200,60);
        ImageIcon backgroundpic = new ImageIcon("unknown.png");
        Image bg=backgroundpic.getImage();
        Image bgscale = bg.getScaledInstance(680,680,Image.SCALE_SMOOTH);//resize pic
        ImageIcon bgc=new ImageIcon(bgscale);
        JLabel bglbl = new JLabel();
        bglbl.setBounds(0,0,680,680);
        bglbl.setIcon(bgc);
        add(bglbl);

        bglbl.add(goto1v1);
        bglbl.add(goto1v1withAI);
        bglbl.add(gotoonline);
        repaint();
        goto1v1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Othello1v1();
                dispose();//close frame
            }
        });
        goto1v1withAI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OthellowithAI();
                dispose();
            }
        });
        //add online
    }
}
