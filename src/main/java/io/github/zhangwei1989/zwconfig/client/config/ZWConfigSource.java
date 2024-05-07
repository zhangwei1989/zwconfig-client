package io.github.zhangwei1989.zwconfig.client.config;

import io.github.zhangwei1989.zwconfig.client.repository.ConfigMeta;
import io.github.zhangwei1989.zwconfig.client.repository.ZWConfigRepository;

/**
 * ZWConfigSource
 *
 * @Author : zhangwei(331874675@qq.com)
 * @Create : 2024/5/5
 */
public interface ZWConfigSource {

    static ZWConfigSource getDefault(ConfigMeta configMeta) {
        ZWConfigRepository repository = ZWConfigRepository.getDefault(configMeta);
        return new ZWConfigSourceImpl(repository.getConfig());
    }

    String[] getPropertyNames();

    Object getProperty(String name);

}
