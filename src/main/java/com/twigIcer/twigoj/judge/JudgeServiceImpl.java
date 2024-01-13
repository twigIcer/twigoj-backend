package com.twigIcer.twigoj.judge;

import cn.hutool.json.JSONUtil;
import com.twigIcer.twigoj.common.ErrorCode;
import com.twigIcer.twigoj.exception.BusinessException;
import com.twigIcer.twigoj.judge.codeSandbox.CodeSandbox;
import com.twigIcer.twigoj.judge.codeSandbox.CodeSandboxFactory;
import com.twigIcer.twigoj.judge.codeSandbox.CodeSandboxProxy;
import com.twigIcer.twigoj.judge.codeSandbox.model.ExecuteCodeRequest;
import com.twigIcer.twigoj.judge.codeSandbox.model.ExecuteCodeResponse;
import com.twigIcer.twigoj.judge.codeSandbox.model.JudgeInfo;
import com.twigIcer.twigoj.judge.strategy.JudgeContext;
import com.twigIcer.twigoj.model.dto.question.JudgeCase;
import com.twigIcer.twigoj.model.entity.Question;
import com.twigIcer.twigoj.model.entity.QuestionSubmit;
import com.twigIcer.twigoj.model.enums.QuestionSubmitStatusEnum;
import com.twigIcer.twigoj.service.QuestionService;
import com.twigIcer.twigoj.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService{
    @Value("${codeSandbox.type:example}")
    private String type;

    @Resource
    private JudgeManager judgeManager;
    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;
    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if(questionSubmit == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"题目提交信息不存在");
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        if(question == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"题目不存在");
        }

        if(questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"题目正在判题");
        }
        //更改题目状态
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionSubmitService.updateById(questionSubmitUpdate);
        if(!update){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"题目状态更新失败");
        }

        //执行代码沙箱，获取执行结果
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        codeSandbox = new CodeSandboxProxy(codeSandbox);
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        //获取输入用例
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> list = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> inputList = list.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest =ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);

        List<String> outputList = executeCodeResponse.getOutputList();
        JudgeContext judgeContext = new JudgeContext();
        //根据执行结果，判断题目是否通过
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setJudgeCaseList(list);
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);

        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);
        //修改数据库中的判题结果
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        update = questionSubmitService.updateById(questionSubmitUpdate);
        if(!update){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"题目状态更新失败");
        }
        QuestionSubmit questionSubmitResult = questionSubmitService.getById(questionId);
        return questionSubmitResult;
    }
}
