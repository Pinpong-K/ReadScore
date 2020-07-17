package ReadScoreExam;



import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Readability {

    //Readability statistics
    private String rawMessage;
    private int wordCount;
    private int sentencesCount;
    private int characterCount;
    private int syllablesCount;
    private int polysyllablesCount;
    private double automatedReadabilityIndex;
    private double fleschKincaidReadability;
    private double simpleMeasureOfGobble;
    private double colemanLiauIndex;

    public Readability(String message)
    {
        this.rawMessage = message;
        this.wordCount = findWordCount(message);
        this.sentencesCount = findSentencesCount(message);
        this.characterCount = findCharacterCount(message);
        this.syllablesCount = findSyllablesCount(message);
        this.polysyllablesCount = findPolysyllables(message);

        this.automatedReadabilityIndex = getARI();
        this.fleschKincaidReadability = getFleschKincaidReadability();
        this.simpleMeasureOfGobble = getSMOG();
        this.colemanLiauIndex = getColemanLiau();


    }

    public void printResult()
    {
        System.out.println("Word: " + wordCount);
        System.out.println("SentencesCount : " + sentencesCount);
        System.out.println("Characters: "+ characterCount);
        System.out.println("Syllables : " + syllablesCount);
        System.out.println("Polysyllables: " + polysyllablesCount);
    }

    public void printReadabilityScore()
    {
        System.out.println("Automated readability index: "+automatedReadabilityIndex+" (about "+ageConversion(automatedReadabilityIndex)+" year olds).");
        System.out.println("Flesch-Kincaid readability: "+fleschKincaidReadability +" (about "+ageConversion(fleschKincaidReadability)+" year olds).");
        System.out.println("sumple Measure of Gobble: "+ simpleMeasureOfGobble +" (about "+ageConversion(simpleMeasureOfGobble)+" year olds).");
        System.out.println("Coleman-Liau_Index: "+colemanLiauIndex+" (about "+ageConversion(colemanLiauIndex)+" year olds).");

        double average = (ageConversion(automatedReadabilityIndex) +  ageConversion(fleschKincaidReadability)  + ageConversion(simpleMeasureOfGobble) + ageConversion(colemanLiauIndex)) / 4d;
        System.out.println("This text should be understood in average by "+ round(average,2) + " year olds");

    }

    private int findWordCount(String message)
    {
        Pattern p = Pattern.compile("([a-zA-Z]+)");
        Matcher matchText = p.matcher(message);
        int count = 0;
        while (matchText.find()) {
            count++;
        }

        return count;
    }

    private int findSentencesCount(String message)
    {
        Pattern p = Pattern.compile("\\s+[^.!?]*[.!?]",Pattern.MULTILINE|Pattern.COMMENTS);
        Matcher matchText = p.matcher(message);
        int count = 0;
        while (matchText.find()) {
            count++;
        }

        return count;
    }

    private int findCharacterCount(String message)
    {
        int count = 0;
        for (int i=0;i<message.length();i++)
            if(message.charAt(i) != ' ')
                count++;

        return  count;
    }

    private int findSyllablesCount(String message)
    {
        Pattern p = Pattern.compile("(?i)[aiou][aeiou]*|e[aeiou]*(?!d?\\b)");
        String lowerCase = message.toLowerCase();
        Matcher matchText = p.matcher(lowerCase);
        int count = 0;
        while(matchText.find()){
            count++;
        }

        return count;
    }

    private int findPolysyllables(String message)
    {
        int count = 0;
        //split message to word
        HashMap<String,Integer> sylablesTable = new HashMap<>();

        Pattern p = Pattern.compile("([a-zA-Z]+)");
        Matcher matchText = p.matcher(message);
        //loop for count syllables each word
        while (matchText.find()) {
            String word =  matchText.group().toLowerCase();
            if(!sylablesTable.containsKey(word))
                sylablesTable.put(word,findSyllablesCount(word));

        }

        //loop for count poly syllables
        for (Map.Entry<String,Integer> entey : sylablesTable.entrySet()) {
            //System.out.println("word : "+entey.getKey()+" || count : "+entey.getValue());
            if (entey.getValue() > 1)
                count++;
        }
            return  count;
    }

    //Automated Readability index
    private double getARI() {
        double score = 4.71 * characterCount / wordCount + 0.5 * wordCount / sentencesCount
                - 21.43;
        return round(score, 2);
    }

    //Flesch-Kincaid readability test
    private double getFleschKincaidReadability() {
        double score = 0.39 * wordCount / sentencesCount + 11.8 * syllablesCount / wordCount
                - 15.59;
        return round(score, 2);
    }

    //Simple Measure of Gobbledygook
    private double getSMOG() {
        double score = 1.043 * Math.sqrt(polysyllablesCount * (30.0 / sentencesCount)) + 3.1291;
        return round(score, 2);
    }

    //coleman-Liau index
    private double getColemanLiau() {
        double score = (5.89 * characterCount / wordCount) - (30 * sentencesCount / wordCount)
                - 15.8;
        return round(score, 2);
    }

    //cover value in 2 digit
    private double round(double value,int places)
    {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }


    private int ageConversion(double score)
    {
        //conversion score to age
        if(score >= 0 && score < 1)
            return 5;
        else if(score >= 1 && score < 2)
            return 6;
        else if(score >= 2 && score < 3)
            return 7;
        else if(score >= 3 && score < 4)
            return 8;
        else if(score >= 4 && score < 5)
            return 9;
        else if(score >= 5 && score < 6)
            return 10;
        else if(score >= 6 && score < 7)
            return 11;
        else if(score >= 7 && score < 8)
            return 12;
        else if(score >= 8 && score < 9)
            return 13;
        else if(score >= 9 && score < 10)
            return 14;
        else if(score >= 10 && score < 11)
            return 15;
        else if(score >= 11 && score < 12)
            return 16;
        else if(score >= 12 && score < 13)
            return 17;
        else if(score >= 13 && score < 14)
            return 20;
        else if(score >= 14 )
            return 24;

        return 0;

    }









}
