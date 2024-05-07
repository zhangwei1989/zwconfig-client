package io.github.zhangwei1989.zwconfig.client.listener;

/**
 * listener for config change
 *
 * @Author : zhangwei(331874675@qq.com)
 * @Create : 2024/5/7
 */
public interface ConfigChangerListener {

    void onChange(ConfigChangeEvent event);

}
