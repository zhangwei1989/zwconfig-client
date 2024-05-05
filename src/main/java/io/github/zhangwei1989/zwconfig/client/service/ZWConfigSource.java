package io.github.zhangwei1989.zwconfig.client.service;

/**
 * ZWConfigSource
 *
 * @Author : zhangwei(331874675@qq.com)
 * @Create : 2024/5/5
 */
public interface ZWConfigSource {

    String[] getPropertyNames();

    Object getProperty(String name);

}
