package me.rabrg.util;

import edu.stanford.nlp.simple.Sentence;

import java.util.Arrays;
import java.util.List;

public class WordUtil {

    private static final List<String> STOP_WORDS = Arrays.asList("a", "about", "above", "above", "across", "after",
            "afterwards", "again", "against", "all", "almost", "alone", "along", "already", "also", "although", "always",
            "am", "among", "amongst", "amoungst", "amount", "an", "and", "another", "any", "anyhow", "anyone", "anything",
            "anyway", "anywhere", "are", "around", "as", "at", "back", "be", "became", "because", "become", "becomes",
            "becoming", "been", "before", "beforehand", "behind", "being", "below", "beside", "besides", "between",
            "beyond", "bill", "both", "bottom", "but", "by", "call", "can", "cannot", "cant", "co", "con", "could",
            "couldnt", "cry", "de", "describe", "detail", "do", "done", "down", "due", "during", "each", "eg", "eight",
            "either", "eleven", "else", "elsewhere", "empty", "enough", "etc", "even", "ever", "every", "everyone",
            "everything", "everywhere", "except", "few", "fifteen", "fify", "fill", "find", "fire", "first", "five",
            "for", "former", "formerly", "forty", "found", "four", "from", "front", "full", "further", "get", "give",
            "go", "had", "has", "hasnt", "have", "he", "hence", "her", "here", "hereafter", "hereby", "herein",
            "hereupon", "hers", "herself", "him", "himself", "his", "how", "however", "hundred", "ie", "if", "in",
            "inc", "indeed", "interest", "into", "is", "it", "its", "itself", "keep", "last", "latter", "latterly",
            "least", "less", "ltd", "made", "many", "may", "me", "meanwhile", "might", "mill", "mine", "more",
            "moreover", "most", "mostly", "move", "much", "must", "my", "myself", "name", "namely", "neither", "never",
            "nevertheless", "next", "nine", "no", "nobody", "none", "noone", "nor", "not", "nothing", "now", "nowhere",
            "of", "off", "often", "on", "once", "one", "only", "onto", "or", "other", "others", "otherwise", "our",
            "ours", "ourselves", "out", "over", "own", "part", "per", "perhaps", "please", "put", "rather", "re",
            "same", "see", "seem", "seemed", "seeming", "seems", "serious", "several", "she", "should", "show", "side",
            "since", "sincere", "six", "sixty", "so", "some", "somehow", "someone", "something", "sometime",
            "sometimes", "somewhere", "still", "such", "system", "take", "ten", "than", "that", "the", "their", "them",
            "themselves", "then", "thence", "there", "thereafter", "thereby", "therefore", "therein", "thereupon",
            "these", "they", "thickv", "thin", "third", "this", "those", "though", "three", "through", "throughout",
            "thru", "thus", "to", "together", "too", "top", "toward", "towards", "twelve", "twenty", "two", "un",
            "under", "until", "up", "upon", "us", "very", "via", "was", "we", "well", "were", "what", "whatever",
            "when", "whence", "whenever", "where", "whereafter", "whereas", "whereby", "wherein", "whereupon",
            "wherever", "whether", "which", "while", "whither", "who", "whoever", "whole", "whom", "whose", "why",
            "will", "with", "within", "without", "would", "yet", "you", "your", "yours", "yourself", "yourselves",
            "the");

    public static int getLemmaFrequency(final Sentence target, final Sentence source) {
        int frequency = 0;
        for (final String targetLemma : target.lemmas()) {
            if (STOP_WORDS.contains(targetLemma))
                continue;
            for (final String sourceLemma : source.lemmas()) {
                if (!STOP_WORDS.contains(sourceLemma) && targetLemma.equals(sourceLemma))
                    frequency++;
            }
        }
        return frequency;
    }

    public static int relationMatches = 0;
    public static int relationObject = 0;

    public static double getTripleMatchMultiplier(final Sentence target, final Sentence source) {
        final TypeDependencyUtil.TypeDependencyData targetTriple = TypeDependencyUtil.getData(target.text());
        final TypeDependencyUtil.TypeDependencyData sourceTriple = TypeDependencyUtil.getData(source.text());
        if (targetTriple == null) {
            System.out.println("Null target triple: " + target.text());
            return 1;
        }
        if (sourceTriple == null) {
            System.out.println("Null target triple: " + source.text());
            return 1;
        }
        double multiplier = 1.0;
        if (targetTriple.getSubject() != null && sourceTriple.getSubject() != null && targetTriple.getSubject().equals(sourceTriple.getSubject()))
            multiplier += 0.66;
        if (targetTriple.getRelation() != null && sourceTriple.getRelation() != null && targetTriple.getRelation().equals(sourceTriple.getRelation())) {
            multiplier += 0.66;
            relationMatches++;
        }
        if (targetTriple.getObject() != null && sourceTriple.getObject() != null && targetTriple.getObject().equals(sourceTriple.getObject())) {
            multiplier += 0.66;
            relationObject++;
        }
        return multiplier;
    }
}