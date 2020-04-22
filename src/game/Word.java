package game;

public class Word {
    String word, meaning;
    boolean isComplete;
    
    public Word(String word, String meaning) {
        this.word = word;
        this.meaning = meaning;
        isComplete = false;
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
}