package GraphicAndLogic;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;


public class Controller
{
    private final static int SQUARE_SIDE_LENGTH = 80;
    private final static int BLANK_IN_ROWS = 50;
    private final static int BLANK_IN_COLUMNS = 50;
    private final static int SCORE__SHOWING_SPACE = 200;

    private Group rootSignUpMenu = new Group();
    private Scene sceneSignUpMenu = new Scene(rootSignUpMenu, 400, 400);
    private Group rootLoginMenu = new Group();
    private Scene sceneLoginMenu = new Scene(rootLoginMenu, 400, 400);
    private Group rootMainMenu = new Group();
    private Scene sceneMainMenu = new Scene(rootMainMenu, 400, 450);
    private Group rootChangeName = new Group();
    private Scene sceneChangeName = new Scene(rootChangeName, 300, 300);
    private Group rootChangeTable = new Group();
    private Scene sceneChangeTable = new Scene(rootChangeTable, 400, 400);
    private Group rootRanking = new Group();
    private Scene sceneRanking = new Scene(rootRanking, 300, 700);
    private Group rootGameOver = new Group();
    private Scene sceneGameOver = new Scene(rootGameOver, 300, 300);

    public void signUpMenu(Stage primaryStage) throws Exception
    {
        rootSignUpMenu.getChildren().clear();

        TextField textFieldName = new TextField();
        TextField textFieldPassword = new TextField();
        nameAndPasswordFields(rootSignUpMenu, textFieldName, textFieldPassword);

        Label labelSignUp = new Label("Sign Up");
        rootSignUpMenu.getChildren().add(labelSignUp);
        labelSignUp.setFont(Font.font(25));
        labelSignUp.relocate(150, 30);
        labelSignUp.setTextFill(Color.BLACK);

        Button buttonSignUp = new Button("Submit");
        Label labelInvalidInput = new Label();
        submitButton(buttonSignUp, labelInvalidInput);
        buttonSignUp.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                rootSignUpMenu.getChildren().remove(labelInvalidInput);
                String name = textFieldName.getText();
                String password = textFieldPassword.getText();
                if (name.isEmpty() || password.isEmpty())
                {
                    rootSignUpMenu.getChildren().add(labelInvalidInput);
                    labelInvalidInput.setText("you must Fill both TextFields");
                    return;
                }
                Player player = Player.findPlayer(name);
                if (player == null)
                {
                    rootSignUpMenu.getChildren().remove(labelInvalidInput);
                    player = new Player(name, password);
                    try
                    {
                        player.savePlayerInfo(player, name, true);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    primaryStage.setScene(sceneLoginMenu);
                    primaryStage.centerOnScreen();
                    login(primaryStage);
                }
                else
                {
                    labelInvalidInput.setText("Player exists with this name");
                    rootSignUpMenu.getChildren().add(labelInvalidInput);
                }
            }
        });
        rootSignUpMenu.getChildren().add(buttonSignUp);

        Button buttonAlreadyHaveAccount = new Button("Already have account");
        buttonAlreadyHaveAccount.relocate(150, 300);
        buttonAlreadyHaveAccount.setFont(Font.font(20));
        buttonAlreadyHaveAccount.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                rootSignUpMenu.getChildren().remove(labelInvalidInput);
                primaryStage.setScene(sceneLoginMenu);
                primaryStage.centerOnScreen();
                login(primaryStage);
            }
        });
        rootSignUpMenu.getChildren().add(buttonAlreadyHaveAccount);

        primaryStage.setScene(sceneSignUpMenu);
        primaryStage.setTitle("2048");
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public void login(Stage primaryStage)
    {
        rootLoginMenu.getChildren().clear();

        Label labelLogin = new Label("Login");
        rootLoginMenu.getChildren().add(labelLogin);
        labelLogin.relocate(150, 30);
        labelLogin.setFont(Font.font(25));
        labelLogin.setTextFill(Color.BLACK);

        TextField textFieldName = new TextField();
        TextField textFieldPassword = new TextField();
        nameAndPasswordFields(rootLoginMenu, textFieldName, textFieldPassword);

        Button buttonLogin = new Button("Submit");
        Label labelInvalidInput = new Label();
        submitButton(buttonLogin, labelInvalidInput);
        buttonLogin.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                rootLoginMenu.getChildren().remove(labelInvalidInput);
                String name = textFieldName.getText();
                String password = textFieldPassword.getText();
                if (name.isEmpty() || password.isEmpty())
                {
                    labelInvalidInput.setText("you must Fill both TextFields");
                    rootLoginMenu.getChildren().add(labelInvalidInput);
                    return;
                }
                Player player = Player.findPlayer(name);
                if (player == null)
                {
                    labelInvalidInput.setText("Invalid name or password");
                    rootLoginMenu.getChildren().add(labelInvalidInput);
                }
                else if (player.getPassword().equals(password))
                {
                    Player.login(player);
                    new Game();
                    rootSignUpMenu.getChildren().remove(labelInvalidInput);
                    primaryStage.setScene(sceneMainMenu);
                    primaryStage.centerOnScreen();
                    try
                    {
                        mainMenu(primaryStage);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    labelInvalidInput.setText("Password is Wrong.Try again");
                    rootLoginMenu.getChildren().add(labelInvalidInput);
                }
            }
        });
        rootLoginMenu.getChildren().add(buttonLogin);

        Button buttonNeedToSignUp = new Button("Sign Up");
        buttonNeedToSignUp.relocate(260, 300);
        buttonNeedToSignUp.setFont(Font.font(20));
        buttonNeedToSignUp.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                rootSignUpMenu.getChildren().remove(labelInvalidInput);
                primaryStage.setScene(sceneSignUpMenu);
                primaryStage.centerOnScreen();
                try
                {
                    signUpMenu(primaryStage);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        rootLoginMenu.getChildren().add(buttonNeedToSignUp);
    }

    public void mainMenu(Stage primaryStage) throws Exception
    {
        Label labelMainMenu = new Label("Welcome to 2048");
        rootMainMenu.getChildren().add(labelMainMenu);
        labelMainMenu.relocate(50, 0);
        labelMainMenu.setFont(Font.font(40));
        labelMainMenu.setTextFill(Color.BLACK);

        setMainMenuText(primaryStage, "Play", 80);
        setMainMenuText(primaryStage, "Set Game Table", 140);
        setMainMenuText(primaryStage, "Change Name", 200);
        setMainMenuText(primaryStage, "Ranking", 260);
        setMainMenuText(primaryStage, "Logout", 320);
        setMainMenuText(primaryStage, "Exit", 380);
    }

    private void setMainMenuText(Stage primaryStage, String text, int y)
    {
        Text mainMenuText = new Text(text);
        mainMenuText.setTextOrigin(VPos.TOP);
        mainMenuText.setFont(Font.font(null, FontWeight.SEMI_BOLD, 25));
        mainMenuText.layoutXProperty().bind(sceneMainMenu.widthProperty().subtract(mainMenuText.prefWidth(-1)).divide(2));
        mainMenuText.setY(y);
        mainMenuText.setFill(Color.BLUE);
        mainMenuText.setOnMouseEntered(event -> mainMenuText.setFill(Color.RED));
        mainMenuText.setOnMouseExited(event -> mainMenuText.setFill(Color.BLUE));
        mainMenuText.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                try
                {
                    switch (text)
                    {
                        case "Play":
                            GameStartingPrimarySettings(primaryStage);
                            break;
                        case "Set Game Table":
                            primaryStage.setScene(sceneChangeTable);
                            primaryStage.centerOnScreen();
                            changeTable(primaryStage);
                            break;
                        case "Change Name":
                            primaryStage.setScene(sceneChangeName);
                            primaryStage.centerOnScreen();
                            changeName(primaryStage);
                            break;
                        case "Ranking":
                            primaryStage.setScene(sceneRanking);
                            primaryStage.centerOnScreen();
                            ranking(primaryStage);
                            break;
                        case "Logout":
                            primaryStage.setScene(sceneLoginMenu);
                            login(primaryStage);
                            break;
                        case "Exit":
                            primaryStage.close();
                            break;
                    }
                }
                catch (Exception ignored)
                {

                }
            }
        });
        rootMainMenu.getChildren().add(mainMenuText);
    }

    private void changeName(Stage primaryStage)
    {
        Label labelNewName = new Label("Enter new name");
        labelNewName.relocate(50, 30);
        labelNewName.setFont(Font.font(30));
        labelNewName.setTextFill(Color.YELLOWGREEN);
        rootChangeName.getChildren().add(labelNewName);

        TextField textFieldNewName = new TextField();
        textFieldNewName.relocate(80, 130);
        rootChangeName.getChildren().add(textFieldNewName);

        Label labelInputConditionCommand = new Label();
        labelInputConditionCommand.setFont(Font.font(20));

        Button buttonNewName = new Button("Submit");
        buttonNewName.setFont(Font.font(25));
        buttonNewName.relocate(20, 200);
        buttonNewName.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                rootChangeName.getChildren().remove(labelInputConditionCommand);
                String newName = textFieldNewName.getText();
                if (newName.isEmpty())
                {
                    labelInputConditionCommand.setText("TextField is Empty");
                    labelInputConditionCommand.setTextFill(Color.RED);
                    labelInputConditionCommand.relocate(75, 80);
                    rootChangeName.getChildren().add(labelInputConditionCommand);
                    return;
                }
                Player player = Player.findPlayer(newName);
                if (player != null)
                {
                    labelInputConditionCommand.setText("Player exists with this name");
                    labelInputConditionCommand.setTextFill(Color.RED);
                    labelInputConditionCommand.relocate(30, 80);
                    rootChangeName.getChildren().add(labelInputConditionCommand);
                    return;
                }
                Player.getLoggedInPlayer().setName(newName);
                labelInputConditionCommand.setText("Name changed successfully");
                labelInputConditionCommand.setTextFill(Color.GREEN);
                labelInputConditionCommand.relocate(30, 80);
                rootChangeName.getChildren().add(labelInputConditionCommand);
            }
        });
        rootChangeName.getChildren().add(buttonNewName);

        backButton(primaryStage, rootChangeName, 190, 200);
    }

    private void changeTable(Stage primaryStage) throws Exception
    {
        Label labelChangeTable = new Label("Enter your desired number of rows and columns");
        rootChangeTable.getChildren().add(labelChangeTable);
        labelChangeTable.relocate(10, 30);
        labelChangeTable.setFont(Font.font(18));
        labelChangeTable.setTextFill(Color.GREEN);

        Label labelRow = new Label("Num of rows: ");
        rootChangeTable.getChildren().add(labelRow);
        labelRow.relocate(10, 120);
        labelRow.setFont(Font.font(20));
        labelRow.setTextFill(Color.BLACK);

        Label labelColumn = new Label("Num of columns: ");
        rootChangeTable.getChildren().add(labelColumn);
        labelColumn.relocate(10, 180);
        labelColumn.setFont(Font.font(20));
        labelColumn.setTextFill(Color.BLACK);

        TextField textFieldRow = new TextField();
        textFieldRow.setPromptText("Number of Rows");
        HBox hBoxRow = new HBox(textFieldRow);
        hBoxRow.relocate(200, 120);
        rootChangeTable.getChildren().add(hBoxRow);

        TextField textFieldColumn = new TextField();
        textFieldColumn.setPromptText("Number of Columns");
        HBox hBoxColumn = new HBox(textFieldColumn);
        hBoxColumn.relocate(200, 180);
        rootChangeTable.getChildren().add(hBoxColumn);

        Button buttonApplyTableChange = new Button("Set new Table");
        Label inputIsInvalid = new Label();
        buttonApplyTableChange.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                rootChangeTable.getChildren().remove(inputIsInvalid);
                try
                {
                    int numOfRows = Integer.parseInt(textFieldRow.getText());
                    int numOfColumns = Integer.parseInt(textFieldColumn.getText());
                    if (numOfRows > 3 && numOfRows < 7 && numOfColumns > 3 && numOfColumns < 7)
                    {
                        Game.changeDefaultTable(numOfRows, numOfColumns);
                        primaryStage.setScene(sceneMainMenu);
                        mainMenu(primaryStage);
                    }
                    else
                    {
                        rootChangeTable.getChildren().add(inputIsInvalid);
                        inputIsInvalid.setText("Numbers must be between 4 and 6");
                        inputIsInvalid.relocate(50, 250);
                        inputIsInvalid.setFont(Font.font(18));
                        inputIsInvalid.setTextFill(Color.RED);
                    }
                }
                catch (NumberFormatException exception)
                {
                    rootChangeTable.getChildren().add(inputIsInvalid);
                    inputIsInvalid.setText("Input in invalid.Please enter numbers");
                    inputIsInvalid.relocate(50, 250);
                    inputIsInvalid.setFont(Font.font(18));
                    inputIsInvalid.setTextFill(Color.RED);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        buttonApplyTableChange.setFont(Font.font(25));
        buttonApplyTableChange.relocate(30, 300);
        backButton(primaryStage, rootChangeTable, 280, 300);
        rootChangeTable.getChildren().add(buttonApplyTableChange);
    }

    public void ranking(Stage primaryStage) throws Exception
    {
        Label labelTop10 = new Label("Top 10");
        labelTop10.setTextFill(Color.YELLOW);
        labelTop10.setFont(Font.font(30));
        labelTop10.relocate(100, 0);
        rootRanking.getChildren().clear();
        rootRanking.getChildren().add(labelTop10);
        Player.sortPlayers();
        showRankingPlayers();
        backButton(primaryStage, rootRanking, 100, 600);
    }

    public void showRankingPlayers()
    {
        int counter = 1;
        for (Player player : Player.getPlayers())
        {
            if (counter > 10)
            {
                return;
            }
            Label labelPlayerName = new Label(counter + "- " + player.getName());
            labelPlayerName.setFont(Font.font(15));
            labelPlayerName.relocate(25, counter * 50);
            rootRanking.getChildren().add(labelPlayerName);

            Label labelPlayerHighScore = new Label(Integer.toString(player.getHighScore()));
            labelPlayerHighScore.setFont(Font.font(15));
            labelPlayerHighScore.relocate(250, counter * 50);
            rootRanking.getChildren().add(labelPlayerHighScore);

            counter ++;
        }
    }

    private void GameStartingPrimarySettings(Stage primaryStage) throws Exception
    {
        Game game = new Game();
        Group rootGameGraphics = new Group();
        Scene sceneInsideTheGame = new Scene(rootGameGraphics, game.getTableRow() * SQUARE_SIDE_LENGTH + 2 * BLANK_IN_ROWS + SCORE__SHOWING_SPACE, game.getTableColumn() * SQUARE_SIDE_LENGTH + 2 * BLANK_IN_COLUMNS);
        primaryStage.setScene(sceneInsideTheGame);
        primaryStage.centerOnScreen();
        gameGraphics(primaryStage, rootGameGraphics);
    }

    private void gameGraphics(Stage primaryStage, Group rootGameGraphics) throws Exception
    {
        Game game = Game.getCurrentGame();
        for (int i=0;i < game.getTableRow();i++)
        {
            for (int j=0;j < game.getTableColumn();j++)
            {
                game.getGameTable()[i][j] = new Cell();

                Rectangle square = new Rectangle(SQUARE_SIDE_LENGTH, SQUARE_SIDE_LENGTH);
                square.setFill(Color.BEIGE);
                square.setStroke(Color.BLACK);
                game.getGameTable()[i][j].setSquare(square);
                rootGameGraphics.getChildren().add(game.getGameTable()[i][j].getSquare());

                Label number = new Label();
                number.setFont(Font.font(25));
                game.getGameTable()[i][j].setNumber(number);
                rootGameGraphics.getChildren().add(game.getGameTable()[i][j].getNumber());

                StackPane stackPane = new StackPane(square, number);
                stackPane.relocate(i * SQUARE_SIDE_LENGTH + BLANK_IN_ROWS, j * SQUARE_SIDE_LENGTH + BLANK_IN_COLUMNS);
                game.getGameTable()[i][j].setStackPane(stackPane);
                rootGameGraphics.getChildren().add(game.getGameTable()[i][j].getStackPane());
            }
        }

        Label label2048 = new Label("2048");
        rootGameGraphics.getChildren().add(label2048);
        label2048.relocate(game.getTableRow() * SQUARE_SIDE_LENGTH + BLANK_IN_ROWS + 80, 30);
        label2048.setFont(Font.font(50));
        label2048.setTextFill(Color.YELLOW);

        Label labelHighScore = new Label("HighScore");
        rootGameGraphics.getChildren().add(labelHighScore);
        labelHighScore.relocate(game.getTableRow() * SQUARE_SIDE_LENGTH + BLANK_IN_ROWS + 90, 140);
        labelHighScore.setFont(Font.font(20));
        labelHighScore.setTextFill(Color.BLACK);

        Label labelScore = new Label("Score");
        rootGameGraphics.getChildren().add(labelScore);
        labelScore.relocate(game.getTableRow() * SQUARE_SIDE_LENGTH + BLANK_IN_ROWS + 90, 240);
        labelScore.setFont(Font.font(20));
        labelScore.setTextFill(Color.BLACK);

        Button buttonQuitGame = new Button("Quit Game");
        buttonQuitGame.setFont(Font.font(15));
        buttonQuitGame.relocate(game.getTableRow() * SQUARE_SIDE_LENGTH + BLANK_IN_ROWS + 90, game.getTableColumn() * SQUARE_SIDE_LENGTH);
        buttonQuitGame.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                try
                {
                    rootGameGraphics.getChildren().clear();
                    primaryStage.setScene(sceneMainMenu);
                    primaryStage.centerOnScreen();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        rootGameGraphics.getChildren().add(buttonQuitGame);

        for (int i=0;i < 2;i++)
        {
            Game.getCurrentGame().putRandomCell();
        }
        insideTheGame(primaryStage, rootGameGraphics);
    }

    private void insideTheGame(Stage primaryStage, Group rootGameGraphics)
    {
        Label highScore = new Label();
        highScore.relocate(Game.getCurrentGame().getTableRow() * SQUARE_SIDE_LENGTH + BLANK_IN_ROWS + 90, 190);
        rootGameGraphics.getChildren().add(highScore);

        Label score = new Label();
        score.relocate(Game.getCurrentGame().getTableRow() * SQUARE_SIDE_LENGTH + BLANK_IN_ROWS + 90, 290);
        rootGameGraphics.getChildren().add(score);

        Scene sceneInsideTheGame = primaryStage.getScene();
        ArrayList<String> input = new ArrayList<>();
        sceneInsideTheGame.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event)
            {
                String code = event.getCode().toString();
                if (!input.contains(code) && input.size() == 0)
                {
                    input.add(code);
                }
            }
        });
        AnimationTimer animationTimer = new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
                if (Game.getCurrentGame().isGameEnded())
                {
                    this.stop();
                    try
                    {
                        Thread.sleep(3000);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    primaryStage.setScene(sceneGameOver);
                    primaryStage.centerOnScreen();
                    gameOver(primaryStage);
                }
                if (input.contains("UP"))
                {
                    Game.getCurrentGame().gameLogicUpArrow();
                }
                if (input.contains("RIGHT"))
                {
                    Game.getCurrentGame().gameLogicRightArrow();
                }
                if (input.contains("DOWN"))
                {
                    Game.getCurrentGame().gameLogicDownArrow();
                }
                if (input.contains("LEFT"))
                {
                    Game.getCurrentGame().gameLogicLeftArrow();
                }
                input.clear();
                highScore.setText(Integer.toString(Player.getLoggedInPlayer().getHighScore()));
                score.setText(Integer.toString(Game.getCurrentGame().getScore()));
            }
        };
        animationTimer.start();
    }

    public void gameOver(Stage primaryStage)
    {
        rootGameOver.getChildren().clear();

        Label labelGameOver = new Label("Game Over");
        labelGameOver.setFont(Font.font(40));
        labelGameOver.setTextFill(Color.RED);
        labelGameOver.relocate(50, 25);
        rootGameOver.getChildren().add(labelGameOver);

        Label labelFinalScore = new Label("your Score : " + Game.getCurrentGame().getScore());
        labelFinalScore.setFont(Font.font(20));
        labelFinalScore.setTextFill(Color.GREEN);
        labelFinalScore.relocate(75, 120);
        rootGameOver.getChildren().add(labelFinalScore);

        backButton(primaryStage, rootGameOver, 110, 200);
    }

    public void nameAndPasswordFields(Group root, TextField textFieldName, TextField textFieldPassword)
    {
        Label labelName = new Label("Name");
        root.getChildren().add(labelName);
        labelName.relocate(20, 130);
        labelName.setFont(Font.font(15));
        labelName.setTextFill(Color.BLACK);

        HBox hBoxName = new HBox(textFieldName);
        hBoxName.relocate(115, 130);
        root.getChildren().add(hBoxName);

        Label labelPassword = new Label("Password");
        root.getChildren().add(labelPassword);
        labelPassword.relocate(20, 210);
        labelPassword.setFont(Font.font(15));
        labelPassword.setTextFill(Color.BLACK);

        HBox hBoxPassword = new HBox(textFieldPassword);
        hBoxPassword.relocate(115, 210);
        root.getChildren().add(hBoxPassword);
    }

    public void submitButton(Button button,Label labelInvalidInput)
    {
        button.relocate(25, 300);
        button.setFont(Font.font(20));
        labelInvalidInput.relocate(100, 100);
        labelInvalidInput.setFont(Font.font(15));
        labelInvalidInput.setTextFill(Color.RED);
    }

    public void backButton(Stage primaryStage, Group root, int x, int y)
    {
        Button buttonBackToMainMenu = new Button("Back");
        buttonBackToMainMenu.setFont(Font.font(25));
        buttonBackToMainMenu.relocate(x, y);
        buttonBackToMainMenu.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                primaryStage.setScene(sceneMainMenu);
                primaryStage.centerOnScreen();
                try
                {
                    mainMenu(primaryStage);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        root.getChildren().add(buttonBackToMainMenu);
    }
}