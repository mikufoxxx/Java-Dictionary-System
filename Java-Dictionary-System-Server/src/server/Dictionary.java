package server;

import java.util.HashMap;

public class Dictionary {
    public HashMap<String, String> dictionary;
    public Dictionary(HashMap<String, String> dictionary) {
        this.dictionary = dictionary;
    }
    public HashMap<String, String> getDictionary() {
        return this.dictionary;
    }
    public int getDictionarySize() {
        return this.dictionary.size();
    }

    public synchronized String search(String word) {
        String result = "";
        if (!this.dictionary.containsKey(word.toLowerCase())) {
            result = "[Error]Word [" + word + "] not found in the dictionary, try add first.\n";
        } else {
            result = String.valueOf(word.toLowerCase()) + ": " + (String)this.dictionary.get(word.toLowerCase()) + "\n";
        }
        return result;
    }

    public synchronized String add(String word, String meaning) {
        String result = "";
        if (!this.dictionary.containsKey(word.toLowerCase())) {
            this.dictionary.put(word.toLowerCase(), meaning.toLowerCase());
            result = "[Info]Successfully added a new word\n";
        } else {
            result = "[Error]Word [" + word + "] already existed in the dictioney.\n";
        }
        return result;
    }

    public synchronized String update(String word, String meaning) {
        String result = "";
        if (!this.dictionary.containsKey(word.toLowerCase())) {
            result = "[Error]Word [" + word + "] not found in the dictionary, try add first.\n";
        } else {
            this.dictionary.put(word.toLowerCase(), meaning.toLowerCase());
            result = "[Info]Successfully updated the word [" + word + "].\n";
        }
        return result;
    }

    public synchronized String delete(String word) {
        String result = "";
        if (!this.dictionary.containsKey(word.toLowerCase())) {
            result = "[Error]Word [" + word + "] does not exist in the dictioney.\n";
        } else {
            this.dictionary.remove(word.toLowerCase());
            result = "[Info]Successfully deleted [" + word + "].\n";
        }
        return result;
    }
}