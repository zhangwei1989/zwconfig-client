package io.github.zhangwei1989.zwconfig.client.config;

import java.util.Map;

/**
 * ZW config service impl
 *
 * @Author : zhangwei(331874675@qq.com)
 * @Create : 2024/5/5
 */
public class ZWConfigServiceImpl implements ZWConfigService {

    Map<String, String> config;

    public ZWConfigServiceImpl(Map<String, String> config) {
        this.config = config;
    }

    @Override
    public String[] getPropertyNames() {
        return this.config.keySet().toArray(new String[0]);
    }

    @Override
    public String getProperty(String name) {
        return this.config.get(name);
    }

}
