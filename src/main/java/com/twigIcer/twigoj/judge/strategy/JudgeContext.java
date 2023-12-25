package com.twigIcer.twigoj.judge.strategy;

import com.twigIcer.twigoj.model.dto.question.JudgeCase;
import com.twigIcer.twigoj.model.dto.questionSubmit.JudgeInfo;
import com.twigIcer.twigoj.model.entity.Question;
import com.twigIcer.twigoj.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * 判题上下文
 */
@Data
public class JudgeContext {
    private JudgeInfo judgeInfo;
    private List<String> inputList;
    private List<String> outputList;
    private List<JudgeCase> judgeCaseList;
    private Question question;
    private QuestionSubmit questionSubmit;
}
