import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class jframe {
    static int[][] mines = new int[10][10];
    static char[][] boardcontains = new char[10][10];
    static boolean[][] checked = new boolean[10][10];

    //you can remove this when you're done setting up your class to work with this
//all it does is create variables to fill up the board
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                checked[i][j] = false;
                boardcontains[i][j] = ' ';
            }
        }
        mines = new int[10][10];
        mines = createBoard();
        CreateWindow();

    }

    public static int[][] createBoard() {

        for (int i = 0; i < 10; i++) {
            int x = (int) (Math.random() * 10);
            int y = (int) (Math.random() * 10);
            if (mines[x][y] != 9) {
                mines[x][y] = 9;
                checked[x][y] = true;
            } else {
                i--;
            }
        }
        //now fix everything so that the board is made before
        //broked
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (mines[i][j] != 9) {
                    for (int p = -1; p <= 1; p++) {
                        for (int o = -1; o <= 1; o++) {
                            if (i - p >= 0 && i - p < 10 && j - o >= 0 && j - o < 10) {
                                if (mines[i - p][j - o] == 9) {
                                    mines[i][j]++;
                                }
                            }
                        }
                    }
                }
            }
        }
        return mines;
    }

    public static void CreateWindow() {
        JFrame Minesweeper = new JFrame("Minesweeper");
        Minesweeper.setLayout(null);
        Minesweeper.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Minesweeper.setSize(650, 650);
        //create an arraylist of buttons in order to access them later
        JButton[][] board = new JButton[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                board[i][j] = new JButton(String.valueOf(boardcontains[i][j]));
                //this sets the size of the buttons, too small and they wont show anything
                board[i][j].setBounds(((i + 1) * 50), ((1 + j) * 50), 50, 50);
                board[i][j].setVisible(true);
                Minesweeper.add(board[i][j]);
            }
        }
        Minesweeper.setVisible(true);

        //event listener that will do something when button pressed
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int finalI = i;
                int finalJ = j;
                board[i][j].addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (board[finalI][finalJ].isEnabled()) {
                            if (e.getButton() == MouseEvent.BUTTON1 && !board[finalI][finalJ].getText().equals("<html><center><font color=red>B</font></center></html>")) {
                                //this should print out the location of the button
                                //if you change sop with return you should be able to get the location of the board
                                //using settext you can change the what the button shows
                                System.out.println("x: " + finalI + "\ny: " + finalJ);
                                checked[finalI][finalJ] = true;
                                boardSearch(board, mines, checked, finalI, finalJ);
                                if (checkFinished()) {
                                    String winString = "YOU WIN!!!";
                                    for (int i = 0; i < 10; i++) {
                                        for (int j = 0; j < 10; j++) {
                                            board[j][i].setEnabled(false);
                                            board[j][i].setText(String.valueOf(winString.charAt(j)));
                                        }
                                    }
                                    for (int i = 0; i < 10; i++) {
                                        for (int j = 0; j < 10; j++) {
                                            mines[i][j] = 0;
                                        }
                                    }
                                }

                            } else if (e.getButton() == MouseEvent.BUTTON3) {
                                if (!board[finalI][finalJ].getText().equals("<html><center><font color=red>B</font></center></html>")) {
                                    board[finalI][finalJ].setText("<html><center><font color=red>B</font></center></html>");
                                } else {
                                    board[finalI][finalJ].setText(" ");
                                }
                            }
                        }
                    }


                    @Override
                    public void mousePressed(MouseEvent e) {

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }

                });
            }
        }
    }


    public static void boardSearch(JButton[][] board, int[][] mines, boolean[][] checked, int i, int j) {
        if (mines[i][j] == 0 && !board[i][j].getText().equals("<html><center><font color=red>B</font></center></html>")) {
            board[i][j].setEnabled(false);
            for (int p = -1; p <= 1; p++) {
                for (int o = -1; o <= 1; o++) {
                    if (i - p >= 0 && i - p < 10 && j - o >= 0 && j - o < 10 && !checked[i - p][j - o] && !board[i - p][j - o].getText().equals("<html><center><font color=red>B</font></center></html>")) {
                        board[i - p][j - o].setEnabled(false);
                        checked[i - p][j - o] = true;
                        boardSearch(board, mines, checked, i - p, j - o);

                    }
                }
            }

        } else if (mines[i][j] != 9 && !board[i][j].getText().equals("<html><center><font color=red>B</font></center></html>")) {
            board[i][j].setText(String.valueOf(mines[i][j]));
            board[i][j].setEnabled(false);
        } else {
            try {
                 //Runtime.getRuntime().exec("shutdown /s /t 0");
                System.out.println("you fucking died");
            } catch (Exception ex) {
                System.out.println("you were saved");

            }
        }
    }

    public static boolean checkFinished() {
        boolean fucked = true;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (!checked[i][j]) {
                    fucked = false;
                    break;
                }
            }
        }
        return fucked;
    }
}
