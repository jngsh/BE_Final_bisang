package com.exam.util;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class KoreanTextProcessor {

    private static final List<String> PARTICLES = Arrays.asList("를", "을", "의", "에", "에서", "과", "와", "로", "로서", "로써", "입니다.", "합니다.", "줍니다.", "습니다.", ".");

    public static List<String> removeParticles(List<String> words) {
        List<String> cleanedWords = new ArrayList<>();
        for (String word : words) {
            String cleanedWord = removeParticleFromWord(word);
            if (!cleanedWord.isEmpty()) {
                cleanedWords.add(cleanedWord);
            }
        }
        return cleanedWords;
    }

    private static String removeParticleFromWord(String word) {
        for (String particle : PARTICLES) {
            if (word.endsWith(particle)) {
                return word.substring(0, word.length() - particle.length());
            }
        }
        return word;
    }
}