import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuSolver extends JFrame {

    private static final int GRID_SIZE = 9;
    private JTextField[][] cells = new JTextField[GRID_SIZE][GRID_SIZE];
    private JButton solveButton = new JButton("Solve");
    private JButton clearButton = new JButton("Clear");

    public SudokuSolver() {
        setTitle("Sudoku Solver");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE));
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                cells[row][col] = new JTextField();
                cells[row][col].setHorizontalAlignment(JTextField.CENTER);
                boardPanel.add(cells[row][col]);
            }
        }

        JPanel buttonPanel = new JPanel();
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solveSudoku();
            }
        });
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearBoard();
            }
        });
        buttonPanel.add(solveButton);
        buttonPanel.add(clearButton);

        add(boardPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void solveSudoku() {
        int[][] board = new int[GRID_SIZE][GRID_SIZE];
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (!cells[row][col].getText().trim().isEmpty()) {
                    board[row][col] = Integer.parseInt(cells[row][col].getText());
                } else {
                    board[row][col] = 0;
                }
            }
        }

        if (solveBoard(board)) {
            updateBoard(board);
        } else {
            JOptionPane.showMessageDialog(this, "Unsolvable Sudoku puzzle.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean solveBoard(int[][] board) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (board[row][col] == 0) {
                    for (int num = 1; num <= GRID_SIZE; num++) {
                        if (isValidPlacement(board, num, row, col)) {
                            board[row][col] = num;

                            if (solveBoard(board)) {
                                return true;
                            } else {
                                board[row][col] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValidPlacement(int[][] board, int num, int row, int col) {
        return !isInRow(board, num, row) &&
               !isInCol(board, num, col) &&
               !isInBox(board, num, row, col);
    }

    private boolean isInRow(int[][] board, int num, int row) {
        for (int col = 0; col < GRID_SIZE; col++) {
            if (board[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    private boolean isInCol(int[][] board, int num, int col) {
        for (int row = 0; row < GRID_SIZE; row++) {
            if (board[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    private boolean isInBox(int[][] board, int num, int row, int col) {
        int localBoxRow = row - row % 3;
        int localBoxCol = col - col % 3;

        for (int i = localBoxRow; i < localBoxRow + 3; i++) {
            for (int j = localBoxCol; j < localBoxCol + 3; j++) {
                if (board[i][j] == num) {
                    return true;
                }
            }
        }
        return false;
    }

    private void updateBoard(int[][] board) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                cells[row][col].setText(String.valueOf(board[row][col]));
            }
        }
    }

    private void clearBoard() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                cells[row][col].setText("");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SudokuSolver().setVisible(true);
            }
        });
    }
}
