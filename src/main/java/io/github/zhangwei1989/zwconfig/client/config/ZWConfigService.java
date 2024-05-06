package io.github.zhangwei1989.zwconfig.client.config;

import io.github.zhangwei1989.zwconfig.client.repository.ZWRepository;

/**
 * ZW config service
 *
 * @Author : zhangwei(331874675@qq.com)
 * @Create : 2024/5/5
 */
public interface ZWConfigService {

    static ZWConfigService getDefault(ConfigMeta meta) {
        ZWRepository repository = ZWRepository.getDefault(meta);
        return new ZWConfigServiceImpl(repository.getConfig());
    }

    String[] getPropertyNames();

    String getProperty(String name);

}
