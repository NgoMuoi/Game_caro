/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import view.View;
/**
 *
 * @author win 10pro
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
			View view = new View();
			view.start(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public void handle(WindowEvent event) {
		System.exit(0);
	}
    }
    
