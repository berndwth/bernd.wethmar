package wethmar.bernd.simplicity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import wethmar.bernd.simplicity.Objects.Board;
import wethmar.bernd.simplicity.Objects.BoardView;


public class BoardFragment extends android.support.v4.app.Fragment {

    private int board_size;
    protected Board board;
    protected BoardView boardview;
    private boolean isGoal = false;
    private boolean isRandom = false;
    private String level;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        board_size = getBoardSize(args.getInt("size"));
        isGoal = args.getBoolean("isGoal");
        level = args.getString("Level_Nr");
        isRandom = args.getBoolean("isRandom");

    }

    public boolean isRandom() {
        return isRandom;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (board == null) {
            if (isGoal) {
                board = new Board(level);
                board.setContext(getActivity());
                ((PlayScreen) getActivity()).boardsReady();
            } else if (isRandom) {
                board = Board.generateRandom(board_size);
                board.setContext(getActivity());
                ((PlayScreen) getActivity()).boardsReady();
            }
            else {
                board = new Board(board_size);
                board.setContext(getActivity());
            }
        }

        if (boardview == null) {
            boardview = new BoardView(getActivity().getApplicationContext(), board);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            boardview.setLayoutParams(layoutParams);
        }
        return boardview;
    }

    private int getBoardSize(int screen_size) {
        if (screen_size >= 720)
            return 450;
        if (screen_size >= 600)
            return 400;
        if (screen_size >= 480)
            return 350;
        if (screen_size >= 330)
            return 300;
        else
            return 200;
    }

    public void resetBoard() {
        board = new Board(board_size);
        board.setContext(getActivity());
        boardview.setBoard(board);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //board = null;
    }
}
