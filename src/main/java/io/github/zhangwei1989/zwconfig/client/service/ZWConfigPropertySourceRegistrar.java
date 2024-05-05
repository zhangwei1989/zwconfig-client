package io.github.zhangwei1989.zwconfig.client.service;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * registrar for ZWConfigPropertySource
 *
 * @Author : zhangwei(331874675@qq.com)
 * @Create : 2024/5/5
 */
@Data
public class ZWConfigPropertySourceRegistrar implements BeanFactoryPostProcessor, EnvironmentAware, PriorityOrdered {

    private Environment environment;

    private final static String ZWCONFIG_PROPERTY_SOURCES = "ZWConfigPropertySources";

    private final static String ZWCONFIG_PROPERTY_SOURCE = "ZWConfigPropertySource";

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 如果已经有ZWConfigPropertySourceRegistrar这个 bean 了，那么就不需要额外操作了
        ConfigurableEnvironment env = (ConfigurableEnvironment) environment;

        Optional<PropertySource<?>> first = env.getPropertySources().stream()
                .filter(x -> ZWCONFIG_PROPERTY_SOURCES.equals(x.getName())).findFirst();

        if (first.isPresent()) {
            return;
        }

        // 从 zwconfig-server 获取配置信息
        // TODO 先 MOCK
        Map<String, String> config = new HashMap<>();
        config.put("zw.a", "deva100");
        config.put("zw.b", "devb100");
        config.put("zw.c", "devc100");

        ZWConfigSource configSource = new ZWConfigSourceImpl(config);
        ZWConfigPropertySource propertySource = new ZWConfigPropertySource(ZWCONFIG_PROPERTY_SOURCE, configSource);
        CompositePropertySource compositePropertySource = new CompositePropertySource(ZWCONFIG_PROPERTY_SOURCES);
        compositePropertySource.addPropertySource(propertySource);

        // 放到所有PropertySource的第一位
        env.getPropertySources().addFirst(compositePropertySource);
    }

    @Override
    public int getOrder() {
        return PriorityOrdered.HIGHEST_PRECEDENCE;
    }
}
