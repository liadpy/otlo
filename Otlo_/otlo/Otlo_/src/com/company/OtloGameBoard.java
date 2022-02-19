package com.company;

import jdk.swing.interop.SwingInterOpUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class OtloGameBoard extends JPanel implements ActionListener {
    public static final ImageIcon WHITEDISK = new ImageIcon("img.png");//more comftarble
    public static final ImageIcon BLACKDISK = new ImageIcon("img_1.png");

     int c=4;//c%2==0 for black, c%2==1 for white
     JButton btns[][] = new JButton[8][8];
     boolean iswithai;

    public OtloGameBoard() {
        setLayout(new GridLayout(8, 8));
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                UIManager.put("Button.select",new Color(45, 134, 77));//change press color
               // btns[i][j].setContentAreaFilled(false);
                btns[i][j] = new JButton();
                btns[i][j].setBackground(new Color(45, 127, 77));
                add(btns[i][j]);
                btns[i][j].addActionListener(this);
                btns[i][j].setBorder(BorderFactory.createBevelBorder(1,Color.black,Color.black));//creating better looking btn borders
            }
        btns[3][3].setIcon(BLACKDISK);
        btns[3][4].setIcon(WHITEDISK);
        btns[4][3].setIcon(WHITEDISK);
        btns[4][4].setIcon(BLACKDISK);
        setBackground(Color.BLACK);

    }
    public void restartOtloBoard()
    {
        for(int i=0;i<8;i++)
            for(int j=0;j<8;j++)
                btns[i][j].setIcon(null);
            c=4;
        btns[3][3].setIcon(BLACKDISK);
        btns[3][4].setIcon(WHITEDISK);
        btns[4][3].setIcon(WHITEDISK);
        btns[4][4].setIcon(BLACKDISK);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (btns[i][j] == e.getSource()) {
                    if (btns[i][j].getIcon() == null) {
                        int c_=c;
                        if (c%2==0) {                                       //black
                            btns[i][j].setIcon(BLACKDISK);
                            c_+=1;
                            System.out.println("black");
                            int ix = i, jx = j;//vetical right check
                            if (j < 6 && btns[i][j + 1].getIcon() == WHITEDISK)
                                jx++;
                            while (jx < 7 && btns[i][jx].getIcon() == WHITEDISK)
                                jx++;

                            if (btns[i][jx].getIcon() == BLACKDISK) {
                                while (jx != j) {
                                    btns[i][jx].setIcon(BLACKDISK);
                                    jx--;
                                }
                            }
                            ix = i;
                            jx = j;//vetical left check
                            if (j > 1 && btns[i][j - 1].getIcon() == WHITEDISK)
                                jx--;
                            while (jx > 0 && btns[i][jx].getIcon() == WHITEDISK)
                                jx--;
                            if (btns[i][jx].getIcon() == BLACKDISK) {
                                while (jx != j) {
                                    btns[i][jx].setIcon(BLACKDISK);
                                    jx++;
                                }
                            }
                            ix = i;
                            jx = j;//horizontal down check
                            if (i < 6 && btns[i + 1][j].getIcon() == WHITEDISK)
                                ix++;
                            while (ix < 7 && btns[ix][j].getIcon() == WHITEDISK)
                                ix++;
                            if (btns[ix][j].getIcon() == BLACKDISK) {
                                while (ix != i) {
                                    btns[ix][j].setIcon(BLACKDISK);
                                    ix--;
                                }
                            }
                            ix = i;
                            jx = j;//horizontal up check
                            if (i > 1 && btns[i - 1][j].getIcon() == WHITEDISK)
                                ix--;
                            while (ix > 0 && btns[ix][j].getIcon() == WHITEDISK)
                                ix--;
                            if (btns[ix][j].getIcon() == BLACKDISK) {
                                while (ix != i) {
                                    btns[ix][j].setIcon(BLACKDISK);
                                    ix++;
                                }
                            }
                            ix = i;
                            jx = j;//cross up right check
                            if (i > 1 && j < 6 && btns[i - 1][j + 1].getIcon() == WHITEDISK) {
                                ix--;
                                jx++;
                            }
                            while (jx < 7 && ix > 0 && btns[ix][jx].getIcon() == WHITEDISK) {
                                ix--;
                                jx++;
                            }
                            if (btns[ix][jx].getIcon() == BLACKDISK) {
                                while (ix != i && jx != j) {
                                    btns[ix][jx].setIcon(BLACKDISK);
                                    ix++;
                                    jx--;
                                }
                            }
                            ix = i;
                            jx = j;//cross down left check
                            if (i < 6 && j > 1 && btns[i + 1][j - 1].getIcon() == WHITEDISK) {
                                ix++;
                                jx--;
                            }
                            while (ix < 7 && jx > 0 && btns[ix][jx].getIcon() == WHITEDISK) {
                                ix++;
                                jx--;
                            }
                            if (btns[ix][jx].getIcon() == BLACKDISK) {
                                while (ix != i && jx != j) {
                                    btns[ix][jx].setIcon(BLACKDISK);
                                    ix--;
                                    jx++;
                                }
                            }
                            ix = i;
                            jx = j;//cross down right check
                            if (i < 6 && j < 6 && btns[i + 1][j + 1].getIcon() == WHITEDISK) {
                                ix++;
                                jx++;
                            }
                            while (ix < 7 && jx < 7 && btns[ix][jx].getIcon() == WHITEDISK) {
                                ix++;
                                jx++;
                            }
                            if (btns[ix][jx].getIcon() == BLACKDISK) {
                                while (ix != i && jx != j) {
                                    btns[ix][jx].setIcon(BLACKDISK);
                                    ix--;
                                    jx--;
                                }
                            }
                            ix = i;
                            jx = j;//cross up left check
                            if (i > 1 && j > 1 && btns[i - 1][j - 1].getIcon() == WHITEDISK) {
                                ix--;
                                jx--;
                            }
                            while (ix > 0 && jx > 0 && btns[ix][jx].getIcon() == WHITEDISK) {
                                ix--;
                                jx--;
                            }
                            if (btns[ix][jx].getIcon() == BLACKDISK) {
                                while (ix != i && jx != j) {
                                    btns[ix][jx].setIcon(BLACKDISK);
                                    ix++;
                                    jx++;
                                }
                            }
                        }
                        if(iswithai==false) {
                            if (c % 2 == 1) {                                           //white
                                System.out.println("white");
                                btns[i][j].setIcon(WHITEDISK);
                                c_ += 1;
                                int ix = i, jx = j;//vetical right check
                                if (j < 6 && btns[i][j + 1].getIcon() == BLACKDISK)
                                    jx++;
                                while (jx < 7 && btns[i][jx].getIcon() == BLACKDISK)
                                    jx++;

                                if (btns[i][jx].getIcon() == WHITEDISK) {
                                    while (jx != j) {
                                        btns[i][jx].setIcon(WHITEDISK);
                                        jx--;
                                    }
                                }
                                ix = i;
                                jx = j;//vetical left check
                                if (j > 1 && btns[i][j - 1].getIcon() == BLACKDISK)
                                    jx--;
                                while (jx > 0 && btns[i][jx].getIcon() == BLACKDISK)
                                    jx--;
                                if (btns[i][jx].getIcon() == WHITEDISK) {
                                    while (jx != j) {
                                        btns[i][jx].setIcon(WHITEDISK);
                                        jx++;
                                    }
                                }
                                ix = i;
                                jx = j;//horizontal down check
                                if (i < 6 && btns[i + 1][j].getIcon() == BLACKDISK)
                                    ix++;
                                while (ix < 7 && btns[ix][j].getIcon() == BLACKDISK)
                                    ix++;
                                if (btns[ix][j].getIcon() == WHITEDISK) {
                                    while (ix != i) {
                                        btns[ix][j].setIcon(WHITEDISK);
                                        ix--;
                                    }
                                }
                                ix = i;
                                jx = j;//horizontal up check
                                if (i > 1 && btns[i - 1][j].getIcon() == BLACKDISK)
                                    ix--;
                                while (ix > 0 && btns[ix][j].getIcon() == BLACKDISK)
                                    ix--;
                                if (btns[ix][j].getIcon() == WHITEDISK) {
                                    while (ix != i) {
                                        btns[ix][j].setIcon(WHITEDISK);
                                        ix++;
                                    }
                                }
                                ix = i;
                                jx = j;//cross up right check
                                if (i > 1 && j < 6 && btns[i - 1][j + 1].getIcon() == BLACKDISK) {
                                    ix--;
                                    jx++;
                                }
                                while (jx < 7 && ix > 0 && btns[ix][jx].getIcon() == BLACKDISK) {
                                    ix--;
                                    jx++;
                                }
                                if (btns[ix][jx].getIcon() == WHITEDISK) {
                                    while (ix != i && jx != j) {
                                        btns[ix][jx].setIcon(WHITEDISK);
                                        ix++;
                                        jx--;
                                    }
                                }
                                ix = i;
                                jx = j;//cross down left check
                                if (i < 6 && j > 1 && btns[i + 1][j - 1].getIcon() == BLACKDISK) {
                                    ix++;
                                    jx--;
                                }
                                while (ix < 7 && jx > 0 && btns[ix][jx].getIcon() == BLACKDISK) {
                                    ix++;
                                    jx--;
                                }
                                if (btns[ix][jx].getIcon() == WHITEDISK) {
                                    while (ix != i && jx != j) {
                                        btns[ix][jx].setIcon(WHITEDISK);
                                        ix--;
                                        jx++;
                                    }
                                }
                                ix = i;
                                jx = j;//cross down right check
                                if (i < 6 && j < 6 && btns[i + 1][j + 1].getIcon() == BLACKDISK) {
                                    ix++;
                                    jx++;
                                }
                                while (ix < 7 && jx < 7 && btns[ix][jx].getIcon() == BLACKDISK) {
                                    ix++;
                                    jx++;
                                }
                                if (btns[ix][jx].getIcon() == WHITEDISK) {
                                    while (ix != i && jx != j) {
                                        btns[ix][jx].setIcon(WHITEDISK);
                                        ix--;
                                        jx--;
                                    }
                                }
                                ix = i;
                                jx = j;//cross up left check
                                if (i > 1 && j > 1 && btns[i - 1][j - 1].getIcon() == BLACKDISK) {
                                    ix--;
                                    jx--;
                                }
                                while (ix > 0 && jx > 0 && btns[ix][jx].getIcon() == BLACKDISK) {
                                    ix--;
                                    jx--;
                                }
                                if (btns[ix][jx].getIcon() == WHITEDISK) {
                                    while (ix != i && jx != j) {
                                        btns[ix][jx].setIcon(WHITEDISK);
                                        ix++;
                                        jx++;
                                    }
                                }
                            }
                        }
                        else
                        {
                            //ai
                            Move move=new Move();
                          move =FindNextBestMoveAlphaBeta.findBestMove(this);
                          int ii= move.row;
                          int jj= move.col;
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                          btns[ii][jj].setIcon(WHITEDISK);

                            int ix = ii, jx = jj;//vetical right check
                            if (jj < 6 && btns[ii][jj + 1].getIcon() == BLACKDISK)
                                jx++;
                            while (jx < 7 && btns[ii][jx].getIcon() == BLACKDISK)
                                jx++;

                            if (btns[ii][jx].getIcon() == WHITEDISK) {
                                while (jx != jj) {
                                    btns[ii][jx].setIcon(WHITEDISK);
                                    jx--;
                                }
                            }
                            ix = ii;
                            jx = jj;//vetical left check
                            if (jj > 1 && btns[ii][jj - 1].getIcon() == BLACKDISK)
                                jx--;
                            while (jx > 0 && btns[ii][jx].getIcon() == BLACKDISK)
                                jx--;
                            if (btns[ii][jx].getIcon() == WHITEDISK) {
                                while (jx != jj) {
                                    btns[ii][jx].setIcon(WHITEDISK);
                                    jx++;
                                }
                            }
                            ix = ii;
                            jx = jj;//horizontal down check
                            if (ii < 6 && btns[ii + 1][jj].getIcon() == BLACKDISK)
                                ix++;
                            while (ix < 7 && btns[ix][jj].getIcon() == BLACKDISK)
                                ix++;
                            if (btns[ix][jj].getIcon() == WHITEDISK) {
                                while (ix != ii) {
                                    btns[ix][jj].setIcon(WHITEDISK);
                                    ix--;
                                }
                            }
                            ix = ii;
                            jx = jj;//horizontal up check
                            if (ii > 1 && btns[ii - 1][jj].getIcon() == BLACKDISK)
                                ix--;
                            while (ix > 0 && btns[ix][jj].getIcon() == BLACKDISK)
                                ix--;
                            if (btns[ix][jj].getIcon() == WHITEDISK) {
                                while (ix != ii) {
                                    btns[ix][jj].setIcon(WHITEDISK);
                                    ix++;
                                }
                            }
                            ix = ii;
                            jx = jj;//cross up right check
                            if (ii > 1 && jj < 6 && btns[ii - 1][jj + 1].getIcon() == BLACKDISK) {
                                ix--;
                                jx++;
                            }
                            while (jx < 7 && ix > 0 && btns[ix][jx].getIcon() == BLACKDISK) {
                                ix--;
                                jx++;
                            }
                            if (btns[ix][jx].getIcon() == WHITEDISK) {
                                while (ix != ii && jx != jj) {
                                    btns[ix][jx].setIcon(WHITEDISK);
                                    ix++;
                                    jx--;
                                }
                            }
                            ix = ii;
                            jx = jj;//cross down left check
                            if (ii < 6 && jj > 1 && btns[ii + 1][jj - 1].getIcon() == BLACKDISK) {
                                ix++;
                                jx--;
                            }
                            while (ix < 7 && jx > 0 && btns[ix][jx].getIcon() == BLACKDISK) {
                                ix++;
                                jx--;
                            }
                            if (btns[ix][jx].getIcon() == WHITEDISK) {
                                while (ix != ii && jx != jj) {
                                    btns[ix][jx].setIcon(WHITEDISK);
                                    ix--;
                                    jx++;
                                }
                            }
                            ix = ii;
                            jx = jj;//cross down right check
                            if (ii < 6 && jj < 6 && btns[ii + 1][jj + 1].getIcon() == BLACKDISK) {
                                ix++;
                                jx++;
                            }
                            while (ix < 7 && jx < 7 && btns[ix][jx].getIcon() == BLACKDISK) {
                                ix++;
                                jx++;
                            }
                            if (btns[ix][jx].getIcon() == WHITEDISK) {
                                while (ix != ii && jx != jj) {
                                    btns[ix][jx].setIcon(WHITEDISK);
                                    ix--;
                                    jx--;
                                }
                            }
                            ix = ii;
                            jx = jj;//cross up left check
                            if (ii > 1 && jj > 1 && btns[ii - 1][jj - 1].getIcon() == BLACKDISK) {
                                ix--;
                                jx--;
                            }
                            while (ix > 0 && jx > 0 && btns[ix][jx].getIcon() == BLACKDISK) {
                                ix--;
                                jx--;
                            }
                            if (btns[ix][jx].getIcon() == WHITEDISK) {
                                while (ix != ii && jx != jj) {
                                    btns[ix][jx].setIcon(WHITEDISK);
                                    ix++;
                                    jx++;
                                }
                            }
                            c_+=1;
                        }
                            c=c_;
                        }
                        if(c==64) {
                            int cb=0,cw=0;
                            for (int ie = 0; ie < 8; ie++)
                                for (int je = 0; je < 8; je++) {
                                    if(btns[ie][je].getIcon()==BLACKDISK)
                                        cb++;
                                    else cw++;
                                }
                            String winner;
                                if(cw==cb)
                                    JOptionPane.showMessageDialog(null,"draw  : \n you may restart or go back to menu to continue  :-)","1v1",JOptionPane.PLAIN_MESSAGE);
                                if (cb > cw)
                                    winner = "Black";
                                else winner = "White";
                                JOptionPane.showMessageDialog(null, winner + "  won  by  " + Math.abs(cw - cb) + "  : \n you may restart or go back to menu to continue  :-)", "1v1", JOptionPane.PLAIN_MESSAGE);
                            }
                        }
                    }
                }






