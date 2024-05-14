package io.github.zhangwei1989.zwconfig.client.value;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Field;

/**
 * SpringValue
 *
 * @Author : zhangwei(331874675@qq.com)
 * @Create : 2024/5/14
 */
@Data
@AllArgsConstructor
public class SpringValue {

    private String key;

    private String beanName;

    private Object bean;

    private String placeholder;

    private Field field;

}
