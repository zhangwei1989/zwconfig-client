package io.github.zhangwei1989.zwconfig.client.repository;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ConfigMeta
 *
 * @Author : zhangwei(331874675@qq.com)
 * @Create : 2024/5/7
 */
@Data
@AllArgsConstructor
public class ConfigMeta {

    private String app;

    private String env;

    private String ns;

    private String server;

    public String genKey() {
        return app + "-" + env + "-" + ns;
    }

    public String listPath() {
        return path("list");
    }

    public String versionPath() {
        return path("version");
    }

    private String path(String context) {
        return this.getServer() + "/"
                + context
                + "?app=" + this.getApp()
                + "&env=" + this.getEnv()
                + "&ns=" + this.getNs();
    }
}
