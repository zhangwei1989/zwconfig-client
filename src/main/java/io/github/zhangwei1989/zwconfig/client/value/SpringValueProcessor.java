package io.github.zhangwei1989.zwconfig.client.value;

import cn.kimmking.utils.FieldUtils;
import io.github.zhangwei1989.zwconfig.client.repository.ZWRepository;
import io.github.zhangwei1989.zwconfig.client.util.PlaceholderHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

/**
 * process spring value
 *
 * 1. 扫描所有的 spring value，保存起来
 * 2. 在配置变更时，更新所有的 Spring value
 *
 * @Author : zhangwei(331874675@qq.com)
 * @Create : 2024/5/8
 */
@Slf4j
public class SpringValueProcessor implements BeanPostProcessor, BeanFactoryAware, ApplicationListener<EnvironmentChangeEvent> {

    static final PlaceholderHelper helper = PlaceholderHelper.getInstance();

    static final MultiValueMap<String, SpringValue> VALUE_HOLDER = new LinkedMultiValueMap<>();

    private BeanFactory beanFactory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // 第一步
        List<Field> fields = FieldUtils.findAnnotatedField(bean.getClass(), Value.class);
        fields.forEach(field -> {
            log.info("======> [ZWConfig] find spring value {}", field);
            Value value = field.getAnnotation(Value.class);
            helper.extractPlaceholderKeys(value.value()).forEach(key -> {
                log.info("======> [ZWConfig] find spring key {}", key);
                SpringValue springValue = new SpringValue(bean, beanName, key, value.value(), field);
                VALUE_HOLDER.add(key, springValue);
            });
        });

        return bean;
    }

    @Override
    public void onApplicationEvent(EnvironmentChangeEvent event) {
        // 第二步
        event.getKeys().forEach(key -> {
            log.info("======> [ZWConfig] update spring value {}", key);
            List<SpringValue> springValues = VALUE_HOLDER.get(key);
            if (springValues == null || springValues.isEmpty()) {
                return;
            }

            springValues.forEach(springValue -> {
                log.info("======> [ZWConfig] update spring value {} for key {}", springValue, key);
                Object value = helper.resolvePropertyValue((ConfigurableBeanFactory) beanFactory,
                        springValue.getBeanName(), springValue.getPlaceholder());
                springValue.getField().setAccessible(true);
                try {
                    springValue.getField().set(springValue.getBean(), value);
                } catch (Exception e) {
                    log.error("======> [ZWConfig] update spring value error", e);
                }

            });
        });
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
