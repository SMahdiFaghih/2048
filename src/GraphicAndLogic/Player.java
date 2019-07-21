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
    private transient Game game;
    private static Player loggedInPlayer;

    public Player(String name, String password)
    {
        this.name = name;
        this.password = password;
        players.add(this);
    }

    public static void savePlayerInfo(Player player, boolean isNewAccount) throws IOException
    {
        String name = player.getName();
        if (isNewAccount)
        {
            FileWriter SavedAccountPath = new FileWriter("SavedAccounts/SavedAccountPath.txt" ,true);
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

    public void changeName(String newName) throws IOException
    {
        this.name = newName;
        savePlayerInfo(this, true);
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

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Player)
        {
            return ((Player) obj).getName().equals(this.getName());
        }
        return false;
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

    public String getPassword()
    {
        return password;
    }

    public Game getGame()
    {
        return game;
    }

    public void setGame(Game game)
    {
        this.game = game;
    }
}
