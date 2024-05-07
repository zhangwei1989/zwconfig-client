package io.github.zhangwei1989.zwconfig.client.listener;

import io.github.zhangwei1989.zwconfig.client.repository.ConfigMeta;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

/**
 * ConfigChangeEvent
 *
 * @Author : zhangwei(331874675@qq.com)
 * @Create : 2024/5/7
 */
@Data
@AllArgsConstructor
public class ConfigChangeEvent {

    private ConfigMeta configMeta;

    private Map<String, String> config;

}
