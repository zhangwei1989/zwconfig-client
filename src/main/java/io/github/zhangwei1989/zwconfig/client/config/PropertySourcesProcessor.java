package io.github.zhangwei1989.zwconfig.client.config;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

/**
 * PropertySources processor
 *
 * @Author : zhangwei(331874675@qq.com)
 * @Create : 2024/5/5
 */
@Data
public class PropertySourcesProcessor  implements BeanFactoryPostProcessor, PriorityOrdered, EnvironmentAware {

    private final static String ZW_PROPERTY_SOURCES = "ZWPropertySources";
    private final static String ZW_PROPERTY_SOURCE = "ZWPropertySource";

    private Environment environment;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        ConfigurableEnvironment env = (ConfigurableEnvironment) environment;
        if (env.getPropertySources().contains(ZW_PROPERTY_SOURCES)) {
            return;
        }

        // 通过 http 请求，去 zwconfig server 获取配置 TODO
        Map<String, String> config = new HashMap<>();
        config.put("zw.a", "deva500");
        config.put("zw.b", "devb600");
        config.put("zw.c", "devc700");

        ZWConfigService configService = new ZWConfigServiceImpl(config);
        ZWPropertySource propertySource = new ZWPropertySource(ZW_PROPERTY_SOURCE, configService);
        CompositePropertySource composite = new CompositePropertySource(ZW_PROPERTY_SOURCES);
        composite.addPropertySource(propertySource);
        env.getPropertySources().addFirst(composite);
    }

    @Override
    public int getOrder() {
        return PriorityOrdered.HIGHEST_PRECEDENCE;
    }
}
