package com.sparta.backend.utils;

import com.sparta.backend.model.Article;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class RandomGenerator {
    public List<Article> getRandomArticles(List<Article> articles, int cnt) {
        HashSet<Article> relatedArticles = new HashSet<>();
        Random random = new Random();
        int bound = articles.size();
        while (relatedArticles.size() < cnt) {
            int randomNum = random.nextInt(bound);
            relatedArticles.add(articles.get(randomNum));
        }
        return new ArrayList<>(relatedArticles);
    }
}
