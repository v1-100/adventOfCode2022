import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Day2 {
    File f;
    Integer totalScore;

    public Day2() {
        this.f = new File("src/inputday2.txt");
        totalScore = 0;
    }

    public enum Shape {
        Rock(1), Paper(2), Scissors(3);
        private final int valeur;

        private Shape(int valeur) {
            this.valeur = valeur;
        }

        public int getValeur() {
            return this.valeur;
        }

        public static Shape getWin(Shape shape) {
            switch (shape) {
                case Rock:
                    return Paper;
                case Paper:
                    return Scissors;
                case Scissors:
                    return Rock;
            }
            return shape;
        }

        public static Shape getDraw(Shape shape) {
            return shape;
        }

        public static Shape getLoss(Shape shape) {
            switch (shape) {
                case Rock:
                    return Scissors;
                case Paper:
                    return Rock;
                case Scissors:
                    return Paper;
            }
            return shape;
        }

        public static Shape getShape(char letter) {
            switch (letter) {
                case 'A':
                    return Shape.Rock;
                case 'B':
                    return Shape.Paper;
                case 'C':
                    return Shape.Scissors;
            }
            return null;
        }

    }

    public enum Action {
        Win('Z'), Loss('X'), Draw('Y');
        private final char valeur;

        private Action(char valeur) {
            this.valeur = valeur;
        }

        public int getValeur() {
            return this.valeur;
        }

        public static Action getAction(char letter) {
            for (Action action : Action.values()) {
                if (action.getValeur() == letter) {
                    return action;
                }
            }
            return null;
        }
    }

    public Shape getShapeFromLetter(char letter) {
        switch (letter) {
            case 'A':
                return Shape.Rock;
            case 'B':
                return Shape.Paper;
            case 'C':
                return Shape.Scissors;
            case 'X':
                return Shape.Rock;
            case 'Y':
                return Shape.Paper;
            case 'Z':
                return Shape.Scissors;
            default:
                return null;
        }
    }

    public enum RoundResult {
        Loss(0), Draw(3), Win(6);
        private final int valeur;

        private RoundResult(int valeur) {
            this.valeur = valeur;
        }

        public int getValeur() {
            return this.valeur;
        }
    }

    public void calculate() throws IOException {
        System.out.println("Hello calculate day 2!");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
        String line = bufferedReader.readLine();

        while (line != null) {
            if (line.length() != 0) {

                Action action = Action.getAction(line.charAt(2));
                Shape myShape = Shape.Rock;
                switch (action) {
                    case Win:
                        myShape = Shape.getWin(Shape.getShape(line.charAt(0)));
                        break;
                    case Loss:
                        myShape = Shape.getLoss(Shape.getShape(line.charAt(0)));
                        break;
                    case Draw:
                        myShape = Shape.getDraw(Shape.getShape(line.charAt(0)));
                        break;
                }
                RoundResult roundResult = getRoundResult(Shape.getShape(line.charAt(0)), myShape);
                switch (roundResult) {
                    case Draw:
                        totalScore = totalScore + 3 + myShape.getValeur();
                        System.out.println("Draw " + myShape + " " + totalScore);
                        break;
                    case Win:
                        totalScore = totalScore + 6 + myShape.getValeur();
                        System.out.println("Win " + myShape + " " + totalScore);
                        break;
                    case Loss:
                        totalScore = totalScore + myShape.getValeur();
                        System.out.println("Loss " + myShape + " " + totalScore);
                        break;
                }
            }
            line = bufferedReader.readLine();
        }
        System.out.println("End totalScore " + totalScore);

    }

    private RoundResult getRoundResult(Shape shape, Shape myShape) {
        if (shape == Shape.Rock) {
            switch (myShape) {
                case Rock:
                    return RoundResult.Draw;
                case Paper:
                    return RoundResult.Win;
                case Scissors:
                    return RoundResult.Loss;
            }
        }
        if (shape == Shape.Paper) {
            switch (myShape) {
                case Rock:
                    return RoundResult.Loss;
                case Paper:
                    return RoundResult.Draw;
                case Scissors:
                    return RoundResult.Win;
            }
        }
        if (shape == Shape.Scissors) {
            switch (myShape) {
                case Rock:
                    return RoundResult.Win;
                case Paper:
                    return RoundResult.Loss;
                case Scissors:
                    return RoundResult.Draw;
            }
        }
        return null;
    }

    private RoundResult getAction(String line) {
        switch (line) {
            // X/A for Rock, Y/B for Paper, and Z/C for Scissors
            case "A X":
                return RoundResult.Draw;
            case "A Y":
                return RoundResult.Win;
            case "A Z":
                return RoundResult.Loss;
            case "B X":
                return RoundResult.Loss;
            case "B Y":
                return RoundResult.Draw;
            case "B Z":
                return RoundResult.Win;
            case "C X":
                return RoundResult.Win;
            case "C Y":
                return RoundResult.Loss;
            case "C Z":
                return RoundResult.Draw;
            default:
                System.out.println("No match");
        }
        return null;
    }

    private RoundResult getRoundResult(String line) {
        switch (line) {
            // X/A for Rock, Y/B for Paper, and Z/C for Scissors
            case "A X":
                return RoundResult.Draw;
            case "A Y":
                return RoundResult.Win;
            case "A Z":
                return RoundResult.Loss;
            case "B X":
                return RoundResult.Loss;
            case "B Y":
                return RoundResult.Draw;
            case "B Z":
                return RoundResult.Win;
            case "C X":
                return RoundResult.Win;
            case "C Y":
                return RoundResult.Loss;
            case "C Z":
                return RoundResult.Draw;
            default:
                System.out.println("No match");
        }
        System.out.println("On ne devrait pas être la");
        return RoundResult.Win;
    }
}
