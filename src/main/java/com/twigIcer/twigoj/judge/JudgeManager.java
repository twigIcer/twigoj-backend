package com.twigIcer.twigoj.judge;

import com.twigIcer.twigoj.judge.strategy.DefaultJudgeStrategy;
import com.twigIcer.twigoj.judge.strategy.JavaLangageJudgeStrategy;
import com.twigIcer.twigoj.judge.strategy.JudgeContext;
import com.twigIcer.twigoj.judge.strategy.JudgeStrategy;
import com.twigIcer.twigoj.model.dto.questionSubmit.JudgeInfo;
import com.twigIcer.twigoj.model.entity.QuestionSubmit;
import org.springframework.stereotype.Component;

/**
 * 判题管理，简化调用
 */
@Component
public class JudgeManager {
    JudgeInfo doJudge(JudgeContext judgeContext){
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if("java".equals(language)){
            judgeStrategy = new JavaLangageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
