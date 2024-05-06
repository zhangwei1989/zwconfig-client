package io.github.zhangwei1989.zwconfig.client.repository;

import cn.kimmking.utils.HttpUtils;
import com.alibaba.fastjson.TypeReference;
import io.github.zhangwei1989.zwconfig.client.config.ConfigMeta;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * default impl for zw repository
 *
 * @Author : zhangwei(331874675@qq.com)
 * @Create : 2024/5/5
 */
@AllArgsConstructor
public class ZWRepositoryImpl  implements ZWRepository {

    private ConfigMeta meta;

    @Override
    public Map<String, String> getConfig() {
        String listPath = meta.getConfigServer() + "/list?app=" + meta.getApp()
                + "&env=" + meta.getEnv() +"&ns=" +meta.getNs();

        List<Configs> configs = HttpUtils.httpGet(listPath, new TypeReference<List<Configs>>(){});
        Map<String, String> resultMap = new HashMap<>();
        configs.forEach(c -> resultMap.put(c.getPkey(), c.getPval()));

        return resultMap;
    }

}
