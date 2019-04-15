package view;

/**
 * This class holds all necessary values and fields for keeping the main GUI
 * of the game intact. 
 *
 * @author Jamie David, Ryan McCommon, Alexis Tinoco, Carl Wernicke
 * 
 * */

import java.io.File;
import java.net.URI;

import enemy.Enemy;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import mainModel.MainGame;
import tower.Tower;

public class MainGUI extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	private final int SCENE_WIDTH = 1000;
	private final int SCENE_HEIGHT = 700;

	private Stage mainStage;
	private MainGame game;
	private MenuBar menuBar;
	private BorderPane pane;
	private String currentTowerSelection;
	private Enemy selectedEnemy;
	private Label goldAmount;
	private boolean ghostTowerDrawn;
	private Image infoImage = null;
	private Label infoName = new Label("");
	private Label infoDesc = new Label("");
	private ImageView infoImageView = new ImageView();
	private Label gameOver = new Label("Game Over");
	private Label gameWon = new Label("Game Won");
	private Label baseHealth;
	private Canvas canvas;
	private Timeline timeline;
	private int gameSpeedMilliseconds;
	private double mouseX;
	private double mouseY;
	private MediaPlayer player;
	private boolean isEnemyCurrentlySelected;
	private int currentRate;

	// Radio buttons
	RadioButton fireTowerToggle = new RadioButton("Fire Tower");
	RadioButton waterTowerToggle = new RadioButton("Water Tower");
	RadioButton iceTowerToggle = new RadioButton("Ice Tower");


	@Override
	public void start(Stage primaryStage) throws Exception {
		mainStage = primaryStage;
		mainStage.setTitle("335 Defenders");
		mainStage.setScene(StartScreen());
		mainStage.show();
		isEnemyCurrentlySelected = false;
		
		/*
		 * Close all threads on exit
		 */
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
      @Override
      public void handle(WindowEvent e) {
         Platform.exit();
         System.exit(0);
      }
   });
		
		//rate at which the game plays
		gameSpeedMilliseconds = 100;
		
		//Begin the game animations
		timeline = new Timeline(new KeyFrame(Duration.millis(gameSpeedMilliseconds), new AnimateStarter()));
        timeline.setCycleCount(Animation.INDEFINITE);
	}

	private class AnimateStarter implements EventHandler<ActionEvent> {

    
    public AnimateStarter() {
    }

    /**
     * Handles the start of a map and general game loop
     * */
    @Override
    public void handle(ActionEvent event) {
    	baseHealth.setText(game.getGameMap().getBaseHealth() + "");
    	if(game.getWaveInProg() && game.getGameMap().getGameLost() == false) {
		    ghostTowerDrawn = false;
		    game.moveEnemies();
			game.checkWaveOver();
    		
    		game.towersAttack();
    		
	    	//Do all the drawing
	        game.getGameMap().drawMap();
	  		game.drawTowers(canvas);
	  		updateLabels();
	  		int newMoney = game.removeDeadEnemies() * 10;
	  		if(newMoney!=0) {
	  			game.getInventory().setGold(game.getInventory().getGold() + newMoney);
	  			goldAmount.setText("$" + game.getInventory().getGold());
	  		}
	  		drawGhostTower();
	  		
	  		if(isEnemyCurrentlySelected==true) {
	  			
	  		}
	  		canvas = game.drawEnemies(canvas);
	  		pane.setCenter(canvas);
    	}
    	
    	//If the conditions for a loss are met
    	else if(game.getGameMap().getGameLost() == true) {
    		pane.setCenter(gameOver);
    		System.out.println("YOU LOST");
    		timeline.stop();
    		player.stop();
    		playLoseSound();
    	}
    	//If the conditions for a win are met
    	else {
    		pane.setCenter(gameWon);
    		System.out.println("YOU WON");
    		timeline.stop();
    		player.stop();
    		playWinSound();
    	}
    }
        /**
         * Updates the description label for an enemy
         * */
		private void updateLabels() {
			if(selectedEnemy == null) {
				isEnemyCurrentlySelected = false;
				return;
			}
			
			if(isEnemyCurrentlySelected==true) {
  			if(selectedEnemy.isDead() == false) {
  				infoDesc.setText(selectedEnemy.toString());
  				}
  			else {
  				infoName.setText("Enemy has died");
  				infoImageView.setVisible(false);
  				infoDesc.setVisible(false);
  				isEnemyCurrentlySelected = false;
  			}
  		}
		}
  }

	/**
	 * Switches the scene of the stage into the scene with the actual game
	 * and also starts the game.
	 */
	private void startGameScene(int mapSel) {
		initializeGame(mapSel);
		pane = new BorderPane();
		Scene scene = new Scene(pane, SCENE_WIDTH, SCENE_HEIGHT);

		// Have options at top for New Game, etc.
		setupMenus();
		pane.setTop(menuBar);

		// Show the map at the center
		canvas = game.getGameMap().getCanvas();

		pane.setCenter(canvas);
		game.startWave();
		//Upgrade Towers Button
		Button upgradeButton = new Button("");
		upgradeButton.setVisible(false);
		//Info Display
		GridPane infoPane = new GridPane();
		GridPane innerPane = new GridPane();
		infoPane.add(innerPane, 1, 0);
		infoPane.add(infoImageView, 0, 0);
		innerPane.add(infoName, 0, 0);
		infoImageView.setFitWidth(50);
		infoImageView.setFitHeight(50);
		innerPane.add(infoDesc, 0, 1);
		innerPane.add(upgradeButton, 1, 1);
		pane.setLeft(infoPane);
		
		
		//Pause and Double Speed buttons
		Button pause = new Button("Pause");
		pause.setOnAction(new PauseHandler());
		
		Button speed = new Button("Fast Speed");
		speed.setOnAction(new FastSpeedHandler());
		
		// Display available Towers and gold at bottom of the screen
		HBox towerHBox = setupRadioButtons();
		// Label to display gold amount
		goldAmount = new Label("$" + game.getInventory().getGold());
		baseHealth = new Label(game.getGameMap().getBaseHealth() + "");
		HBox goldLabelHBox = new HBox(new Label("Gold Available: "), goldAmount, new Label("  Base Health:"), baseHealth);
		pane.setBottom(new HBox(new VBox(speed, pause, goldLabelHBox, towerHBox), infoPane));
		
		//when the mouse moves
		canvas.setOnMouseMoved(event -> {
			mouseX = event.getX();
			mouseY = event.getY();
		});
		
		//when the mouse is clicked on the canvas
		canvas.setOnMouseClicked(event -> {
			double startingX, startingY;
			
			int tileSize = 35;
			double tileX = Math.floor(event.getX() / tileSize);
			double tileY = Math.floor(event.getY() / tileSize);
			System.out.println("X:" + tileX + "\nY:"+tileY);
			startingX = tileSize * (tileX);
			startingY = tileSize * (tileY);
			
			//If the player clicks on a built tower, show information and 
			//give the option to upgrade
			if (currentTowerSelection == null) {
				Tower selectedTower = game.getTowerSelection((int) startingX, (int) startingY);
				if (selectedTower != null) {
					//set tower information
					game.unselectEnemies();
					isEnemyCurrentlySelected = false;
					infoImage = selectedTower.getImage();
					infoName.setText(selectedTower.getName());
					infoDesc.setText(selectedTower.getDescription());
					infoImageView.setImage(infoImage);
					infoName.setVisible(true);
					infoDesc.setVisible(true);
					infoImageView.setVisible(true);
					
					//if the tower is not upgraded, provide an upgrade button
					if (selectedTower.isNotUpgraded()) {
						upgradeButton.setVisible(true);
						if (game.getInventory().canAfford(selectedTower.getUpgradeCost())) {
							upgradeButton.setText("Upgrade\nFor $" + selectedTower.getUpgradeCost());
							upgradeButton.setOnAction(buttonEvent -> {
								selectedTower.upgrade();
								infoImageView.setImage(selectedTower.getImage());
								infoDesc.setText(selectedTower.getDescription());
								upgradeButton.setVisible(false);
								game.getInventory().subtractGold(selectedTower.getUpgradeCost());
								goldAmount.setText("$" + game.getInventory().getGold());
						    });
						} else {
							upgradeButton.setOnAction(badEvent -> {});
							upgradeButton.setText("Can't Afford\n Upgrade");
						}
					}
				}
			}
			
			boolean isThereATowerSelected = false;
			if(fireTowerToggle.isSelected() == true || waterTowerToggle.isSelected() == true || iceTowerToggle.isSelected() == true) {
				isThereATowerSelected = true;
			}
			
			//Enemy Selection Code
			if(!game.canBuild((int)tileX, (int)tileY) || isThereATowerSelected == false) {
				selectedEnemy = game.returnSelectedEnemy(event.getX(), event.getY());
				if(selectedEnemy!= null) {
					infoName.setText(selectedEnemy.getName());
					infoDesc.setText(selectedEnemy.toString());
					infoImageView.setImage(selectedEnemy.getCharacterImg());
					infoImageView.setVisible(true);
					infoName.setVisible(true);
					infoDesc.setVisible(true);
					isEnemyCurrentlySelected = true;
					
					//Hide tower upgrade button
					upgradeButton.setVisible(false);
					return;
				}
			}
			//Ends Enemy Selection Code
			if(isThereATowerSelected == false)
				return;
			
			
			//if the player can build a tower here
			if (game.canBuild((int) tileX, (int) tileY)) {
				//build the correct type of tower that's being selected
				if (currentTowerSelection.toLowerCase().equals("fire tower")) {
					if (game.getInventory().canAfford(60)) {
						// Make Tower
						game.createFireTower(canvas, startingX, startingY);
						goldAmount.setText("$" + game.getInventory().getGold());
						currentTowerSelection = null;
						fireTowerToggle.setSelected(false);
						return;
					}
				} else if (currentTowerSelection.toLowerCase().equals("water tower")) {
					if (game.getInventory().canAfford(30)) { // Make Tower
						game.createWaterTower(canvas, startingX, startingY);
						goldAmount.setText("$" + game.getInventory().getGold());
						currentTowerSelection = null;
						waterTowerToggle.setSelected(false);
						return;
					}
				} else if (currentTowerSelection.toLowerCase().equals("ice tower")) {
					if (game.getInventory().canAfford(90)) {
						// Make Tower
						game.createIceTower(canvas, startingX, startingY);
						goldAmount.setText("$" + game.getInventory().getGold());
						currentTowerSelection = null;
						iceTowerToggle.setSelected(false);
						return;
					}
				}
				System.out.println("Not enough gold to purchase " + currentTowerSelection);
			} else {
				System.out.println("Can't build here");
			}
		});
		mainStage.setScene(scene);
		mainStage.show();
	}

	/**
	 * Creates a scene for the start screen which will contain the main menu of
	 * the game on start.
	 * 
	 * @return Scene of Start Screen
	 */
	private Scene StartScreen() {
		BorderPane mainMenuPane = new BorderPane();
		Scene mainMenuScene = new Scene(mainMenuPane, SCENE_WIDTH, SCENE_HEIGHT);
		mainMenuPane.setStyle("-fx-background-color: #333");

		Text title = new Text("335 DEFENDERS!");
		title.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 70));
		// Setting the color
		title.setFill(Color.GOLDENROD);
		// Setting the Stroke
		title.setStrokeWidth(2);
		// Setting the stroke color
		title.setStroke(Color.GHOSTWHITE);
		title.setTextAlignment(TextAlignment.CENTER);
		title.setWrappingWidth(SCENE_WIDTH);
		VBox textBox = new VBox(title);
		VBox.setMargin(title, new Insets(100, 0, 0, 0));
		mainMenuPane.setTop(textBox);

		double buttonWidth = 300;
		double buttonHeight = 200;
		
		//Set action handlers to map buttons
		Button mapOne = new Button("Map One");
		mapOne.setPrefSize(buttonWidth, buttonHeight);
		mapOne.setOnAction(event -> {
			
			startGameScene(1);
			timeline.play();
		});
		Button mapTwo = new Button("Map Two");
		mapTwo.setOnAction(event -> {
			
			startGameScene(2);
			timeline.play();
		});
		mapTwo.setPrefSize(buttonWidth, buttonHeight);
		Button mapThree = new Button("Map Three");
		mapThree.setOnAction(event -> {
			
			startGameScene(3);
			timeline.play();
		});
		mapThree.setPrefSize(buttonWidth, buttonHeight);
		
		//setup the HBox of buttons which Link to different game maps
		HBox mapSelectButtons = new HBox(15, mapOne, mapTwo, mapThree);
		mapSelectButtons.setAlignment(Pos.CENTER);
		mainMenuPane.setCenter(mapSelectButtons);

		Text description = new Text("Click a map to play to get start the game");
		description.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		// Setting the color
		description.setFill(Color.GOLDENROD);
		description.setTextAlignment(TextAlignment.CENTER);
		description.setWrappingWidth(SCENE_WIDTH);
		mainMenuPane.setBottom(description);

		return mainMenuScene;
	}

	// Sets up the menu for starting a new game
	private void setupMenus() {
		MenuItem newGame = new MenuItem("Start Screen");
		Menu options = new Menu("Options");
		options.getItems().add(newGame);

		menuBar = new MenuBar();
		menuBar.getMenus().add(options);

		// Add the listener to menu item requiring action
		newGame.setOnAction(new MenuItemListener());
	}

	// Sets up the radio buttons for the Towers
	private HBox setupRadioButtons() {
		// Add radio buttons to a toggle group
		ToggleGroup radioGroup = new ToggleGroup();
		// Set user data for each radio button
		fireTowerToggle.setToggleGroup(radioGroup);
		fireTowerToggle.setUserData("Fire Tower");
		waterTowerToggle.setToggleGroup(radioGroup);
		waterTowerToggle.setUserData("Water Tower");
		iceTowerToggle.setToggleGroup(radioGroup);
		iceTowerToggle.setUserData("Ice Tower");

		// Handle the toggle with this event listener
		radioGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
				if (radioGroup.getSelectedToggle() != null) {
					currentTowerSelection = radioGroup.getSelectedToggle().getUserData().toString();
				}
			}
		});
		return new HBox(fireTowerToggle, waterTowerToggle, iceTowerToggle);
	}

	/**
	 * Set the game to the default map
	 */
	public void initializeGame(int mapSel) {
		game = new MainGame();
		game.setMap(mapSel);
		startMainTheme();
	}

	// This class handles the ActionEvents for the menu
	private class MenuItemListener implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {
			// Find out the text of the JMenuItem that was just clicked
			String text = ((MenuItem) e.getSource()).getText();
			if (text.equals("New Game"))
		        timeline.stop();
				player.stop();
				//set the stage scene to the main menu
				mainStage.setScene(StartScreen());
										
		} // END METHOD HANDLE
	} // END CLASS MENUITEMLISTENER

	/**
	 * Will draw a ghost tower image onto the canvas where the players mouse is
	 * Only applies when the player has selected a type of tower to build.
	 * */
	private void drawGhostTower() {
		
		//if there's no tower selection, then do nothing
		if (currentTowerSelection == null) {
			return;
		}
		
		double startingX, startingY;
		int tileSize = 35;
		double tileX = Math.floor(mouseX / tileSize);
		double tileY = Math.floor(mouseY / tileSize);
		
		startingX = tileSize * (tileX);
		startingY = tileSize * (tileY);
		if (game.canBuild((int) tileX, (int) tileY) && !ghostTowerDrawn) {
			
			//this next variable prevents ghost towers from being draw on multiple spots
			ghostTowerDrawn = true;
			
			//draw the appropriate type of tower
			if (currentTowerSelection.toLowerCase().equals("fire tower")) {
				game.drawGhostFireTower(canvas, startingX, startingY);
			} else if (currentTowerSelection.toLowerCase().equals("water tower")) {
				game.drawGhostWaterTower(canvas, startingX, startingY);
			} else if (currentTowerSelection.toLowerCase().equals("ice tower")) {
				game.drawGhostIceTower(canvas, startingX, startingY);
			}
		}
	}
	
	//begin playing the main theme
	private void startMainTheme() {
		File file = new File("sound/Main Theme.wav");
		URI uri = file.toURI();
		Media media = new Media(uri.toString());
		player = new MediaPlayer(media);
		player.setVolume(0.05);
		player.play();
	}
	
	//Play the winning sound
	private void playWinSound() {
		File file = new File("sound/Win.wav");
		URI uri = file.toURI();
		Media media = new Media(uri.toString());
		player = new MediaPlayer(media);
		player.setVolume(1);
		player.play();
	}
	
	//Play the losing sound
	private void playLoseSound() {
		File file = new File("sound/Lose.wav");
		URI uri = file.toURI();
		Media media = new Media(uri.toString());
		player = new MediaPlayer(media);
		player.setVolume(1);
		player.play();
	}
	
	
	/**
	 * Handlers to regulate the pausing of the game. The handler of the pause button
	 * gets swapped everytime the button is pressed.
	 * 
	 * */
	private class PauseHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent ae) {
			((Button) ae.getSource()).setOnAction(new ResumeHandler());
			((Button) ae.getSource()).setText("Resume");
			
			//pause everything
			player.pause();
			Tower.pause();
			MainGUI.this.timeline.pause();
		}
	}
	
	private class ResumeHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent ae) {
			((Button) ae.getSource()).setOnAction(new PauseHandler());
			((Button) ae.getSource()).setText("Pause");
			
			//resume everything
			player.play();
			Tower.unpause();
			MainGUI.this.timeline.play();
		}
	}
	/**
	 * Handlers to regulate the speed of the game. The handler of the speed button
	 * gets swapped everytime the user clicks it
	 * 
	 * */
	private class FastSpeedHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent ae) {
			//if the game is paused, don't increase speed
			if (MainGUI.this.timeline.getRate() == 0)
				return;
			
			//switch the handler and set appropriate button text
			((Button) ae.getSource()).setOnAction(new NormalSpeedHandler());
			((Button) ae.getSource()).setText("Normal Speed");
			
			//speedup the tower attacks and speedup the rate of the animations
			Tower.speedUp();
			currentRate = 2;
			MainGUI.this.timeline.setRate(2);	
		}
	}
	
	private class NormalSpeedHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent ae) {
			//if the game is paused, don't increase speed
			if (MainGUI.this.timeline.getRate() == 0)
				return;
			
			//switch the handler and set appropriate button text
			((Button) ae.getSource()).setOnAction(new FastSpeedHandler());
			((Button) ae.getSource()).setText("Fast Speed");
			
			//slow down the tower attacks and speedup the rate of the animations
			Tower.slowDown();
			currentRate = 1;
			MainGUI.this.timeline.setRate(1);
		}
	}
}
