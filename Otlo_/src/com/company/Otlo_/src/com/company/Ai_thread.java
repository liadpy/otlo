package com.company;

import javax.swing.*;
import java.awt.*;

public class Ai_thread extends Thread
{
OtloGameBoard gameBoard;

    public Ai_thread(OtloGameBoard gameBoard)
    {
    this.gameBoard=gameBoard;
    }

    @Override
    public void run() {
        while (true) {
    try{
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (gameBoard.c % 2 == 1 && gameBoard.iswithai==true)
            {
                try {
                    sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Move move=new Move();
                move =FindNextBestMoveAlphaBeta.findBestMove(gameBoard);
                int ii= move.row;
                int jj= move.col;
                gameBoard.btns[ii][jj].setIcon(gameBoard.WHITEDISK);
                int ix = ii, jx = jj;//vetical right check
                if (jj < 6 && gameBoard.btns[ii][jj + 1].getIcon() == gameBoard.BLACKDISK)
                    jx++;
                while (jx < 7 && gameBoard.btns[ii][jx].getIcon() == gameBoard.BLACKDISK)
                    jx++;

                if (gameBoard.btns[ii][jx].getIcon() == gameBoard.WHITEDISK) {
                    while (jx != jj) {
                        gameBoard.btns[ii][jx].setIcon(gameBoard.WHITEDISK);
                        jx--;
                    }
                }
                ix = ii;
                jx = jj;//vetical left check
                if (jj > 1 && gameBoard.btns[ii][jj - 1].getIcon() == gameBoard.BLACKDISK)
                    jx--;
                while (jx > 0 && gameBoard.btns[ii][jx].getIcon() == gameBoard.BLACKDISK)
                    jx--;
                if (gameBoard.btns[ii][jx].getIcon() == gameBoard.WHITEDISK) {
                    while (jx != jj) {
                        gameBoard.btns[ii][jx].setIcon(gameBoard.WHITEDISK);
                        jx++;
                    }
                }
                ix = ii;
                jx = jj;//horizontal down check
                if (ii < 6 && gameBoard.btns[ii + 1][jj].getIcon() == gameBoard.BLACKDISK)
                    ix++;
                while (ix < 7 && gameBoard.btns[ix][jj].getIcon() == gameBoard.BLACKDISK)
                    ix++;
                if (gameBoard.btns[ix][jj].getIcon() == gameBoard.WHITEDISK) {
                    while (ix != ii) {
                        gameBoard.btns[ix][jj].setIcon(gameBoard.WHITEDISK);
                        ix--;
                    }
                }
                ix = ii;
                jx = jj;//horizontal up check
                if (ii > 1 && gameBoard.btns[ii - 1][jj].getIcon() == gameBoard.BLACKDISK)
                    ix--;
                while (ix > 0 && gameBoard.btns[ix][jj].getIcon() == gameBoard.BLACKDISK)
                    ix--;
                if (gameBoard.btns[ix][jj].getIcon() == gameBoard.WHITEDISK) {
                    while (ix != ii) {
                        gameBoard.btns[ix][jj].setIcon(gameBoard.WHITEDISK);
                        ix++;
                    }
                }
                ix = ii;
                jx = jj;//cross up right check
                if (ii > 1 && jj < 6 && gameBoard.btns[ii - 1][jj + 1].getIcon() == gameBoard.BLACKDISK) {
                    ix--;
                    jx++;
                }
                while (jx < 7 && ix > 0 && gameBoard.btns[ix][jx].getIcon() == gameBoard.BLACKDISK) {
                    ix--;
                    jx++;
                }
                if (gameBoard.btns[ix][jx].getIcon() == gameBoard.WHITEDISK) {
                    while (ix != ii && jx != jj) {
                        gameBoard.btns[ix][jx].setIcon(gameBoard.WHITEDISK);
                        ix++;
                        jx--;
                    }
                }
                ix = ii;
                jx = jj;//cross down left check
                if (ii < 6 && jj > 1 && gameBoard.btns[ii + 1][jj - 1].getIcon() == gameBoard.BLACKDISK) {
                    ix++;
                    jx--;
                }
                while (ix < 7 && jx > 0 && gameBoard.btns[ix][jx].getIcon() == gameBoard.BLACKDISK) {
                    ix++;
                    jx--;
                }
                if (gameBoard.btns[ix][jx].getIcon() == gameBoard.WHITEDISK) {
                    while (ix != ii && jx != jj) {
                        gameBoard.btns[ix][jx].setIcon(gameBoard.WHITEDISK);
                        ix--;
                        jx++;
                    }
                }
                ix = ii;
                jx = jj;//cross down right check
                if (ii < 6 && jj < 6 && gameBoard.btns[ii + 1][jj + 1].getIcon() == gameBoard.BLACKDISK) {
                    ix++;
                    jx++;
                }
                while (ix < 7 && jx < 7 && gameBoard.btns[ix][jx].getIcon() == gameBoard.BLACKDISK) {
                    ix++;
                    jx++;
                }
                if (gameBoard.btns[ix][jx].getIcon() == gameBoard.WHITEDISK) {
                    while (ix != ii && jx != jj) {
                        gameBoard.btns[ix][jx].setIcon(gameBoard.WHITEDISK);
                        ix--;
                        jx--;
                    }
                }
                ix = ii;
                jx = jj;//cross up left check
                if (ii > 1 && jj > 1 && gameBoard.btns[ii - 1][jj - 1].getIcon() == gameBoard.BLACKDISK) {
                    ix--;
                    jx--;
                }
                while (ix > 0 && jx > 0 && gameBoard.btns[ix][jx].getIcon() == gameBoard.BLACKDISK) {
                    ix--;
                    jx--;
                }
                if (gameBoard.btns[ix][jx].getIcon() == gameBoard.WHITEDISK) {
                    while (ix != ii && jx != jj) {
                        gameBoard.btns[ix][jx].setIcon(gameBoard.WHITEDISK);
                        ix++;
                        jx++;
                    }
                }
                for(int k=0;k<8;k++)
                    for(int t=0;t<8;t++)
                        gameBoard.btns[k][t].setText(null);
                for(int k=0;k<8;k++)
                    for(int t=0;t<8;t++)
                        if(OtloGameBoard.ismoveleagal(k,t,gameBoard,gameBoard.c+1)&&gameBoard.btns[k][t].getIcon()==null) {
                            gameBoard.btns[k][t].setForeground(null);
                            gameBoard.btns[k][t].setText("⚫");
                        }

                gameBoard.c++;

                if(OtloGameBoard.isgamedone(gameBoard)==true) {
                    int cb=0,cw=0;
                    for (int ie = 0; ie < 8; ie++)
                        for (int je = 0; je < 8; je++) {
                            if(gameBoard.btns[ie][je].getIcon()==OtloGameBoard.BLACKDISK)
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
        } catch (Exception e){  }
        }
    }
}
