package com.twigIcer.twigoj.judge.codeSandbox.impl;

import com.twigIcer.twigoj.judge.codeSandbox.CodeSandbox;
import com.twigIcer.twigoj.judge.codeSandbox.model.ExecuteCodeRequest;
import com.twigIcer.twigoj.judge.codeSandbox.model.ExecuteCodeResponse;
import com.twigIcer.twigoj.model.dto.questionSubmit.JudgeInfo;
import com.twigIcer.twigoj.model.enums.JudgeInfoMessageEnum;
import com.twigIcer.twigoj.model.enums.QuestionSubmitStatusEnum;

import java.util.List;

/**
 * 示例代码沙箱（仅为了跑通流程）
 */
public class ExampleCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();

        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("测试执行成功");
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setTime(100L);
        judgeInfo.setMemory(100L);
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());

        executeCodeResponse.setJudgeInfo(judgeInfo);

        System.out.println("示例代码沙箱");
        return null;
    }
}
