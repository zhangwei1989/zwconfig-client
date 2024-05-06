package io.github.zhangwei1989.zwconfig.client.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ConfigMeta
 *
 * @Author : zhangwei(331874675@qq.com)
 * @Create : 2024/5/6
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigMeta {

    private String app;

    private String env;

    private String ns;

    private String configServer;

}
