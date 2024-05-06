package io.github.zhangwei1989.zwconfig.client.repository;

import cn.kimmking.utils.HttpUtils;
import com.alibaba.fastjson.TypeReference;
import io.github.zhangwei1989.zwconfig.client.config.ConfigMeta;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * default impl for zw repository
 *
 * @Author : zhangwei(331874675@qq.com)
 * @Create : 2024/5/5
 */
@Slf4j
@AllArgsConstructor
public class ZWRepositoryImpl implements ZWRepository {

    private ConfigMeta meta;

    Map<String, Long> versionMap = new HashMap<>();

    Map<String, Map<String, String>> configMap = new HashMap<>();

    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    private List<ChangeListener> listeners = new ArrayList<>();

    public ZWRepositoryImpl(ConfigMeta meta) {
        this.meta = meta;
        executor.scheduleWithFixedDelay(this::heartbeat, 1000, 5000, TimeUnit.MILLISECONDS);
    }

    public void addListener(ChangeListener listener) {
        listeners.add(listener);
    }

    @Override
    public Map<String, String> getConfig() {
        String key = meta.genKey();
        if (configMap.containsKey(key)) {
            return configMap.get(key);
        }

        Map<String, String> resultMap = findAll();
        return resultMap;
    }

    @NotNull
    private Map<String, String> findAll() {
        String listPath = meta.listPath();
        log.info("======> [ZWConfig] list all config from config server");
        List<Configs> configs = HttpUtils.httpGet(listPath, new TypeReference<List<Configs>>() {
        });
        Map<String, String> resultMap = new HashMap<>();
        configs.forEach(c -> resultMap.put(c.getPkey(), c.getPval()));
        return resultMap;
    }

    private void heartbeat() {
        String versionPath = meta.versionPath();
        Long version = HttpUtils.httpGet(versionPath, Long.class);
        String key = meta.genKey();
        Long oldVersion = versionMap.getOrDefault(key, -1L);

        if (version > oldVersion) {
            log.info("======> [ZWConfig] current = {}, old = {}", version, oldVersion);
            log.info("======> [ZWConfig] need update new version");
            Map<String, String> newConfigs = findAll();
            configMap.put(key, newConfigs);
            versionMap.put(key, version);
            listeners.forEach(l -> l.onChange(new ChangeEvent(meta, newConfigs)));
        }
    }

}
