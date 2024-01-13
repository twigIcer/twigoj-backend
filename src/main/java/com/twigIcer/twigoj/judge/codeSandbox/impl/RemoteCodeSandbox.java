package com.twigIcer.twigoj.judge.codeSandbox.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.twigIcer.twigoj.common.ErrorCode;
import com.twigIcer.twigoj.exception.BusinessException;
import com.twigIcer.twigoj.judge.codeSandbox.CodeSandbox;
import com.twigIcer.twigoj.judge.codeSandbox.model.ExecuteCodeRequest;
import com.twigIcer.twigoj.judge.codeSandbox.model.ExecuteCodeResponse;
import org.apache.commons.lang3.StringUtils;

/**
 * 远程代码沙箱（实际调用接口）
 */
public class RemoteCodeSandbox implements CodeSandbox {

    //定义鉴权
    private static final String AUTH_REQUEST_HEADER = "auth";

    private static String AUTH_REQUEST_SECERT = "secertKey";
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程代码沙箱");
        String url = "http://localhost:8090/executeCode";
        String json = JSONUtil.toJsonStr(executeCodeRequest);
        String responseStr = HttpUtil.createPost(url)
                .header(AUTH_REQUEST_HEADER,AUTH_REQUEST_SECERT)
                .body(json)
                .execute()
                .body();
        if(StringUtils.isBlank(responseStr)){
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR, "executeCode remoteSandbox error, message = " + responseStr);
        }
        return JSONUtil.toBean(responseStr,ExecuteCodeResponse.class);
    }
}
