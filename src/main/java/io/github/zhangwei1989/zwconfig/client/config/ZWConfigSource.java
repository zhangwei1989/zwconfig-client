package io.github.zhangwei1989.zwconfig.client.config;

import io.github.zhangwei1989.zwconfig.client.listener.ConfigChangerListener;
import io.github.zhangwei1989.zwconfig.client.repository.ConfigMeta;
import io.github.zhangwei1989.zwconfig.client.repository.ZWConfigRepository;
import org.springframework.context.ApplicationContext;

/**
 * ZWConfigSource
 *
 * @Author : zhangwei(331874675@qq.com)
 * @Create : 2024/5/5
 */
public interface ZWConfigSource extends ConfigChangerListener {

    static ZWConfigSource getDefault(ApplicationContext applicationContext, ConfigMeta configMeta) {
        ZWConfigRepository repository = ZWConfigRepository.getDefault(configMeta);
        ZWConfigSource configSource = new ZWConfigSourceImpl(applicationContext, repository.getConfig());
        repository.addConfigListener(configSource);
        return configSource;
    }

    String[] getPropertyNames();

    Object getProperty(String name);

}
