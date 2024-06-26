package io.github.zhangwei1989.zwconfig.client.config;

import io.github.zhangwei1989.zwconfig.client.value.SpringValueProcessor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Arrays;
import java.util.Optional;

/**
 * registrar for ZWConfig
 *
 * @Author : zhangwei(331874675@qq.com)
 * @Create : 2024/5/5
 */
public class ZWConfigRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        registerClass(registry, ZWConfigPropertySourceRegistrar.class);
        registerClass(registry, SpringValueProcessor.class);
    }

    private static void registerClass(BeanDefinitionRegistry registry, Class<?> aClass) {
        // 如果已经有 aClass 这个 bean 定义了，则直接返回
        Optional<String> first = Arrays.stream(registry.getBeanDefinitionNames())
                .filter(x -> aClass.getName().equals(x)).findFirst();

        if (first.isPresent()) {
            return;
        }

        BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(aClass).getBeanDefinition();
        registry.registerBeanDefinition(aClass.getName(), beanDefinition);
    }

}
