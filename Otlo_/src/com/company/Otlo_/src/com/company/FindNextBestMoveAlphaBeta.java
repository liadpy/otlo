package com.company;

import javax.swing.*;

// Java program to find the next optimal move for a player

public class FindNextBestMoveAlphaBeta {
   static OtloGameBoard global_Board=new OtloGameBoard(false);
   static OtloGameBoard gboard =new OtloGameBoard(false);
   static OtloGameBoard oboard = new OtloGameBoard(false);

    // This function returns true if there are moves
    // remaining on the board. It returns false if
    // there are no moves left to play.
    public static boolean isMovesLeft(OtloGameBoard gameBoard) {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (gameBoard.btns[i][j].getIcon() == null)
                    return true;
        return false;
    }


    // This is the evaluation function *******************************************
    public static int evaluate(OtloGameBoard gb) {

      if (isMovesLeft(gb) == false)
            return 0;

        int val = 0;
        if(gb.btns[3][3].getIcon()==gb.WHITEDISK) val+=3;//mids
        if(gb.btns[4][3].getIcon()==gb.WHITEDISK) val+=3;
        if(gb.btns[3][4].getIcon()==gb.WHITEDISK) val+=3;
        if(gb.btns[4][4].getIcon()==gb.WHITEDISK) val+=3;

        if(gb.btns[3][3].getIcon()==gb.BLACKDISK) val-=2;//bad mids
        if(gb.btns[4][3].getIcon()==gb.BLACKDISK) val-=2;
        if(gb.btns[3][4].getIcon()==gb.BLACKDISK) val-=2;
        if(gb.btns[4][4].getIcon()==gb.BLACKDISK) val-=2;

        if(gb.btns[0][0].getIcon()==gb.WHITEDISK) val+=4;//corners
        if(gb.btns[0][7].getIcon()==gb.WHITEDISK) val+=4;
        if(gb.btns[7][0].getIcon()==gb.WHITEDISK) val+=4;
        if(gb.btns[7][7].getIcon()==gb.WHITEDISK) val+=4;

        if(gb.btns[0][0].getIcon()==gb.BLACKDISK) val-=7;//bad corners
        if(gb.btns[0][7].getIcon()==gb.BLACKDISK) val-=7;
        if(gb.btns[7][0].getIcon()==gb.BLACKDISK) val-=7;
        if(gb.btns[7][7].getIcon()==gb.BLACKDISK) val-=7;


        for(int i=0;i<8;i++) {          //kirot
            if (gb.btns[i][0].getIcon() == OtloGameBoard.BLACKDISK) val -= 5;
            if (gb.btns[i][7].getIcon() == OtloGameBoard.BLACKDISK) val -= 5;
            if (gb.btns[7][i].getIcon() == OtloGameBoard.BLACKDISK) val -= 5;
            if (gb.btns[0][i].getIcon() == OtloGameBoard.BLACKDISK) val -= 5;


        }

      //checking buffers not including midbuffers
        if((gb.btns[0][0].getIcon()==gb.WHITEDISK)&&(gb.btns[0][1].getIcon()==gb.WHITEDISK&&gb.btns[1][0].getIcon()==gb.WHITEDISK)) val+=3;
        else
        if((gb.btns[0][0].getIcon()==gb.WHITEDISK)&&(gb.btns[0][1].getIcon()==gb.WHITEDISK||gb.btns[1][0].getIcon()==gb.WHITEDISK)) val+=2;

        if((gb.btns[7][0].getIcon()==gb.WHITEDISK)&&(gb.btns[7][1].getIcon()==gb.WHITEDISK&&gb.btns[6][0].getIcon()==gb.WHITEDISK)) val+=3;
        else
        if((gb.btns[7][0].getIcon()==gb.WHITEDISK)&&(gb.btns[7][1].getIcon()==gb.WHITEDISK||gb.btns[6][0].getIcon()==gb.WHITEDISK)) val+=2;

        if((gb.btns[0][7].getIcon()==gb.WHITEDISK)&&(gb.btns[1][7].getIcon()==gb.WHITEDISK&&gb.btns[0][6].getIcon()==gb.WHITEDISK)) val+=3;
        else
        if((gb.btns[0][7].getIcon()==gb.WHITEDISK)&&(gb.btns[1][7].getIcon()==gb.WHITEDISK||gb.btns[0][6].getIcon()==gb.WHITEDISK)) val+=2;

        if((gb.btns[7][7].getIcon()==gb.WHITEDISK)&&(gb.btns[7][6].getIcon()==gb.WHITEDISK&&gb.btns[6][7].getIcon()==gb.WHITEDISK)) val+=3;
        else
        if((gb.btns[7][7].getIcon()==gb.WHITEDISK)&&(gb.btns[7][6].getIcon()==gb.WHITEDISK||gb.btns[6][7].getIcon()==gb.WHITEDISK)) val+=2;

        //checking points
        int cb = 0, cw = 0;
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                if (gb.btns[i][j].getIcon() == gb.WHITEDISK) cw++;
                if (gb.btns[i][j].getIcon() == gb.BLACKDISK) cb++;
            }
        val = val + 2*(cw - cb);//adding hefresh in order to get more points and win

        return val;


    }

    /*
    MINI MAX ******************************************************************************
     */
    // This is the minimax function. It considers all
    // the possible ways the game can go and returns
    // the value of the board
    public static int minimax(OtloGameBoard gameBoard, int depth, Boolean isMax, int alpha, int beta) {
        int score;

        if (depth == 0) {
            score = evaluate(gameBoard);
            return score;
        }


        // If this maximizer's move
        if (isMax) {
            int bestScore = Integer.MIN_VALUE;

            // Traverse all cells
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    // Check if cell is empty
                    if (gameBoard.btns[i][j].getIcon() == null) {
                        // Make the move
                        gameBoard= makethemove(gameBoard,OtloGameBoard.WHITEDISK,i,j);//!!!!!!!!!!!!!!
                        int currentScore;
                        // Call minimax recursively and choose
                        // the maximum value
                        for(int ii = 0;ii<8;ii++)
                            for (int jj = 0;jj<8;jj++)
                              gboard.btns[ii][jj].setIcon(gameBoard.btns[ii][jj].getIcon());
                        currentScore = minimax(gameBoard, depth - 1, false, alpha, beta);
                        // Undo the move
                        for(int ii = 0;ii<8;ii++)
                            for (int jj = 0;jj<8;jj++)
                                gameBoard.btns[ii][jj].setIcon(gboard.btns[ii][jj].getIcon());//undo!!!!!!!!!!!!!!!!!!!!!!!!!
                        bestScore = Math.max(currentScore, bestScore);
                       // if (best >= beta)
                         //   break;
                        //alpha = Math.max(alpha, best);
                    }
                }
            }
            return bestScore;
        }

        // If this minimizer's move
        else {
            int bestScore = Integer.MAX_VALUE;

            // Traverse all cells
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    // Check if cell is empty
                    if (gameBoard.btns[i][j].getIcon() == null) {
                        // Make the move
                        for(int ii = 0;ii<8;ii++)
                            for (int jj = 0;jj<8;jj++)
                                oboard.btns[ii][jj].setIcon(gameBoard.btns[ii][jj].getIcon());
                        gameBoard=makethemove(gameBoard,OtloGameBoard.BLACKDISK,i,j);//uiiiiiiihhhhhhuuuuuuu

                        int currentScore;
                        // Call minimax recursively and choose
                        // the minimum value
                        currentScore = minimax(gameBoard, depth - 1, true, alpha, beta);
                        for(int ii = 0;ii<8;ii++)
                            for (int jj = 0;jj<8;jj++)
                                gameBoard.btns[ii][jj].setIcon(oboard.btns[ii][jj].getIcon());;///!!!!!!undooooo
                        bestScore = Math.min(currentScore, bestScore);
                      //  if (best >= beta)
                        //    break;
                        //alpha = Math.max(alpha, best);

                    }
                }
            }
            return bestScore;
        }

    }

    // This will return the best possible, move for the player
    public static Move findBestMove(OtloGameBoard board) {
        int bestVal = Integer.MIN_VALUE;
        Move bestMove = new Move();
        bestMove.row = -1;
        bestMove.col = -1;

        // Traverse all cells, evaluate minimax function
        // for all empty cells. And return the cell
        // with optimal value.
        for (int i = 7; i >=0; i--) {
            for (int j = 7; j >=0; j--) {
                int alpha = Integer.MIN_VALUE;
                int beta = Integer.MAX_VALUE;
                // Check if cell is empty
                if (board.btns[i][j].getIcon() == null&&OtloGameBoard.ismoveleagal(i,j,board,board.c)) {
                    // Make the move
                    for(int ii = 0;ii<8;ii++)
                        for (int jj = 0;jj<8;jj++)
                    global_Board.btns[ii][jj].setIcon(board.btns[ii][jj].getIcon());
                   board=makethemove(board,OtloGameBoard.WHITEDISK,i,j);//55555555555555555555555555555555555555555

                    // compute evaluation function for this move.
                    int moveVal = minimax(board, 1, false, alpha, beta);
                    System.out.println(moveVal);

                    // Undo the move
                    for(int ii = 0;ii<8;ii++)
                        for (int jj = 0;jj<8;jj++)
                    board.btns[ii][jj].setIcon(global_Board.btns[ii][jj].getIcon());//undo11111111111111111111111111111111111111111111111111111111

                    // If the value of the current move is
                    // more than the best value, then update
                    // best/
                    if (moveVal > bestVal) {
                        bestMove.row = i;
                        bestMove.col = j;
                        bestVal = moveVal;
                    }
                }
            }
        }

        System.out.printf("The value of the best Move " +
                "is : %d\n\n", bestVal);

        return bestMove;
    }


    public static OtloGameBoard makethemove(OtloGameBoard board, ImageIcon icon, int i, int j) {
        if (icon == OtloGameBoard.BLACKDISK) {                                       //black
            board.btns[i][j].setIcon(OtloGameBoard.BLACKDISK);

            int ix = i, jx = j;//vetical right check
            if (j < 6 && board.btns[i][j + 1].getIcon() == OtloGameBoard.WHITEDISK)
                jx++;
            while (jx < 7 && board.btns[i][jx].getIcon() == OtloGameBoard.WHITEDISK)
                jx++;

            if (board.btns[i][jx].getIcon() == OtloGameBoard.BLACKDISK) {
                while (jx != j) {
                    board.btns[i][jx].setIcon(OtloGameBoard.BLACKDISK);
                    jx--;
                }
            }
            ix = i;
            jx = j;//vetical left check
            if (j > 1 && board.btns[i][j - 1].getIcon() == OtloGameBoard.WHITEDISK)
                jx--;
            while (jx > 0 && board.btns[i][jx].getIcon() == OtloGameBoard.WHITEDISK)
                jx--;
            if (board.btns[i][jx].getIcon() == OtloGameBoard.BLACKDISK) {
                while (jx != j) {
                    board.btns[i][jx].setIcon(OtloGameBoard.BLACKDISK);
                    jx++;
                }
            }
            ix = i;
            jx = j;//horizontal down check
            if (i < 6 && board.btns[i + 1][j].getIcon() == OtloGameBoard.WHITEDISK)
                ix++;
            while (ix < 7 && board.btns[ix][j].getIcon() == OtloGameBoard.WHITEDISK)
                ix++;
            if (board.btns[ix][j].getIcon() == OtloGameBoard.BLACKDISK) {
                while (ix != i) {
                    board.btns[ix][j].setIcon(OtloGameBoard.BLACKDISK);
                    ix--;
                }
            }
            ix = i;
            jx = j;//horizontal up check
            if (i > 1 && board.btns[i - 1][j].getIcon() == OtloGameBoard.WHITEDISK)
                ix--;
            while (ix > 0 && board.btns[ix][j].getIcon() == OtloGameBoard.WHITEDISK)
                ix--;
            if (board.btns[ix][j].getIcon() == OtloGameBoard.BLACKDISK) {
                while (ix != i) {
                    board.btns[ix][j].setIcon(OtloGameBoard.BLACKDISK);
                    ix++;
                }
            }
            ix = i;
            jx = j;//cross up right check
            if (i > 1 && j < 6 && board.btns[i - 1][j + 1].getIcon() == OtloGameBoard.WHITEDISK) {
                ix--;
                jx++;
            }
            while (jx < 7 && ix > 0 && board.btns[ix][jx].getIcon() == OtloGameBoard.WHITEDISK) {
                ix--;
                jx++;
            }
            if (board.btns[ix][jx].getIcon() == OtloGameBoard.BLACKDISK) {
                while (ix != i && jx != j) {
                    board.btns[ix][jx].setIcon(OtloGameBoard.BLACKDISK);
                    ix++;
                    jx--;
                }
            }
            ix = i;
            jx = j;//cross down left check
            if (i < 6 && j > 1 && board.btns[i + 1][j - 1].getIcon() == OtloGameBoard.WHITEDISK) {
                ix++;
                jx--;
            }
            while (ix < 7 && jx > 0 && board.btns[ix][jx].getIcon() == OtloGameBoard.WHITEDISK) {
                ix++;
                jx--;
            }
            if (board.btns[ix][jx].getIcon() == OtloGameBoard.BLACKDISK) {
                while (ix != i && jx != j) {
                    board.btns[ix][jx].setIcon(OtloGameBoard.BLACKDISK);
                    ix--;
                    jx++;
                }
            }
            ix = i;
            jx = j;//cross down right check
            if (i < 6 && j < 6 && board.btns[i + 1][j + 1].getIcon() == OtloGameBoard.WHITEDISK) {
                ix++;
                jx++;
            }
            while (ix < 7 && jx < 7 && board.btns[ix][jx].getIcon() == OtloGameBoard.WHITEDISK) {
                ix++;
                jx++;
            }
            if (board.btns[ix][jx].getIcon() == OtloGameBoard.BLACKDISK) {
                while (ix != i && jx != j) {
                    board.btns[ix][jx].setIcon(OtloGameBoard.BLACKDISK);
                    ix--;
                    jx--;
                }
            }
            ix = i;
            jx = j;//cross up left check
            if (i > 1 && j > 1 && board.btns[i - 1][j - 1].getIcon() == OtloGameBoard.WHITEDISK) {
                ix--;
                jx--;
            }
            while (ix > 0 && jx > 0 && board.btns[ix][jx].getIcon() == OtloGameBoard.WHITEDISK) {
                ix--;
                jx--;
            }
            if (board.btns[ix][jx].getIcon() == OtloGameBoard.BLACKDISK) {
                while (ix != i && jx != j) {
                    board.btns[ix][jx].setIcon(OtloGameBoard.BLACKDISK);
                    ix++;
                    jx++;
                }
            }
        }
        if (icon == OtloGameBoard.WHITEDISK) {                          //white

                board.btns[i][j].setIcon(OtloGameBoard.WHITEDISK);
                int ix = i, jx = j;//vetical right check
                if (j < 6 && board.btns[i][j + 1].getIcon() == OtloGameBoard.BLACKDISK)
                    jx++;
                while (jx < 7 && board.btns[i][jx].getIcon() == OtloGameBoard.BLACKDISK)
                    jx++;

                if (board.btns[i][jx].getIcon() == OtloGameBoard.WHITEDISK) {
                    while (jx != j) {
                        board.btns[i][jx].setIcon(OtloGameBoard.WHITEDISK);
                        jx--;
                    }
                }
                ix = i;
                jx = j;//vetical left check
                if (j > 1 && board.btns[i][j - 1].getIcon() == OtloGameBoard.BLACKDISK)
                    jx--;
                while (jx > 0 && board.btns[i][jx].getIcon() == OtloGameBoard.BLACKDISK)
                    jx--;
                if (board.btns[i][jx].getIcon() == OtloGameBoard.WHITEDISK) {
                    while (jx != j) {
                        board.btns[i][jx].setIcon(OtloGameBoard.WHITEDISK);
                        jx++;
                    }
                }
                ix = i;
                jx = j;//horizontal down check
                if (i < 6 && board.btns[i + 1][j].getIcon() == OtloGameBoard.BLACKDISK)
                    ix++;
                while (ix < 7 && board.btns[ix][j].getIcon() == OtloGameBoard.BLACKDISK)
                    ix++;
                if (board.btns[ix][j].getIcon() == OtloGameBoard.WHITEDISK) {
                    while (ix != i) {
                        board.btns[ix][j].setIcon(OtloGameBoard.WHITEDISK);
                        ix--;
                    }
                }
                ix = i;
                jx = j;//horizontal up check
                if (i > 1 && board.btns[i - 1][j].getIcon() == OtloGameBoard.BLACKDISK)
                    ix--;
                while (ix > 0 && board.btns[ix][j].getIcon() == OtloGameBoard.BLACKDISK)
                    ix--;
                if (board.btns[ix][j].getIcon() == OtloGameBoard.WHITEDISK) {
                    while (ix != i) {
                        board.btns[ix][j].setIcon(OtloGameBoard.WHITEDISK);
                        ix++;
                    }
                }
                ix = i;
                jx = j;//cross up right check
                if (i > 1 && j < 6 && board.btns[i - 1][j + 1].getIcon() == OtloGameBoard.BLACKDISK) {
                    ix--;
                    jx++;
                }
                while (jx < 7 && ix > 0 && board.btns[ix][jx].getIcon() == OtloGameBoard.BLACKDISK) {
                    ix--;
                    jx++;
                }
                if (board.btns[ix][jx].getIcon() == OtloGameBoard.WHITEDISK) {
                    while (ix != i && jx != j) {
                        board.btns[ix][jx].setIcon(OtloGameBoard.WHITEDISK);
                        ix++;
                        jx--;
                    }
                }
                ix = i;
                jx = j;//cross down left check
                if (i < 6 && j > 1 && board.btns[i + 1][j - 1].getIcon() == OtloGameBoard.BLACKDISK) {
                    ix++;
                    jx--;
                }
                while (ix < 7 && jx > 0 && board.btns[ix][jx].getIcon() == OtloGameBoard.BLACKDISK) {
                    ix++;
                    jx--;
                }
                if (board.btns[ix][jx].getIcon() == OtloGameBoard.WHITEDISK) {
                    while (ix != i && jx != j) {
                        board.btns[ix][jx].setIcon(OtloGameBoard.WHITEDISK);
                        ix--;
                        jx++;
                    }
                }
                ix = i;
                jx = j;//cross down right check
                if (i < 6 && j < 6 && board.btns[i + 1][j + 1].getIcon() == OtloGameBoard.BLACKDISK) {
                    ix++;
                    jx++;
                }
                while (ix < 7 && jx < 7 && board.btns[ix][jx].getIcon() == OtloGameBoard.BLACKDISK) {
                    ix++;
                    jx++;
                }
                if (board.btns[ix][jx].getIcon() == OtloGameBoard.WHITEDISK) {
                    while (ix != i && jx != j) {
                        board.btns[ix][jx].setIcon(OtloGameBoard.WHITEDISK);
                        ix--;
                        jx--;
                    }
                }
                ix = i;
                jx = j;//cross up left check
                if (i > 1 && j > 1 && board.btns[i - 1][j - 1].getIcon() == OtloGameBoard.BLACKDISK) {
                    ix--;
                    jx--;
                }
                while (ix > 0 && jx > 0 && board.btns[ix][jx].getIcon() == OtloGameBoard.BLACKDISK) {
                    ix--;
                    jx--;
                }
                if (board.btns[ix][jx].getIcon() == OtloGameBoard.WHITEDISK) {
                    while (ix != i && jx != j) {
                        board.btns[ix][jx].setIcon(OtloGameBoard.WHITEDISK);
                        ix++;
                        jx++;
                    }
                }
        }
        return board;
    }
}