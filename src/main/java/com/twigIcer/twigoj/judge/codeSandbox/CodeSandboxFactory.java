package com.twigIcer.twigoj.judge.codeSandbox;

import com.twigIcer.twigoj.judge.codeSandbox.impl.ExampleCodeSandbox;
import com.twigIcer.twigoj.judge.codeSandbox.impl.RemoteCodeSandbox;
import com.twigIcer.twigoj.judge.codeSandbox.impl.ThirdPartyCodeSandbox;

/**
 * 代码沙箱创建工厂（根据用户输入的字符串参数创建对应的代码沙箱实例）
 */
public class CodeSandboxFactory {
    public static CodeSandbox newInstance(String type){
        switch (type){
            case "example":
                return new ExampleCodeSandbox();
            case "remote":
                return new RemoteCodeSandbox();
            case "thirdParty":
                return new ThirdPartyCodeSandbox();
            default:
                return new ExampleCodeSandbox();
        }
    }
}
