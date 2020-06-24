package com.traap.traapapp.ui.fragments.performanceEvaluation;

public interface PerformanceEvaluationActionView
{
    void showErrorAlert(String message);

    void onPlayerShowEvaluatedResult(int matchId, int positionId, String name, String imageURL);

    void onPlayerSetEvaluation(int matchId, int positionId, String name, String imageURL);
}
