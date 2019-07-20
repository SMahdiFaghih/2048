package GraphicAndLogic;

import javafx.scene.Group;
import javafx.scene.Scene;

import java.util.Random;

public class Game
{
    private final static int SQUARE_SIDE_LENGTH = Main.getSquareSideLength();
    private final static int BLANK_IN_ROWS = Main.getBlankInRows();
    private final static int BLANK_IN_COLUMNS = Main.getBlankInColumns();
    private final static int SCORE_SHOWING_SPACE = Main.getScoreShowingSpace();

    private static int defaultTableRow = 4;
    private static int defaultTableColumn = 4;
    private int tableRow = defaultTableRow;
    private int tableColumn = defaultTableColumn;
    private Cell[][] gameTable = new Cell[tableRow][tableColumn];
    private Random random = new Random();
    private boolean gameConditionChangedWithThisPress = false;
    private int score;
    private Group rootGameGraphics;
    private Scene sceneInsideTheGame;

    public Game(Player player)
    {
        player.setGame(this);
        setRootGameGraphics(new Group());
        setSceneInsideTheGame(new Scene(rootGameGraphics, this.getTableRow() * SQUARE_SIDE_LENGTH + 2 * BLANK_IN_ROWS + SCORE_SHOWING_SPACE, this.getTableColumn() * SQUARE_SIDE_LENGTH + 2 * BLANK_IN_COLUMNS));
    }

    public static void changeDefaultTable(int row, int column)
    {
        Game.defaultTableRow = row;
        Game.defaultTableColumn = column;
    }

    public Cell getRandomCell()
    {
        int row = random.nextInt(this.getTableRow());
        int column = random.nextInt(this.getTableColumn());
        if (this.getGameTable()[row][column].isCellEmpty())
        {
            return this.getGameTable()[row][column];
        }
        return getRandomCell();
    }

    public void gameLogicUpArrow()
    {
        gameConditionChangedWithThisPress = false;
        moveCellsUp();
        mergeCellsUp();
        moveCellsUp();
        if (gameConditionChangedWithThisPress)
        {
            putRandomCell();
        }
    }

    public void gameLogicRightArrow()
    {
        gameConditionChangedWithThisPress = false;
        moveCellsRight();
        mergeCellsRight();
        moveCellsRight();
        if (gameConditionChangedWithThisPress)
        {
            putRandomCell();
        }
    }

    public void gameLogicDownArrow()
    {
        gameConditionChangedWithThisPress = false;
        moveCellsDown();
        mergeCellsDown();
        moveCellsDown();
        if (gameConditionChangedWithThisPress)
        {
            putRandomCell();
        }
    }

    public void gameLogicLeftArrow()
    {
        gameConditionChangedWithThisPress = false;
        moveCellsLeft();
        mergeCellsLeft();
        moveCellsLeft();
        if (gameConditionChangedWithThisPress)
        {
            putRandomCell();
        }
    }

    public void mergeCells(Cell firstCell, Cell secondCell)
    {
        int firstCellNumber = Integer.parseInt(firstCell.getNumber().getText());
        int secondCellNumber = Integer.parseInt(secondCell.getNumber().getText());
        secondCell.getNumber().setText(Integer.toString(firstCellNumber + secondCellNumber));
        firstCell.getNumber().setText("");
        secondCell.showCellGraphics();
        firstCell.showCellGraphics();
        increaseScore(firstCellNumber + secondCellNumber);
        if (Player.getLoggedInPlayer().getHighScore() < getScore())
        {
            Player.getLoggedInPlayer().increaseHighScore(firstCellNumber + secondCellNumber);
        }
    }

    public void putRandomCell()
    {
        Cell cell = this.getRandomCell();
        String number = Integer.toString(cell.getRandomNumber());
        cell.getNumber().setText(number);
        cell.showCellGraphics();
    }

    public void moveCellsUp()
    {
        for (int i=0;i < getTableRow();i++)
        {
            for (int j=0;j < getTableColumn();j++)
            {
                if (getGameTable()[i][j].getNumber().getText().isEmpty())
                {
                    continue;
                }
                int counter = 1;
                while (j - counter >= 0 && getGameTable()[i][j - counter].getNumber().getText().isEmpty())
                {
                    counter ++;
                }
                if (counter == 1)
                {
                    continue;
                }
                getGameTable()[i][j - counter + 1].getNumber().setText(getGameTable()[i][j].getNumber().getText());
                getGameTable()[i][j].getNumber().setText("");
                getGameTable()[i][j].showCellGraphics();
                getGameTable()[i][j - counter + 1].showCellGraphics();
                gameConditionChangedWithThisPress = true;
            }
        }
    }

    public void moveCellsRight()
    {
        for (int j=0;j < getTableColumn();j++)
        {
            for (int i = getTableRow() - 1;i >= 0;i--)
            {
                if (getGameTable()[i][j].getNumber().getText().isEmpty())
                {
                    continue;
                }
                int counter = 1;
                while (counter + i < getTableRow() && getGameTable()[i + counter][j].getNumber().getText().isEmpty())
                {
                    counter ++;
                }
                if (counter == 1)
                {
                    continue;
                }
                getGameTable()[i + counter - 1][j].getNumber().setText(getGameTable()[i][j].getNumber().getText());
                getGameTable()[i][j].getNumber().setText("");
                getGameTable()[i][j].showCellGraphics();
                getGameTable()[i + counter - 1][j].showCellGraphics();
                gameConditionChangedWithThisPress = true;
            }
        }
    }

    public void moveCellsDown()
    {
        for (int i=0;i < getTableRow();i++)
        {
            for (int j = getTableColumn() - 1;j >= 0 ;j--)
            {
                if (getGameTable()[i][j].getNumber().getText().isEmpty())
                {
                    continue;
                }
                int counter = 1;
                while (counter + j < getTableColumn() && getGameTable()[i][j + counter].getNumber().getText().isEmpty())
                {
                    counter ++;
                }
                if (counter == 1)
                {
                    continue;
                }
                getGameTable()[i][j + counter - 1].getNumber().setText(getGameTable()[i][j].getNumber().getText());
                getGameTable()[i][j].getNumber().setText("");
                getGameTable()[i][j].showCellGraphics();
                getGameTable()[i][j + counter - 1].showCellGraphics();
                gameConditionChangedWithThisPress = true;
            }
        }
    }

    public void moveCellsLeft()
    {
        for (int j=0;j < getTableColumn();j++)
        {
            for (int i=0;i < getTableRow();i++)
            {
                if (getGameTable()[i][j].getNumber().getText().isEmpty())
                {
                    continue;
                }
                int counter = 1;
                while (i - counter >= 0 && getGameTable()[i - counter][j].getNumber().getText().isEmpty())
                {
                    counter ++;
                }
                if (counter == 1)
                {
                    continue;
                }
                getGameTable()[i - counter + 1][j].getNumber().setText(getGameTable()[i][j].getNumber().getText());
                getGameTable()[i][j].getNumber().setText("");
                getGameTable()[i][j].showCellGraphics();
                getGameTable()[i - counter + 1][j].showCellGraphics();
                gameConditionChangedWithThisPress = true;
            }
        }
    }

    public void mergeCellsUp()
    {
        for (int i=0;i < getTableRow();i++)
        {
            for (int j=0;j < getTableColumn();j++)
            {
                if (j == 0 || getGameTable()[i][j].getNumber().getText().isEmpty() || getGameTable()[i][j - 1].getNumber().getText().isEmpty())
                {
                    continue;
                }
                if (getGameTable()[i][j].getNumber().getText().equals(getGameTable()[i][j - 1].getNumber().getText()))
                {
                    mergeCells(getGameTable()[i][j], getGameTable()[i][j - 1]);
                    gameConditionChangedWithThisPress = true;
                }
            }
        }
    }

    public void mergeCellsRight()
    {
        for (int j=0;j < getTableColumn();j++)
        {
            for (int i = getTableRow() - 1;i >= 0;i--)
            {
                if (i + 1 == getTableRow() || getGameTable()[i][j].getNumber().getText().isEmpty() || getGameTable()[i + 1][j].getNumber().getText().isEmpty())
                {
                    continue;
                }
                if (getGameTable()[i][j].getNumber().getText().equals(getGameTable()[i + 1][j].getNumber().getText()))
                {
                    mergeCells(getGameTable()[i][j], getGameTable()[i + 1][j]);
                    gameConditionChangedWithThisPress = true;
                }
            }
        }
    }

    public void mergeCellsDown()
    {
        for (int i=0;i < getTableRow();i++)
        {
            for (int j = getTableColumn() - 1;j >= 0 ;j--)
            {
                if (j + 1 == getTableColumn() || getGameTable()[i][j].getNumber().getText().isEmpty() || getGameTable()[i][j + 1].getNumber().getText().isEmpty())
                {
                    continue;
                }
                if (getGameTable()[i][j].getNumber().getText().equals(getGameTable()[i][j + 1].getNumber().getText()))
                {
                    mergeCells(getGameTable()[i][j], getGameTable()[i][j + 1]);
                    gameConditionChangedWithThisPress = true;
                }
            }
        }
    }

    public void mergeCellsLeft()
    {
        for (int j=0;j < getTableColumn();j++)
        {
            for (int i=0;i < getTableRow();i++)
            {
                if (i == 0 || getGameTable()[i][j].getNumber().getText().isEmpty() || getGameTable()[i - 1][j].getNumber().getText().isEmpty())
                {
                    continue;
                }
                if (getGameTable()[i][j].getNumber().getText().equals(getGameTable()[i - 1][j].getNumber().getText()))
                {
                    mergeCells(getGameTable()[i][j], getGameTable()[i - 1][j]);
                    gameConditionChangedWithThisPress = true;
                }
            }
        }
    }

    public boolean isGameEnded()
    {
        for (int i=0;i < getTableRow();i++)
        {
            for (int j=0;j < getTableColumn();j++)
            {
                if (getGameTable()[i][j].isCellEmpty())
                {
                    continue;
                }
                if (Integer.parseInt(getGameTable()[i][j].getNumber().getText()) == 2048)
                {
                    return true;
                }
            }
        }
        for (int i=0;i < getTableRow();i++)
        {
            for (int j=0;j < getTableColumn();j++)
            {
                if (getGameTable()[i][j].isCellEmpty())
                {
                    return false;
                }
            }
        }
        int[] dx = {0, 1, 0, -1};
        int[] dy = {-1, 0, 1, 0};
        for (int i=0;i < getTableRow();i++)
        {
            for (int j=0;j < getTableColumn();j++)
            {
                for (int k=0;k < 4;k++)
                {
                    if (i + dx[k] >= 0 && i + dx[k] < getTableRow() && j + dy[k] >= 0 && j + dy[k] < getTableColumn())
                    {
                        if (getGameTable()[i][j].getNumber().getText().equals(getGameTable()[i + dx[k]][j + dy[k]].getNumber().getText()))
                        {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public int getTableRow()
    {
        return tableRow;
    }

    public int getTableColumn()
    {
        return tableColumn;
    }

    public static int getDefaultTableRow()
    {
        return defaultTableRow;
    }

    public static int getDefaultTableColumn()
    {
        return defaultTableColumn;
    }

    public Cell[][] getGameTable()
    {
        return gameTable;
    }

    public int getScore()
    {
        return score;
    }

    public void increaseScore(int score)
    {
        this.score += score;
    }

    public Group getRootGameGraphics()
    {
        return rootGameGraphics;
    }

    public void setRootGameGraphics(Group rootGameGraphics)
    {
        this.rootGameGraphics = rootGameGraphics;
    }

    public Scene getSceneInsideTheGame()
    {
        return sceneInsideTheGame;
    }

    public void setSceneInsideTheGame(Scene sceneInsideTheGame)
    {
        this.sceneInsideTheGame = sceneInsideTheGame;
    }
}
