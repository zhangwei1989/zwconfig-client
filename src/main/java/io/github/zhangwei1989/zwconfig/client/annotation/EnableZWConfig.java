package io.github.zhangwei1989.zwconfig.client.annotation;

import io.github.zhangwei1989.zwconfig.client.config.ZWConfigRegistar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * ZW config client entrypoint
 *
 * @Author : zhangwei(331874675@qq.com)
 * @Create : 2024/5/5
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Import(ZWConfigRegistar.class)
public @interface EnableZWConfig {
}
