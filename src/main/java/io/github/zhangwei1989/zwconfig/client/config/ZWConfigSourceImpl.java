package io.github.zhangwei1989.zwconfig.client.config;

import java.util.Map;

/**
 * ZWConfigSourceImpl
 *
 * @Author : zhangwei(331874675@qq.com)
 * @Create : 2024/5/5
 */
public class ZWConfigSourceImpl implements ZWConfigSource {

    Map<String, String> config;

    public ZWConfigSourceImpl(Map<String, String> config) {
        this.config = config;
    }

    @Override
    public String[] getPropertyNames() {
        return config.keySet().toArray(new String[0]);
    }

    @Override
    public Object getProperty(String name) {
        return config.get(name);
    }
}
