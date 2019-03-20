package com.example.quizss;

public class Player {
    private double mScore;
    private String mName;

    public Player(String name, double score){
        mScore = score;
        mName = name;
    }

    public String getName(){
        return mName;
    }
    public void setName(String name){
        mName = name;
    }
    public double getScore(){
        return mScore;
    }
    public void setScore(double score){
        mScore = score;
    }
    @Override
    public String toString() {
        return "{name : "+mName+",score : " + mScore+"}";
    }
    public int compareTo(Player other){
        if(other.getScore() == mScore) return 0;
        else if(other.getScore() > mScore) return 1;
        else return -1;
    }
}
