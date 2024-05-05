package io.github.zhangwei1989.zwconfig.client.service;

import org.springframework.core.env.EnumerablePropertySource;

/**
 * ZWConfig property source
 *
 * @Author : zhangwei(331874675@qq.com)
 * @Create : 2024/5/5
 */
public class ZWConfigPropertySource extends EnumerablePropertySource<ZWConfigSource> {

    private String name;

    private ZWConfigSource configSource;

    public ZWConfigPropertySource(String name, ZWConfigSource configSource) {
        super(name, configSource);
        this.name = name;
        this.configSource = configSource;
    }

    @Override
    public String[] getPropertyNames() {
        return configSource.getPropertyNames();
    }

    @Override
    public Object getProperty(String name) {
        return configSource.getProperty(name);
    }
}
