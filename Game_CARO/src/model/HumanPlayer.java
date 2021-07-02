/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author win 10pro
 */
public class HumanPlayer implements Player {
        BoardState boardState; // ban co de xu li
	int playerFlag = 1; // danh dau la nguoi choi

	public HumanPlayer(BoardState board) {
		this.boardState = board;
	}

	public Point movePoint(int player) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getPlayerFlag() {
		// TODO Auto-generated method stub
		return playerFlag;
	}

	public void setPlayerFlag(int playerFlag) {
		this.playerFlag = playerFlag;
	}

	public BoardState getBoardState() {
		// TODO Auto-generated method stub
		return boardState;
	}

}
