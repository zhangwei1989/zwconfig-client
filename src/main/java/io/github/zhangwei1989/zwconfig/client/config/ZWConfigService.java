package io.github.zhangwei1989.zwconfig.client.config;

import io.github.zhangwei1989.zwconfig.client.repository.ZWRepository;
import org.springframework.context.ApplicationContext;

/**
 * ZW config service
 *
 * @Author : zhangwei(331874675@qq.com)
 * @Create : 2024/5/5
 */
public interface ZWConfigService extends ZWRepository.ChangeListener {

    static ZWConfigService getDefault(ApplicationContext applicationContext, ConfigMeta meta) {
        ZWRepository repository = ZWRepository.getDefault(meta);
        ZWConfigService configService = new ZWConfigServiceImpl(applicationContext, repository.getConfig());
        repository.addListener(configService);
        return configService;
    }

    String[] getPropertyNames();

    String getProperty(String name);

}
