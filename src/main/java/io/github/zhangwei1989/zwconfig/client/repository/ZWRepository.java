package io.github.zhangwei1989.zwconfig.client.repository;

import io.github.zhangwei1989.zwconfig.client.config.ConfigMeta;
import io.github.zhangwei1989.zwconfig.client.config.ZWConfigServiceImpl;

import java.util.Map;

/**
 * interface to get config from remote
 *
 * @Author : zhangwei(331874675@qq.com)
 * @Create : 2024/5/5
 */
public interface ZWRepository {

    static ZWRepository getDefault(ConfigMeta meta) {
        return new ZWRepositoryImpl(meta);
    }

    Map<String, String> getConfig();
}
