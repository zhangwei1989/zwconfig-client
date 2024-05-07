package io.github.zhangwei1989.zwconfig.client.repository;

import cn.kimmking.utils.HttpUtils;
import com.alibaba.fastjson.TypeReference;
import io.github.zhangwei1989.zwconfig.client.listener.ConfigChangeEvent;
import io.github.zhangwei1989.zwconfig.client.listener.ConfigChangerListener;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * ZWConfig repository impl
 *
 * @Author : zhangwei(331874675@qq.com)
 * @Create : 2024/5/7
 */
@Slf4j
public class ZWConfigRepositoryImpl implements ZWConfigRepository {

    private ConfigMeta configMeta;

    private Map<String, List<Configs>> cacheConfig = new HashMap<>();

    private Map<String, Long> VERSIONS = new HashMap<>();

    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    private List<ConfigChangerListener> listeners = new ArrayList<>();

    public ZWConfigRepositoryImpl(ConfigMeta configMeta) {
        this.configMeta = configMeta;
        executorService.scheduleWithFixedDelay(this::heartBeat, 1000, 5000, TimeUnit.MILLISECONDS);
    }

    @Override
    public Map<String, String> getConfig() {
        String key = configMeta.genKey();
        // 如果缓存里已经有了，就不需要去远程 zwconfig-server 去获取了，直接获取
        if (cacheConfig.containsKey(key)) {
            return mapToConfig(cacheConfig.get(key));
        }

        List<Configs> configs = fetchAll();
        cacheConfig.put(key, configs);

        return mapToConfig(configs);
    }

    @Override
    public void heartBeat() {
        String key = configMeta.genKey();
        Long oldVersion = VERSIONS.getOrDefault(key, -1L);
        Long currentVersion = HttpUtils.httpGet(configMeta.versionPath(), Long.class);

        if (oldVersion < currentVersion) {
            log.info("======> [ZWConfig] need to update configs, oldVersion is {}, currentVersion is {}", oldVersion, currentVersion);
            cacheConfig.put(key, fetchAll());
            VERSIONS.put(key, currentVersion);
            // 监听器发布配置更新的事件
            try {
                listeners.forEach(l -> l.onChange(new ConfigChangeEvent(configMeta, mapToConfig(cacheConfig.get(key)))));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void addConfigListener(ConfigChangerListener listener) {
        listeners.add(listener);
    }

    private Map<String, String> mapToConfig(List<Configs> configs) {
        Map<String, String> resultMap = new HashMap<>();
        configs.forEach(c -> resultMap.put(c.getPkey(), c.getPval()));

        return resultMap;
    }

    private List<Configs> fetchAll() {
        return HttpUtils.httpGet(configMeta.listPath(), new TypeReference<>() {});
    }

}
