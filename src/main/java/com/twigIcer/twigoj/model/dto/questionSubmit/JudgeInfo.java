package com.twigIcer.twigoj.model.dto.questionSubmit;

import lombok.Data;

@Data
public class JudgeInfo {
    /**
     * 时间
     */
    private Long time;

    /**
     * 内存
     */
    private Long memory;

    /**
     * 程序执行信息
     */
    private String message;
}
