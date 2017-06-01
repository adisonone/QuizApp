package com.example.aditya.studentquiz;

import javax.sql.StatementEvent;

/**
 * Created by aditya on 11-05-2017.
 */

public class QuesAns {
    private String Ques;
    private String ans;
    private String result;

    public QuesAns() {
    }

    public QuesAns(String ques, String ans,String result) {
        Ques = ques;
        this.ans = ans;
        this.result=result;
    }

    public String getQues() {
        return Ques;
    }

    public void setQues(String ques) {
        Ques = ques;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
