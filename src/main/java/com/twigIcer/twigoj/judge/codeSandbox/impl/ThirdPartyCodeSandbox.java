package com.twigIcer.twigoj.judge.codeSandbox.impl;

import com.twigIcer.twigoj.judge.codeSandbox.CodeSandbox;
import com.twigIcer.twigoj.judge.codeSandbox.model.ExecuteCodeRequest;
import com.twigIcer.twigoj.judge.codeSandbox.model.ExecuteCodeResponse;

/**
 * 第三方代码沙箱（调用现成的代码沙箱）
 */
public class ThirdPartyCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("第三方代码沙箱");
        return null;
    }
}
