package GraphicAndLogic;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Cell
{
    private Label number;
    private Rectangle square;
    private StackPane stackPane;
    private Random random = new Random();

    public void showCellGraphics()
    {
        if (this.getNumber().getText().isEmpty())
        {
            getSquare().setFill(Color.BEIGE);
            return;
        }
        int numberOfCell = Integer.parseInt(this.getNumber().getText());
        switch (numberOfCell)
        {
            case 2:
                getSquare().setFill(Color.BURLYWOOD);
                break;
            case 4:
                getSquare().setFill(Color.YELLOW);
                break;
            case 8:
                getSquare().setFill(Color.ORANGE);
                break;
            case 16:
                getSquare().setFill(Color.RED);
                break;
            case 32:
                getSquare().setFill(Color.BLUE);
                break;
            case 64:
                getSquare().setFill(Color.GREEN);
                break;
            case 128:
                getSquare().setFill(Color.PURPLE);
                break;
            case 256:
                getSquare().setFill(Color.BROWN);
                break;
            case 512:
                getSquare().setFill(Color.YELLOWGREEN);
                break;
            case 1024:
                getSquare().setFill(Color.ROSYBROWN);
                break;
            case 2048:
                getSquare().setFill(Color.GOLD);
                break;
            default:
                getSquare().setFill(Color.GRAY);
                break;
        }
        getSquare().setStroke(Color.BLACK);
    }

    public boolean isCellEmpty()
    {
        if (this.getNumber().getText().isEmpty())
        {
            return true;
        }
        return false;
    }

    public int getRandomNumber()
    {
        int randomNum = random.nextInt(2);
        return (randomNum + 1) * 2;
    }

    public Label getNumber()
    {
        return number;
    }

    public void setNumber(Label number)
    {
        this.number = number;
    }

    public Rectangle getSquare()
    {
        return square;
    }

    public void setSquare(Rectangle square)
    {
        this.square = square;
    }

    public StackPane getStackPane()
    {
        return stackPane;
    }

    public void setStackPane(StackPane stackPane)
    {
        this.stackPane = stackPane;
    }
}
