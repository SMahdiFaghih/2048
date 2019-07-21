package GraphicAndLogic;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.Scanner;

public class Main extends Application
{
    private final static int SQUARE_SIDE_LENGTH = 80;
    private final static int BLANK_IN_ROWS = 50;
    private final static int BLANK_IN_COLUMNS = 50;
    private final static int SCORE_SHOWING_SPACE = 200;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        convertJSONToPlayers();
        addIconImage(primaryStage);
        Media sound = new Media(new File("Music/Light High Classical Piano Music Theme Production Music Royalty-Free Stock Audio - Storyblocks Audio.mp3").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(-1);
        mediaPlayer.play();
        Controller controller = new Controller();
        controller.signUpMenu(primaryStage);
    }

    public static void main(String[] args)
    {
        launch(args);
    }

    private void addIconImage(Stage primaryStage)
    {
        Image iconImage = new Image("file:Game Icon/2048 game icon.jpg");
        primaryStage.getIcons().add(iconImage);
    }

    private void convertJSONToPlayers() throws Exception
    {
        InputStream inputStream = new FileInputStream("SavedAccounts/SavedAccountPath.txt");
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNext())
        {
            String fileName = scanner.nextLine();

            JSONParser jsonParser = new JSONParser();
            FileReader reader = new FileReader("SavedAccounts/" + fileName + ".json");
            Object obj = jsonParser.parse(reader);
            System.out.println(obj);
            Player.getPlayers().add(new Gson().fromJson(obj.toString(), Player.class));
        }
    }

    public static int getSquareSideLength()
    {
        return SQUARE_SIDE_LENGTH;
    }

    public static int getBlankInRows()
    {
        return BLANK_IN_ROWS;
    }

    public static int getBlankInColumns()
    {
        return BLANK_IN_COLUMNS;
    }

    public static int getScoreShowingSpace()
    {
        return SCORE_SHOWING_SPACE;
    }
}
