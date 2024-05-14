package io.github.zhangwei1989.zwconfig.client.config;

import io.github.zhangwei1989.zwconfig.client.listener.ConfigChangeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
        Set<String> changedKeys = calChangedKeys(config, event.getConfig());
        if (changedKeys.isEmpty()) {
            return;
        }

        this.config = event.getConfig();
        log.info("======> [ZWConfig] fire an ConfigChangeEvent with keys: {}", changedKeys);
        applicationContext.publishEvent(new EnvironmentChangeEvent(changedKeys));
    }

    private Set<String> calChangedKeys(Map<String, String> oldConfigs, Map<String, String> newConfigs) {
        Set<String> changedKeys = oldConfigs.keySet().stream()
                .filter(k -> !oldConfigs.get(k).equals(newConfigs.get(k))).collect(Collectors.toSet());

        // 处理新增的配置
        changedKeys.addAll(newConfigs.keySet().stream().filter(k -> !oldConfigs.containsKey(k)).collect(Collectors.toSet()));

        return changedKeys;
    }
}
