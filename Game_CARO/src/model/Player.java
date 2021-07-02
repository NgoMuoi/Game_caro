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
public interface Player {
    public Point movePoint(int player);

	int getPlayerFlag();

	void setPlayerFlag(int playerFlag);

	BoardState getBoardState();
}
