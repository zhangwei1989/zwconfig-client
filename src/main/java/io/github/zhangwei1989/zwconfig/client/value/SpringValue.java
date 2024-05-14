package io.github.zhangwei1989.zwconfig.client.value;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Field;

/**
 * spring value
 *
 * @Author : zhangwei(331874675@qq.com)
 * @Create : 2024/5/8
 */
@Data
@AllArgsConstructor
public class SpringValue {

    private Object bean;

    private String beanName;

    private String key;

    private String placeholder;

    private Field field;

}
