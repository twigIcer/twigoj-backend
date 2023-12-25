package com.twigIcer.twigoj.judge;

import com.twigIcer.twigoj.model.entity.QuestionSubmit;

public interface JudgeService {

    QuestionSubmit doJudge(long questionSubmitId);
}
