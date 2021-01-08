package org.apache.dubbo.samples.configcenter;

import com.ctrip.framework.apollo.openapi.client.ApolloOpenApiClient;
import com.ctrip.framework.apollo.openapi.dto.OpenAppDTO;
import com.ctrip.framework.apollo.openapi.dto.OpenAppNamespaceDTO;
import com.ctrip.framework.apollo.openapi.dto.OpenEnvClusterDTO;
import com.ctrip.framework.apollo.openapi.dto.OpenItemDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ApolloUtil {

    private static final Logger logger = LoggerFactory.getLogger(ApolloUtil.class);

    public static void importConfigs() {
        String zookeeperAddress = System.getProperty("zookeeper.address", "127.0.0.1");
        String zookeeperPort = System.getProperty("zookeeper.port", "2181");

        Map<String,String> props = new LinkedHashMap<>();
        props.put("dubbo.registry.address", String.format("zookeeper://%s:%s", zookeeperAddress, zookeeperPort));

        logger.info("Importing configs to apollo : {}", props);
        try {
            String portalAddress = System.getProperty("apollo.address", "127.0.0.1");
            String portalPort = System.getProperty("apollo.portal-port", "8070");
            //How to generate apollo token by program ?
            String token = System.getProperty("apollo.portal-token");

            ApolloOpenApiClient apolloApiClient = ApolloOpenApiClient
                    .newBuilder()
                    .withPortalUrl(String.format("http://%s:%s", portalAddress, portalPort))
                    .withToken(token)
                    .build();

            //find app
            List<OpenAppDTO> allApps = apolloApiClient.getAllApps();
            OpenAppDTO appDTO = allApps.get(0);
            String appId = appDTO.getAppId();

            //find env
            List<OpenEnvClusterDTO> envClusterInfo = apolloApiClient.getEnvClusterInfo(appId);
            OpenEnvClusterDTO openEnvClusterDTO = envClusterInfo.get(0);
            String env = openEnvClusterDTO.getEnv();
            List<String> clusters = new ArrayList<>(openEnvClusterDTO.getClusters());
            String cluster = clusters.get(0);

            //create dubbo namespace
            String namespace = "dubbo";
            OpenAppNamespaceDTO appNamespaceDTO = new OpenAppNamespaceDTO();
            appNamespaceDTO.setName(namespace);
            appNamespaceDTO.setAppId(appDTO.getAppId());
            appNamespaceDTO.setFormat("properties");
            appNamespaceDTO.setDataChangeCreatedBy("dubbo");
            apolloApiClient.createAppNamespace(appNamespaceDTO);

            //import configs
            props.forEach((key,value) -> {
                OpenItemDTO itemDTO = new OpenItemDTO();
                itemDTO.setKey(key);
                itemDTO.setValue(value);
                apolloApiClient.createItem(appId, env, cluster, namespace, itemDTO);
            } );
            logger.info("Import configs to apollo successfully.");
        } catch (Exception e) {
            logger.error("Import configs failed", e);
            throw e;
        }
    }

}
