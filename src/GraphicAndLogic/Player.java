package GraphicAndLogic;

import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Player
{
    private static ArrayList<Player> players = new ArrayList<>();
    private String name;
    private String password;
    private int highScore = 0;
    private static Player loggedInPlayer;

    public Player(String name, String password)
    {
        this.name = name;
        this.password = password;
        players.add(this);
    }

    public void savePlayerInfo(Player player, String name, boolean isNewAccount) throws IOException
    {
        FileWriter SavedAccountPath = new FileWriter("SavedAccounts/SavedAccountPath.txt" ,true);
        if (isNewAccount)
        {
            SavedAccountPath.write(name + "\n");
            SavedAccountPath.close();
        }
        String json = new GsonBuilder().setPrettyPrinting().create().toJson(player);
        System.out.println(json);
        try
        {

            FileWriter saveAccountInfo = new FileWriter("SavedAccounts/" + name + ".json", false);
            saveAccountInfo.write(json);
            saveAccountInfo.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void login(Player player)
    {
        Player.loggedInPlayer = player;
    }

    public static Player findPlayer(String name)
    {
        for (Player player : getPlayers())
        {
            if (player.getName().equals(name))
            {
                return player;
            }
        }
        return null;
    }

    public static void sortPlayers()
    {
        players.sort((o1, o2) -> {
            if (o1.getHighScore() > o2.getHighScore())
            {
                return -1;
            }
            else if (o1.getHighScore() < o2.getHighScore())
            {
                return 1;
            }
            return o1.getName().compareTo(o2.getName());
        });
    }

    public static Player getLoggedInPlayer()
    {
        return loggedInPlayer;
    }

    public int getHighScore()
    {
        return highScore;
    }

    public void increaseHighScore(int highScore)
    {
        this.highScore += highScore;
    }

    public static ArrayList<Player> getPlayers()
    {
        return players;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPassword()
    {
        return password;
    }
}
