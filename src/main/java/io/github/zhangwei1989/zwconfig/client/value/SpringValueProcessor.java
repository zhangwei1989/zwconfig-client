package io.github.zhangwei1989.zwconfig.client.value;

import cn.kimmking.utils.FieldUtils;
import io.github.zhangwei1989.zwconfig.client.util.PlaceholderHelper;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.event.EventListener;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

/**
 * SpringValueProcessor
 *
 * @Author : zhangwei(331874675@qq.com)
 * @Create : 2024/5/14
 */
@Slf4j
public class SpringValueProcessor implements BeanPostProcessor, BeanFactoryAware {

    @Setter
    private BeanFactory beanFactory;

    private PlaceholderHelper placeholderHelper = PlaceholderHelper.getInstance();

    private MultiValueMap<String, SpringValue> VALUE_HOLDER = new LinkedMultiValueMap<>();

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 构建 SpringValue ，缓存至 valueConfigs
        List<Field> fields = FieldUtils.findAnnotatedField(bean.getClass(), Value.class);
        fields.forEach(f -> {
            String placeholder = f.getAnnotation(Value.class).value();
            Set<String> keys = placeholderHelper.extractPlaceholderKeys(placeholder);
            keys.forEach(k -> {
                SpringValue springValue = new SpringValue(k, beanName, bean, placeholder, f);
                VALUE_HOLDER.add(k, springValue);
            });
        });

        return bean;
    }

    @EventListener
    private void onChange(EnvironmentChangeEvent event) {
        event.getKeys().forEach(k -> {
            List<SpringValue> springValues = VALUE_HOLDER.get(k);
            log.info("======> [ZWConfig] the springValues of key {} is : {}", k, springValues);
            if (springValues == null || springValues.isEmpty()) {
                return;
            }

            springValues.forEach(springValue -> {
                Object newValue = placeholderHelper.resolvePropertyValue((ConfigurableBeanFactory) beanFactory,
                        springValue.getBeanName(), springValue.getPlaceholder());
                springValue.getField().setAccessible(true);
                try {
                    springValue.getField().set(springValue.getBean(), newValue);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }

                log.info("======> [ZWConfig] the field {} of {} set to new value {}", springValue.getField(),
                        springValue.getBeanName(), newValue);
            });
        });
    }

}
