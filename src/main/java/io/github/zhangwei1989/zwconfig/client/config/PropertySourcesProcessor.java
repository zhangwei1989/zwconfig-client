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
        ConfigurableEnvironment ENV = (ConfigurableEnvironment) environment;
        if (ENV.getPropertySources().contains(ZW_PROPERTY_SOURCES)) {
            return;
        }

        String app = ENV.getProperty("zwconfig.app", "app1");
        String env = ENV.getProperty("zwconfig.env", "dev");
        String ns = ENV.getProperty("zwconfig.ns", "public");
        String configServer = ENV.getProperty("zwconfig.configServer", "http://localhost:9129");

        ConfigMeta config = new ConfigMeta(app, env, ns, configServer);

        ZWConfigService configService = ZWConfigService.getDefault(config);
        ZWPropertySource propertySource = new ZWPropertySource(ZW_PROPERTY_SOURCE, configService);
        CompositePropertySource composite = new CompositePropertySource(ZW_PROPERTY_SOURCES);
        composite.addPropertySource(propertySource);
        ENV.getPropertySources().addFirst(composite);
    }

    @Override
    public int getOrder() {
        return PriorityOrdered.HIGHEST_PRECEDENCE;
    }
}
