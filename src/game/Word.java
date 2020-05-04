package game;

public class Word {
    private String word, meaning;
    private boolean isComplete, isSolution;
    private Coordinate[] coords;
    
    public Word(String word, String meaning) {
        this.word = word;
        this.meaning = meaning;
        isComplete = false;
        isSolution = false;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    public boolean isSolution() {
        return isSolution;
    }

    public void setSolution(boolean isSolution) {
        this.isSolution = isSolution;
    }

    public Coordinate[] getCoords() {
        return coords;
    }

    public void setCoords(Coordinate[] coords) {
        this.coords = coords;
    }
}