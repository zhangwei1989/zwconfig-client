package io.github.zhangwei1989.zwconfig.client.config;

import io.github.zhangwei1989.zwconfig.client.listener.ConfigChangeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * ZWConfigSourceImpl
 *
 * @Author : zhangwei(331874675@qq.com)
 * @Create : 2024/5/5
 */
@Slf4j
public class ZWConfigSourceImpl implements ZWConfigSource {

    private ApplicationContext applicationContext;

    Map<String, String> config;

    public ZWConfigSourceImpl(ApplicationContext applicationContext, Map<String, String> config) {
        this.applicationContext = applicationContext;
        this.config = config;
    }

    @Override
    public String[] getPropertyNames() {
        return config.keySet().toArray(new String[0]);
    }

    @Override
    public Object getProperty(String name) {
        return config.get(name);
    }

    @Override
    public void onChange(ConfigChangeEvent event) {
        this.config = event.getConfig();
        log.info("======> [ZWConfig] fire an ConfigChangeEvent with keys: {}", config.keySet());
        applicationContext.publishEvent(new EnvironmentChangeEvent(config.keySet()));
    }
}
