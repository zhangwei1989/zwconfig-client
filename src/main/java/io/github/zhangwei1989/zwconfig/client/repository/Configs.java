package io.github.zhangwei1989.zwconfig.client.repository;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Configs
 *
 * @Author : zhangwei(331874675@qq.com)
 * @Create : 2024/4/29
 */
@Data
@AllArgsConstructor
public class Configs {

    private String app;

    private String env;

    private String ns;

    private String pkey;

    private String pval;

}
