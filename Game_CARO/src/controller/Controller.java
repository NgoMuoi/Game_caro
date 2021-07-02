/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Timer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import model.BoardState;
import model.ComputerPlayer;
import model.Player;
import model.HumanPlayer;
import model.Point;
import model.TaskTimer;
import view.View;
/**
 *
 * @author win 10pro
 */
public class Controller implements IController{
    public View view; 
	private Player player;
	private Stack<Point> stack; // ngan xep luu cac nuoc da di
	private Class<?> classImg ; //  lay anh quan co
	private InputStream o;
	private InputStream x;
	private Image imageO;
	private Image imageX;
	private boolean end;
	private int tongNuocDi;
	private String playerWin;

	public Controller() {
		getComponents();	
	}

	private void getComponents() {
		end = false;
		tongNuocDi = 0;
		playerWin = "";
		stack = new Stack<>();
		classImg = this.getClass();
		o = classImg.getResourceAsStream("/image/o.png");
		x = classImg.getResourceAsStream("/image/x.png");
		imageO = new Image(o);
		imageX = new Image(x);
	}

	public Point AI(int player) {
		return this.player.movePoint(player);
	}

	public int getPlayerFlag() {
		return player.getPlayerFlag();
	}

	public void setPlayerFlag(int playerFlag) {
		player.setPlayerFlag(playerFlag);
	}

	public BoardState getBoardState() {
		return player.getBoardState();
	}

	public int checkEnd(int x, int y) {
		return player.getBoardState().checkEnd(x, y);
	}


	public boolean isEnd() {
		return end;
	}

	public void play(Button c, Button[][] a) {
		StringTokenizer tokenizer = new StringTokenizer(c.getAccessibleText(), ";");
		int x = Integer.parseInt(tokenizer.nextToken());
		int y = Integer.parseInt(tokenizer.nextToken());
		//
		if (player instanceof HumanPlayer) {
			getBoardState();
			if (getPlayerFlag() == 1 && BoardState.boardArr[x][y] == 0) {
				danhCo(x, y, 1, a);
				setPlayerFlag(2);
			} else {
				getBoardState();
				if (getPlayerFlag() == 2 && BoardState.boardArr[x][y] == 0) {
					danhCo(x, y, 2, a);
					setPlayerFlag(1);
				}
			}

		} else {
			if (getPlayerFlag() == 1) {
				if (getBoardState().getPosition(x, y) == 0) {
					danhCo(x, y, 1, a);
					setPlayerFlag(2);
				}
			}
			if (getPlayerFlag()== 2) {
					Point p = AI(2);
					danhCo(p.x, p.y, 2, a);
					setPlayerFlag(1);
			}
		}
		if (end) {
			if (player instanceof ComputerPlayer && playerWin.equals("2")) {
				playerWin = "Computer";
			}
			timer1.cancel();
			timer2.cancel();
			
			dialog("Player " + playerWin + " win!");
			return;
		}
		runTimer(getPlayerFlag());
	}


	public void danhCo(int x, int y, int player, Button[][] arrayButtonChess) {
		getBoardState().setPosition(x, y, player);
		if (player == 1) {
			arrayButtonChess[x][y].setGraphic(new ImageView(imageX));
			Point point = new Point(x, y);
			point.setPlayer(1);
			stack.push(point);
			tongNuocDi++;
		} else {
			arrayButtonChess[x][y].setGraphic(new ImageView(imageO));
			Point point = new Point(x, y);
			point.setPlayer(2);
			stack.push(point);
			tongNuocDi++;
		}
		if (getBoardState().checkEnd(x, y) == player) {
			playerWin = player + "";
			end = true;
		}
		if (tongNuocDi == (getBoardState().height * getBoardState().width)) {
			playerWin = 2 + "";
			end = true;
		}

	}

	void print(int[][] a) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				System.out.print(a[i][j]);
			}
			System.out.println();
		}
	}
	
	// quay lại 1 nuoc co
	public void undo(Button[][] arrayButtonChess) {
		if (!stack.isEmpty()) {
			tongNuocDi--;
			Point point = stack.pop();
			getBoardState();
			BoardState.boardArr[point.x][point.y] = 0;
			arrayButtonChess[point.x][point.y].setGraphic(null);
		}
	}
	public void setPlayer(Player player) {
		this.player = player;
	}

	public EventHandler<ActionEvent> action(String action) {
		return null;
	}

	EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent e) {

		}
	};

	public void dialog(String title) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Trò chơi kết thúc");
		alert.setHeaderText(title);
		alert.setContentText("Bạn có muốn chơi lại");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			if (getPlayer() instanceof HumanPlayer) {
				view.replayHuman();
			} else {
				view.replayComputer();
			}
		} else {
			// su dung khi chon khong hoac dong hoi thoai
		}
	}

	public void setView(View view) {
		this.view = view;
	}

	public void setEnd(boolean end) {
		this.end = end;
	}

	public Player getPlayer() {
		return player;
	}

	public void reset(Button[][] arrayButtonChess) {
		tongNuocDi = 0;
		timer1.cancel();
		timer2.cancel();
		timePlayer1.setText("30");
		timePlayer2.setText("30");
		getBoardState().resetBoard();
		for (int i = 0; i < arrayButtonChess.length; i++) {
			for (int j = 0; j < arrayButtonChess[i].length; j++) {
				arrayButtonChess[i][j].setGraphic(null);
			}
		}
	}

	Labeled timePlayer1, timePlayer2;

	public void setTimePlayer(Labeled timePlayer1, Labeled timePlayer2) {
		this.timePlayer1 = timePlayer1;
		this.timePlayer2 = timePlayer2;
	}
	Timer timer1 = new Timer();
	Timer timer2 = new Timer();
	public void runTimer(int player) {
		if(end){
			timer1.cancel();
			timer2.cancel();
		}else{
			timer1.cancel();
			timer2.cancel();
			TaskTimer task1 = new TaskTimer(timePlayer1);
			TaskTimer task2 = new TaskTimer(timePlayer2);
			task1.setController(this);
			task2.setController(this);
			if (player == 1) {
				timer2.cancel();
				timer1 = new Timer();
				timer1.schedule(task1, 0, 1000);
			} else {
				timer1.cancel();
				timer2 = new Timer();
				timer2.schedule(task2, 0, 1000);
			}
		}
	}

}

