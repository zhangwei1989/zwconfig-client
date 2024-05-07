package io.github.zhangwei1989.zwconfig.client.config;

import org.springframework.core.env.EnumerablePropertySource;

/**
 * ZWConfig property source
 *
 * @Author : zhangwei(331874675@qq.com)
 * @Create : 2024/5/5
 */
public class ZWConfigPropertySource extends EnumerablePropertySource<ZWConfigSource> {

    public ZWConfigPropertySource(String name, ZWConfigSource configSource) {
        super(name, configSource);
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
