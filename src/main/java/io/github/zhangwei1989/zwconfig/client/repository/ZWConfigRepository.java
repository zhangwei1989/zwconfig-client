package io.github.zhangwei1989.zwconfig.client.repository;

import io.github.zhangwei1989.zwconfig.client.listener.ConfigChangerListener;

import java.util.Map;

/**
 * ZWConfig repository
 *
 * @Author : zhangwei(331874675@qq.com)
 * @Create : 2024/5/7
 */
public interface ZWConfigRepository {

    static ZWConfigRepository getDefault(ConfigMeta configMeta) {
        return new ZWConfigRepositoryImpl(configMeta);
    }

    Map<String, String> getConfig();

    void heartBeat();

    void addConfigListener(ConfigChangerListener listener);

}
