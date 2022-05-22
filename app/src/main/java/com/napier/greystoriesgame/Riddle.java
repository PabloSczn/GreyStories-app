package com.napier.greystoriesgame;

/**
 * POJO Riddle Class
 * @author Pablo Sanchez
 * Last modification date: 03/03/2021
 */
public class Riddle
{
    private String title;
    private String author;
    private String imageUrl;
    private String textRiddle;
    private String hints;
    private String solution;

    public Riddle(String title, String author, String imageUrl, String textRiddle, String hints, String solution)
    {
        this.title = title;
        this.author = author;
        this.imageUrl = imageUrl;
        this.textRiddle = textRiddle;
        this.hints = hints;
        this.solution = solution;
    }

    public String getTitle()
    {
        return title;
    }

    public String getAuthor()
    {
        return author;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public String getTextRiddle()
    {
        return textRiddle;
    }

    public String getHints()
    {
        return hints;
    }

    public String getSolution()
    {
        return solution;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public void setTextRiddle(String textRiddle)
    {
        this.textRiddle = textRiddle;
    }

    public void setHints(String hints)
    {
        this.hints = hints;
    }

    public void setSolution(String solution)
    {
        this.solution = solution;
    }

    @Override
    public String toString() {
        return "Riddle{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", textRiddle='" + textRiddle + '\'' +
                ", hints='" + hints + '\'' +
                ", solution='" + solution + '\'' +
                '}';
    }
}
