package com.traap.traapapp.ui.adapters.Leaguse;

/**
 * Created by feng on 2017/4/10.
 */

public class DataBean {
    public String teamId;
    public String teamTitle;
    public String matches;
    public String won;
    public String drawn;
    public String lost;
    public String goals_score;
    public String goals_canceded;
    public String goals_diff;
    public String point;
    public String imageLogo;

    public DataBean(String teamId,
            String teamTitle, String matches, String won, String drawn, String lost, String goals_score, String goals_canceded,
            String goals_diff, String point,String imageLogo) {
        this.teamTitle = teamTitle;
        this.matches = matches;
        this.won = won;
        this.drawn = drawn;
        this.lost = lost;
        this.goals_score = goals_score;
        this.goals_canceded = goals_canceded;
        this.goals_diff = goals_diff;
        this.point = point;
        this.imageLogo = imageLogo;
        this.teamId = teamId;
    }
}
