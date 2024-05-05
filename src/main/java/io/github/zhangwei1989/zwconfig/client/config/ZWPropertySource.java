package io.github.zhangwei1989.zwconfig.client.config;

import org.springframework.core.env.EnumerablePropertySource;

/**
 * ZW property source
 *
 * @Author : zhangwei(331874675@qq.com)
 * @Create : 2024/5/5
 */
public class ZWPropertySource extends EnumerablePropertySource<ZWConfigService> {

    public ZWPropertySource(String name, ZWConfigService source) {
        super(name, source);
    }

    @Override
    public String[] getPropertyNames() {
        return source.getPropertyNames();
    }

    @Override
    public Object getProperty(String name) {
        return source.getProperty(name);
    }

}
