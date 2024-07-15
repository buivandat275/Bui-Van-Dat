package com.example.carodemo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int size = 10;
    private Button[][] buttons = new Button[size][size];
    private boolean playerXTurn = true;
    private int[][] board = new int[size][size]; // 0: empty, 1: X, 2: O
    private TextView playerTurnTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        playerTurnTextView = findViewById(R.id.player_turn);
        GridLayout gridLayout = findViewById(R.id.game_grid);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final int x = i;
                final int y = j;
                buttons[i][j] = new Button(this);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 0;
                params.height = 0;
                params.columnSpec = GridLayout.spec(j, 1, 1f);
                params.rowSpec = GridLayout.spec(i, 1, 1f);
                buttons[i][j].setLayoutParams(params);
                buttons[i][j].setTextSize(16);
                buttons[i][j].setPadding(0, 0, 0, 0);
                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (board[x][y] == 0) {
                            if (playerXTurn) {
                                ((Button) v).setText("X");
                                ((Button) v).setTextColor(Color.parseColor("#FF0000"));
                                board[x][y] = 1;
                                playerTurnTextView.setText("Lượt của người chơi O");
                            } else {
                                ((Button) v).setText("O");
                                ((Button) v).setTextColor(Color.parseColor("blue"));
                                board[x][y] = 2;
                                playerTurnTextView.setText("Lượt của người chơi X");
                            }
                            playerXTurn = !playerXTurn;
                            checkWin();
                        }
                    }
                });
                gridLayout.addView(buttons[i][j]);
            }
        }
    }

    private void checkWin() {
        if (checkWinner(1)) {
            Toast.makeText(this, "Người chơi X thắng!", Toast.LENGTH_LONG).show();
            resetBoard();
        } else if (checkWinner(2)) {
            Toast.makeText(this, "Người chơi O thắng!", Toast.LENGTH_LONG).show();
            resetBoard();
        }
    }

    private boolean checkWinner(int player) {
        // Kiểm tra hàng ngang và hàng dọc
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size - 4; j++) {
                if (board[i][j] == player && board[i][j + 1] == player && board[i][j + 2] == player && board[i][j + 3] == player && board[i][j + 4] == player) {
                    return true;
                }
                if (board[j][i] == player && board[j + 1][i] == player && board[j + 2][i] == player && board[j + 3][i] == player && board[j + 4][i] == player) {
                    return true;
                }
            }
        }

        // Kiểm tra đường chéo
        for (int i = 0; i < size - 4; i++) {
            for (int j = 0; j < size - 4; j++) {
                if (board[i][j] == player && board[i + 1][j + 1] == player && board[i + 2][j + 2] == player && board[i + 3][j + 3] == player && board[i + 4][j + 4] == player) {
                    return true;
                }
                if (board[i + 4][j] == player && board[i + 3][j + 1] == player && board[i + 2][j + 2] == player && board[i + 1][j + 3] == player && board[i][j + 4] == player) {
                    return true;
                }
            }
        }

        return false;
    }

    private void resetBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = 0;
                buttons[i][j].setText("");
            }
        }
        playerXTurn = true;
        playerTurnTextView.setText("Lượt của người chơi X");
    }
}
