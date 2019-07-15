package GraphicAndLogic;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.json.simple.parser.JSONParser;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.Scanner;

public class Main extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        convertJSONToPlayers();
        addIconImage(primaryStage);
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
}
