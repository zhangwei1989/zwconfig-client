package io.github.zhangwei1989.zwconfig.client.config;

/**
 * ZW config service
 *
 * @Author : zhangwei(331874675@qq.com)
 * @Create : 2024/5/5
 */
public interface ZWConfigService {

    String[] getPropertyNames();

    String getProperty(String name);

}
