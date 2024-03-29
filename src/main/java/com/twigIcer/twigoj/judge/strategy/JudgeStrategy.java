package com.twigIcer.twigoj.judge.strategy;

import com.twigIcer.twigoj.judge.codeSandbox.model.JudgeInfo;

/**
 * 判题策略
 */
public interface JudgeStrategy {
    /**
     * 执行判题
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext);
}
