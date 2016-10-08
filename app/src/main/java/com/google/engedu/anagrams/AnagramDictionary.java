package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 4;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private ArrayList<String> wordList;
    HashSet<String> wordSet;
    int wordLength=DEFAULT_WORD_LENGTH;
    HashMap<String, ArrayList<String>> lettersToWord;
    HashMap<Integer,ArrayList<String>> sizeToWords;

    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        wordSet= new HashSet<>();
        wordList= new ArrayList<>();
        lettersToWord= new HashMap<>();
        sizeToWords= new HashMap<>();


        while((line = in.readLine()) != null) {
            String word = line.trim();
            //working with hashmaps

            if(!(lettersToWord.isEmpty())){

                String key= sortLetters(word);
                if(lettersToWord.containsKey(key)){
                    ArrayList<String> val = lettersToWord.get(key);
                    val.add(word);
                    lettersToWord.put(key,val);
                }
                else
                {
                    ArrayList<String> value= new ArrayList<>();
                    value.add(word);
                    lettersToWord.put(key,value);
                }
            }
            else {
                ArrayList<String> values= new ArrayList<>();
                values.add(word);
                lettersToWord.put(sortLetters(word),values);
            }

            wordList.add(word);
            wordSet.add(word);
            int k= word.length();
            if(sizeToWords.containsKey(Integer.valueOf(k))){
                ArrayList<String> s= sizeToWords.get(Integer.valueOf(k));
                s.add(word);
                sizeToWords.put(Integer.valueOf(k),s);
            }
            else{
                ArrayList<String> st= new ArrayList<>();
                st.add(word);
                sizeToWords.put(Integer.valueOf(k),st);
            }

        }

    }

    public boolean isGoodWord(String word, String base) {
        if((wordSet.contains(word))&& (!(word.contains(base)))){
                return true;
        }
        else
            return false;

    }

    private String sortLetters(String sortIt){
            char strings[]= new char[sortIt.length()];
            strings= sortIt.toCharArray();
            Arrays.sort(strings);
        return String.valueOf(strings);

    }

    public ArrayList<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        int i;

        for(i=0;i< wordList.size(); i++){
            if(sortLetters(wordList.get(i)).equals(sortLetters(targetWord))){
                result.add(wordList.get(i));

            }
        }

        return result;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        String t="";
        for(char ch='a';ch<='z';ch++){
            t=word+ch;
            t=sortLetters(t);
            if(lettersToWord.containsKey(t)){
                ArrayList<String> temp= lettersToWord.get(t);
                for(String x:temp){
                    result.add(x);
                }

            }
        }
        return result;
    }

    public String pickGoodStarterWord() {
        while(true){
            ArrayList<String> r= sizeToWords.get(Integer.valueOf(wordLength));
            int i=random.nextInt(r.size());
            String k=sortLetters(r.get(Integer.valueOf(i)));
            if((lettersToWord.containsKey(k)) && (((lettersToWord.get(k)).size())>= MIN_NUM_ANAGRAMS))
            {
                if(wordLength==MAX_WORD_LENGTH){
                    wordLength=DEFAULT_WORD_LENGTH;
                }
                else
                ++wordLength;

                return r.get(Integer.valueOf(i));
            }
        }
    }
}
