package ui;

import java.io.*;
import java.util.*;

public class HighScore implements Serializable { // todo: відцентувати, збільшити шрифт тд
    private ArrayList<HighScoreRecord> highScores;
    @Serial
    private static final long serialVersionUID = 8574982327062808366L;

    public HighScore() {
        highScores = readHighScores();
        Collections.sort(highScores);
    }

    public void addUserScore(String userName, int score) {
        highScores.add(new HighScoreRecord(userName, score));
        saveHighScores();
        Collections.sort(highScores);
        System.out.println("Record was written.");
    }

    private void saveHighScores() {
        String fileName = "HighScores.txt";
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(highScores);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<HighScoreRecord> getHighScores()
    {
        return highScores;
    }

    private ArrayList<HighScoreRecord> readHighScores() {
        ArrayList<HighScoreRecord> highScores = new ArrayList<>();

        File file = new File("HighScores.txt");
        if(!file.exists())
            return highScores;

    try {
            FileInputStream fileInputStream = new FileInputStream("HighScores.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            highScores = (ArrayList<HighScoreRecord>) objectInputStream.readObject();
            objectInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return highScores;
    }
}

class HighScoreRecord implements Serializable, Comparable<HighScoreRecord> {
    private String userName;
    private int score;

    HighScoreRecord(String userName, int score) {
        this.userName = userName;
        this.score = score;
    }

    @Override
    public int compareTo(HighScoreRecord other) {
        return Integer.compare(other.score, this.score );
    }

    @Override
    public String toString() {
        return this.userName + " -> " + this.score;
    }
}
