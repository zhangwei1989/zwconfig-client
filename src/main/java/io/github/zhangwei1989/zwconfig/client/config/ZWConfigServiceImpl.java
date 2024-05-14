package io.github.zhangwei1989.zwconfig.client.config;

import io.github.zhangwei1989.zwconfig.client.repository.ZWRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ZW config service impl
 *
 * @Author : zhangwei(331874675@qq.com)
 * @Create : 2024/5/5
 */
@Slf4j
public class ZWConfigServiceImpl implements ZWConfigService {

    Map<String, String> config;

    private ApplicationContext applicationContext;

    public ZWConfigServiceImpl(ApplicationContext applicationContext, Map<String, String> config) {
        this.applicationContext = applicationContext;
        this.config = config;
    }

    @Override
    public String[] getPropertyNames() {
        return this.config.keySet().toArray(new String[0]);
    }

    @Override
    public String getProperty(String name) {
        return this.config.get(name);
    }

    @Override
    public void onChange(ZWRepository.ChangeEvent event) {
        Set<String> keys = calcChangeKeys(this.config, event.config());
        if (keys.isEmpty()) {
            return;
        }

        this.config = event.config();

        log.info("======> [ZWConfig] fire an EnvironmentChangeEvent with keys: {}", keys);
        applicationContext.publishEvent(new EnvironmentChangeEvent(keys));
    }

    private Set<String> calcChangeKeys(Map<String, String> oldConfigs, Map<String, String> newConfigs) {
        if (oldConfigs.isEmpty()) {
            return newConfigs.keySet();
        }

        if (newConfigs.isEmpty()) {
            return oldConfigs.keySet();
        }

        Set<String> news = newConfigs.keySet().stream().filter(key -> !newConfigs.get(key).equals(oldConfigs.get(key))).collect(Collectors.toSet());
        oldConfigs.keySet().stream().filter(key -> !newConfigs.containsKey(key)).forEach(news::add);

        return news;
    }
}
