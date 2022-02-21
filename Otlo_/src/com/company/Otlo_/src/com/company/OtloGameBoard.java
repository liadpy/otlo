package com.company;

import jdk.swing.interop.SwingInterOpUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OtloGameBoard extends JPanel implements ActionListener {
    public static final ImageIcon WHITEDISK = new ImageIcon("img.png");//more comftarble
    public static final ImageIcon BLACKDISK = new ImageIcon("img_1.png");

    int c = 4;//c%2==0 for black, c%2==1 for white
    JButton btns[][] = new JButton[8][8];
    boolean iswithai;
    Ai_thread ai_thread = new Ai_thread(this);
    int flag = 0;
    int gflag;


    public OtloGameBoard(boolean iswithai) {
        this.iswithai = iswithai;
        setLayout(new GridLayout(8, 8));
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                UIManager.put("Button.select", new Color(45, 134, 77));//change press color
                btns[i][j] = new JButton();
                btns[i][j].setBackground(new Color(45, 127, 77));
                add(btns[i][j]);
                btns[i][j].addActionListener(this);
                btns[i][j].setBorder(BorderFactory.createBevelBorder(1, Color.black, Color.black));//creating better looking btn borders
                btns[i][j].setFont(new Font("Normal", Font.BOLD, 26));
            }
        btns[3][3].setIcon(BLACKDISK);
        btns[3][4].setIcon(WHITEDISK);
        btns[4][3].setIcon(WHITEDISK);
        btns[4][4].setIcon(BLACKDISK);
        btns[4][2].setText("⚫");
        btns[5][3].setText("⚫");
        btns[3][5].setText("⚫");
        btns[2][4].setText("⚫");
        setBackground(Color.BLACK);
        if (iswithai == true)
            ai_thread.start();
    }

    public void restartOtloBoard() {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                btns[i][j].setIcon(null);
        c = 4;
        btns[3][3].setIcon(BLACKDISK);
        btns[3][4].setIcon(WHITEDISK);
        btns[4][3].setIcon(WHITEDISK);
        btns[4][4].setIcon(BLACKDISK);
        flag = 0;
        for (int k = 0; k < 8; k++)
            for (int t = 0; t < 8; t++) {
                btns[k][t].setForeground(null);
                btns[k][t].setText(null);
            }
        btns[4][2].setText("⚫");
        btns[5][3].setText("⚫");
        btns[3][5].setText("⚫");
        btns[2][4].setText("⚫");

    }

    // the error : after finishing a game and restarting
    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (btns[i][j] == e.getSource()) {
                    if (btns[i][j].getIcon() == null && ismoveleagal(i, j, this, this.c)) {
                        int c_ = c;
                        if (c % 2 == 0) {                                       //black
                            btns[i][j].setIcon(BLACKDISK);
                            c_ += 1;
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
                            for (int k = 0; k < 8; k++)
                                for (int t = 0; t < 8; t++)
                                    btns[k][t].setText(null);
                            for (int k = 0; k < 8; k++)
                                for (int t = 0; t < 8; t++)
                                    if (ismoveleagal(k, t, this, c + 1) && btns[k][t].getIcon() == null) {
                                        btns[k][t].setForeground(Color.white);
                                        btns[k][t].setText("⚫");
                                    }

                            if (ismovesleftbytxt(this) == false)
                                flag = 1;
                        }
                        if (iswithai == false) {
                            if (c % 2 == 1) {                                           //white
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
                                for (int k = 0; k < 8; k++)
                                    for (int t = 0; t < 8; t++)
                                        btns[k][t].setText(null);
                                for (int k = 0; k < 8; k++)
                                    for (int t = 0; t < 8; t++)
                                        if (ismoveleagal(k, t, this, c + 1) && btns[k][t].getIcon() == null) {
                                            btns[k][t].setForeground(null);
                                            btns[k][t].setText("⚫");
                                        }
                                if (ismovesleftbytxt(this) == false)
                                    flag = 1;
                            }

                        }
                        if(isanycanmove(this))
                        if (flag == 1 && isgamedone(this) == false) {
                            if (c % 2 == 0) {
                                JOptionPane.showMessageDialog(null, "White Cant Make A  Move So Black Will Make A Move");
                                for (int k = 0; k < 8; k++)
                                    for (int t = 0; t < 8; t++)
                                        if (ismoveleagal(k, t, this, c) && btns[k][t].getIcon() == null) {
                                            btns[k][t].setForeground(null);
                                            btns[k][t].setText("⚫");
                                        }
                            }
                            if (c % 2 == 1) {
                                JOptionPane.showMessageDialog(null, "Black Cant Make A  Move So White Will Make A Move");
                                for (int k = 0; k < 8; k++)
                                    for (int t = 0; t < 8; t++)
                                        if (ismoveleagal(k, t, this, c) && btns[k][t].getIcon() == null) {
                                            btns[k][t].setForeground(Color.white);
                                            btns[k][t].setText("⚫");
                                        }
                            }

                            flag = 0;
                        } else
                            c = c_;
                    }
                    if (isgamedone(this)||!isanycanmove(this)) {
                        int cb = 0, cw = 0;
                        for (int ie = 0; ie < 8; ie++)
                            for (int je = 0; je < 8; je++) {
                                if (btns[ie][je].getIcon() == BLACKDISK)
                                    cb++;
                                else if (btns[ie][je].getIcon() == WHITEDISK)
                                    cw++;
                            }
                        String winner;
                        if (cw == cb)
                            JOptionPane.showMessageDialog(null, "draw  : \n you may restart or go back to menu to continue  :-)", "1v1", JOptionPane.PLAIN_MESSAGE);
                        if (cb > cw)
                            winner = "Black";
                        else winner = "White";
                        JOptionPane.showMessageDialog(null, winner + "  won  by  " + Math.abs(cw - cb) + "  : \n you may restart or go back to menu to continue  :-)", "1v1", JOptionPane.PLAIN_MESSAGE);
                    }
                }
    }

    public static boolean ismoveleagal(int i, int j, OtloGameBoard gameBoard, int c) {
        if (c % 2 == 0) {  //black

            int ix = i, jx = j;//vetical right check
            if (j < 6 && gameBoard.btns[i][j + 1].getIcon() == WHITEDISK)
                jx++;
            while (jx < 7 && gameBoard.btns[i][jx].getIcon() == WHITEDISK)
                jx++;

            if (gameBoard.btns[i][jx].getIcon() == BLACKDISK) {
                return true;
            }
            ix = i;
            jx = j;//vetical left check
            if (j > 1 && gameBoard.btns[i][j - 1].getIcon() == WHITEDISK)
                jx--;
            while (jx > 0 && gameBoard.btns[i][jx].getIcon() == WHITEDISK)
                jx--;
            if (gameBoard.btns[i][jx].getIcon() == BLACKDISK) {
                return true;

            }
            ix = i;
            jx = j;//horizontal down check
            if (i < 6 && gameBoard.btns[i + 1][j].getIcon() == WHITEDISK)
                ix++;
            while (ix < 7 && gameBoard.btns[ix][j].getIcon() == WHITEDISK)
                ix++;
            if (gameBoard.btns[ix][j].getIcon() == BLACKDISK) {
                return true;
            }
            ix = i;
            jx = j;//horizontal up check
            if (i > 1 && gameBoard.btns[i - 1][j].getIcon() == WHITEDISK)
                ix--;
            while (ix > 0 && gameBoard.btns[ix][j].getIcon() == WHITEDISK)
                ix--;
            if (gameBoard.btns[ix][j].getIcon() == BLACKDISK) {
                return true;
            }
            ix = i;
            jx = j;//cross up right check
            if (i > 1 && j < 6 && gameBoard.btns[i - 1][j + 1].getIcon() == WHITEDISK) {
                ix--;
                jx++;
            }
            while (jx < 7 && ix > 0 && gameBoard.btns[ix][jx].getIcon() == WHITEDISK) {
                ix--;
                jx++;
            }
            if (gameBoard.btns[ix][jx].getIcon() == BLACKDISK) {
                return true;
            }
            ix = i;
            jx = j;//cross down left check
            if (i < 6 && j > 1 && gameBoard.btns[i + 1][j - 1].getIcon() == WHITEDISK) {
                ix++;
                jx--;
            }
            while (ix < 7 && jx > 0 && gameBoard.btns[ix][jx].getIcon() == WHITEDISK) {
                ix++;
                jx--;
            }
            if (gameBoard.btns[ix][jx].getIcon() == BLACKDISK) {
                return true;
            }
            ix = i;
            jx = j;//cross down right check
            if (i < 6 && j < 6 && gameBoard.btns[i + 1][j + 1].getIcon() == WHITEDISK) {
                ix++;
                jx++;
            }
            while (ix < 7 && jx < 7 && gameBoard.btns[ix][jx].getIcon() == WHITEDISK) {
                ix++;
                jx++;
            }
            if (gameBoard.btns[ix][jx].getIcon() == BLACKDISK) {
                return true;
            }
            ix = i;
            jx = j;//cross up left check
            if (i > 1 && j > 1 && gameBoard.btns[i - 1][j - 1].getIcon() == WHITEDISK) {
                ix--;
                jx--;
            }
            while (ix > 0 && jx > 0 && gameBoard.btns[ix][jx].getIcon() == WHITEDISK) {
                ix--;
                jx--;
            }
            if (gameBoard.btns[ix][jx].getIcon() == BLACKDISK) {
                return true;
            }
        }//end black

        if (c % 2 == 1) {                                           //white
            int ix = i, jx = j;//vetical right check
            if (j < 6 && gameBoard.btns[i][j + 1].getIcon() == BLACKDISK)
                jx++;
            while (jx < 7 && gameBoard.btns[i][jx].getIcon() == BLACKDISK)
                jx++;

            if (gameBoard.btns[i][jx].getIcon() == WHITEDISK) {
                return true;
            }
            ix = i;
            jx = j;//vetical left check
            if (j > 1 && gameBoard.btns[i][j - 1].getIcon() == BLACKDISK)
                jx--;
            while (jx > 0 && gameBoard.btns[i][jx].getIcon() == BLACKDISK)
                jx--;
            if (gameBoard.btns[i][jx].getIcon() == WHITEDISK) {
                return true;
            }
            ix = i;
            jx = j;//horizontal down check
            if (i < 6 && gameBoard.btns[i + 1][j].getIcon() == BLACKDISK)
                ix++;
            while (ix < 7 && gameBoard.btns[ix][j].getIcon() == BLACKDISK)
                ix++;
            if (gameBoard.btns[ix][j].getIcon() == WHITEDISK) {
                return true;
            }
            ix = i;
            jx = j;//horizontal up check
            if (i > 1 && gameBoard.btns[i - 1][j].getIcon() == BLACKDISK)
                ix--;
            while (ix > 0 && gameBoard.btns[ix][j].getIcon() == BLACKDISK)
                ix--;
            if (gameBoard.btns[ix][j].getIcon() == WHITEDISK) {
                return true;
            }
            ix = i;
            jx = j;//cross up right check
            if (i > 1 && j < 6 && gameBoard.btns[i - 1][j + 1].getIcon() == BLACKDISK) {
                ix--;
                jx++;
            }
            while (jx < 7 && ix > 0 && gameBoard.btns[ix][jx].getIcon() == BLACKDISK) {
                ix--;
                jx++;
            }
            if (gameBoard.btns[ix][jx].getIcon() == WHITEDISK) {
                return true;
            }
            ix = i;
            jx = j;//cross down left check
            if (i < 6 && j > 1 && gameBoard.btns[i + 1][j - 1].getIcon() == BLACKDISK) {
                ix++;
                jx--;
            }
            while (ix < 7 && jx > 0 && gameBoard.btns[ix][jx].getIcon() == BLACKDISK) {
                ix++;
                jx--;
            }
            if (gameBoard.btns[ix][jx].getIcon() == WHITEDISK) {
                return true;
            }
            ix = i;
            jx = j;//cross down right check
            if (i < 6 && j < 6 && gameBoard.btns[i + 1][j + 1].getIcon() == BLACKDISK) {
                ix++;
                jx++;
            }
            while (ix < 7 && jx < 7 && gameBoard.btns[ix][jx].getIcon() == BLACKDISK) {
                ix++;
                jx++;
            }
            if (gameBoard.btns[ix][jx].getIcon() == WHITEDISK) {
                return true;
            }
            ix = i;
            jx = j;//cross up left check
            if (i > 1 && j > 1 && gameBoard.btns[i - 1][j - 1].getIcon() == BLACKDISK) {
                ix--;
                jx--;
            }
            while (ix > 0 && jx > 0 && gameBoard.btns[ix][jx].getIcon() == BLACKDISK) {
                ix--;
                jx--;
            }
            if (gameBoard.btns[ix][jx].getIcon() == WHITEDISK) {
                return true;
            }

        }
        return false;
    }


    public static boolean isgamedone(OtloGameBoard gameBoard) {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (gameBoard.btns[i][j].getIcon() == null)
                    return false;

        return true;
    }

    public static boolean isanycanmove(OtloGameBoard gameBoard) {
        for (int i=0; i < 8; i++)
            for (int j=0; j < 8; j++) {
                if (gameBoard.btns[i][j].getIcon() == null) {
                    if (ismoveleagal(i, j, gameBoard, gameBoard.c))
                        return true;
                    if (ismoveleagal(i, j, gameBoard, gameBoard.c + 1))
                        return true;
                }
            }
        return false;
    }


    public static boolean ismovesleftbytxt(OtloGameBoard gameBoard)
    {
        for (int k = 0; k < 8; k++)
            for (int t = 0; t < 8; t++)
            {
                if(gameBoard.btns[k][t].getText()!=null)
                return true;
            }
        return false;
    }
}

