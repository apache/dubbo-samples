/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.dubbo.scenario.builder;

import org.apache.dubbo.scenario.builder.exception.ConfigureFileNotFoundException;
import org.apache.dubbo.scenario.builder.vo.CaseConfiguration;
import org.apache.dubbo.scenario.builder.vo.DockerService;
import org.apache.dubbo.scenario.builder.vo.ServiceComponent;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

public class ConfigurationImpl implements IConfiguration {
    public static final String SAMPLE_TEST_IMAGE = "dubbo/sample-test";
    public static final String NATIVE_SAMPLE_TEST_IMAGE = "dubbo/native-sample-test";
    public static final String DUBBO_APP_DIR = "/usr/local/dubbo/app";
    public static final String DUBBO_SRC_DIR = "/usr/local/dubbo/src";
    public static final String DUBBO_JACOCO_RESULT_DIR = "/usr/local/dubbo/target-jacoco";
    public static final String DUBBO_JACOCO_RUNNER_FILE = "/usr/local/dubbo/jacocoagent.jar";
    public static final String DUBBO_LOG_DIR = "/usr/local/dubbo/logs";

    //ENV
    public static final String ENV_SERVICE_NAME = "SERVICE_NAME";
    public static final String ENV_SERVICE_DIR = "SERVICE_DIR";
    public static final String ENV_SERVICE_TYPE = "SERVICE_TYPE";
    public static final String ENV_APP_MAIN_CLASS = "APP_MAIN_CLASS";
    public static final String ENV_WAIT_PORTS_BEFORE_RUN = "WAIT_PORTS_BEFORE_RUN";
    public static final String ENV_CHECK_PORTS = "CHECK_PORTS";
    public static final String ENV_CHECK_LOG = "CHECK_LOG";
    public static final String ENV_WAIT_TIMEOUT = "WAIT_TIMEOUT";
    public static final String ENV_TEST_PATTERNS = "TEST_PATTERNS";
    public static final String ENV_JAVA_OPTS = "JAVA_OPTS";
    public static final String ENV_DEBUG_OPTS = "DEBUG_OPTS";
    public static final String ENV_SCENARIO_HOME = "SCENARIO_HOME";
    public static final String ENV_RUN_DELAY = "RUN_DELAY";

    //PROPS
    public static final String PROP_BASEDIR = "_basedir";
    public static final String PROP_SCENARIO_HOME = "_scenario_home";
    public static final String PROP_SCENARIO_NAME = "_scenario_name";

    private static final Logger logger = LoggerFactory.getLogger(ConfigurationImpl.class);
    private final CaseConfiguration configuration;
    private boolean isJdk9OrLater;
    private String scenarioHome;
    private String configBasedir;
    private String scenarioName;
    private final String scenarioLogDir;
    private final boolean jacocoEnable = Boolean.parseBoolean(System.getenv("JACOCO_ENABLE"));
    private int scenarioTimeout = 600;
    private int javaDebugPort = 20660;
    private int debugTimeout = 36000;
    private Set<Pattern> debugPatterns = new HashSet<>();
    private Set<String> debugServices = new HashSet<>();
    private Set<String> healthcheckServices = new HashSet<>();
    private String testImageVersion;

    public ConfigurationImpl() throws IOException, ConfigureFileNotFoundException {
        String configureFile = System.getProperty("configure.file");
        if (StringUtils.isBlank(configureFile)) {
            throw new ConfigureFileNotFoundException();
        }
        this.configBasedir = new File(configureFile).getParentFile().getCanonicalPath();
        this.configBasedir = this.configBasedir.replace(File.separator, "/");

        //set default scenarioHome dir to ${configBasedir}/target
        this.scenarioHome = System.getProperty("scenario.home");
        if (StringUtils.isBlank(scenarioHome)) {
            scenarioHome = configBasedir + "/target";
        }
        scenarioLogDir = new File(scenarioHome, "logs").getCanonicalPath();

        //set default scenarioName
        scenarioName = System.getProperty("scenario.name");
        if (StringUtils.isBlank(scenarioName)) {
            scenarioName = new File(configBasedir).getName();
        }

        testImageVersion = System.getProperty("test.image.version", "8");
        String verstr = StringUtils.substringBefore(testImageVersion, ".");
        int majorVersion = Integer.parseInt(verstr);
        isJdk9OrLater = (majorVersion > 8);

        String debugService = System.getProperty("debug.service");
        if (StringUtils.isNotBlank(debugService)) {
            String[] strs = debugService.split(",");
            for (String str : strs) {
                str = str.trim();
                if (StringUtils.isNotBlank(str)) {
                    String regex = "\\Q" + str.replace("*", "\\E.*?\\Q") + "\\E";
                    debugPatterns.add(Pattern.compile(regex));
                }
            }
        }

        this.configuration = loadCaseConfiguration(configureFile);

        //set scenario timeout
        if (this.configuration.getTimeout() > 0) {
            scenarioTimeout = this.configuration.getTimeout();
        }
        String timeout = System.getProperty("timeout");
        if (StringUtils.isNotBlank(timeout)) {
            scenarioTimeout = Integer.parseInt(timeout);
        }
        if (isDebug()) {
            scenarioTimeout = debugTimeout;
        }

        logger.info("scenarioName:{}, timeout: {}, testImageVersion: {}, debugServices:{}, config: {}",
                scenarioName, scenarioTimeout, testImageVersion, debugServices, configuration);

        if (StringUtils.isNotBlank(configuration.getIgnoreFor())) {
            logger.warn("{} Ignore testing for: {}", Constants.ERROR_MSG_FLAG, configuration.getIgnoreFor());
            System.exit(Constants.EXIT_IGNORED);
        }

    }

    private boolean isDebugService(String serviceName) {
        for (Pattern pattern : debugPatterns) {
            if (pattern.matcher(serviceName).matches()) {
                debugServices.add(serviceName);
                return true;
            }
        }
        return false;
    }

    private boolean isDebug() {
        return debugPatterns != null && debugPatterns.size() > 0;
    }

    private CaseConfiguration loadCaseConfiguration(String configureFile) throws IOException {
        // read 'props'
        String configYaml = FileUtil.readFully(configureFile);
        CaseConfiguration tmpConfiguration = parseConfiguration(configYaml);
        Map<String, String> props = tmpConfiguration.getProps();
        if (props == null) {
            props = new HashMap<>();
        }
        configProps(props);

        // process 'from', load parent config
        CaseConfiguration parentConfiguration = null;
        if (StringUtils.isNotBlank(tmpConfiguration.getFrom())) {
            String parentConfigYaml = loadParentConfigYaml(tmpConfiguration);
            CaseConfiguration tmpParentConfiguration = parseConfiguration(parentConfigYaml);

            //merge props, overwrite parent props
            if (tmpParentConfiguration.getProps() != null) {
                Map<String, String> newProps = new HashMap<>(tmpParentConfiguration.getProps());
                newProps.putAll(props);
                props = newProps;
            }

            // replace variables '${...}'
            String newParentConfigYaml = replaceHolders(parentConfigYaml, props);
            parentConfiguration = parseConfiguration(newParentConfigYaml);
        }

        // replace variables '${...}'
        String newConfigYaml = replaceHolders(configYaml, props);
        CaseConfiguration caseConfiguration = parseConfiguration(newConfigYaml);

        //merge globalSystemProps, overwrite parent
        if (parentConfiguration != null) {
            List<String> systemProps = mergeSystemProps(parentConfiguration.getSystemProps(), caseConfiguration.getSystemProps());
            caseConfiguration.setSystemProps(systemProps);
        }

        //merge services
        if (parentConfiguration != null && parentConfiguration.getServices() != null) {
            Map<String, ServiceComponent> newServices = new LinkedHashMap<>(parentConfiguration.getServices());
            if (caseConfiguration.getServices() != null) {
                newServices.putAll(caseConfiguration.getServices());
            }
            caseConfiguration.setServices(newServices);
        }

        fillupServices(caseConfiguration);
        return caseConfiguration;
    }

    private void configProps(Map<String, String> props) {
        props.put(PROP_BASEDIR, configBasedir);
        props.put(PROP_SCENARIO_HOME, scenarioHome);
        props.put(PROP_SCENARIO_NAME, scenarioName);
        Properties properties = System.getProperties();
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            if (entry.getKey() instanceof String && entry.getValue() instanceof String) {
                props.put((String) entry.getKey(), (String) entry.getValue());
            }
        }
    }

    private List<String> mergeSystemProps(List<String> parentSystemProps, List<String> childSystemProps) {
        List<String> newSystemProps = new ArrayList<>(parentSystemProps != null ? parentSystemProps : Collections.emptyList());
        if (childSystemProps != null) {
            childSystemProps.forEach(entry -> {
                String[] strs = entry.split("=");
                addOrReplaceKVEntry(newSystemProps, strs[0].trim(), strs.length > 1 ? strs[1].trim() : "");
            });
        }
        return newSystemProps;
    }

    private String loadParentConfigYaml(CaseConfiguration caseConfiguration) throws IOException {
        try {
            String file = "configs/" + caseConfiguration.getFrom();
            InputStream inputStream = CaseConfiguration.class.getClassLoader().getResourceAsStream(file);
            return FileUtil.readFully(inputStream);
        } catch (Exception e) {
            logger.error("load parent config failed: " + caseConfiguration.getFrom(), e);
            throw new IOException("load parent config failed: " + caseConfiguration.getFrom(), e);
        }
    }

    private CaseConfiguration parseConfiguration(String configYaml) {
        return new Yaml().loadAs(configYaml, CaseConfiguration.class);
    }

    private void fillupServices(CaseConfiguration caseConfiguration) throws IOException {
        List<String> caseSystemProps = caseConfiguration.getSystemProps();
        int index = 0;
        for (Map.Entry<String, ServiceComponent> entry : caseConfiguration.getServices().entrySet()) {
            String serviceName = entry.getKey();
            ServiceComponent service = entry.getValue();
            String type = service.getType();
            if (isAppOrTestService(type)) {
                service.setImage(SAMPLE_TEST_IMAGE + ":" + testImageVersion);
                service.setBasedir(toAbsolutePath(service.getBasedir()));
                if (service.getVolumes() == null) {
                    service.setVolumes(new ArrayList<>());
                }
                //mount ${project.basedir}/target : DUBBO_APP_DIR
                String targetPath = new File(service.getBasedir(), "target").getCanonicalPath();
                service.getVolumes().add(targetPath + ":" + DUBBO_APP_DIR);
                logger.info("Service: " + serviceName + ", mount " + targetPath + " to " + DUBBO_APP_DIR);

                //mount ${scenario_home}/logs : DUBBO_LOG_DIR
                service.getVolumes().add(scenarioLogDir + ":" + DUBBO_LOG_DIR);

                if (service.getEnvironment() == null) {
                    service.setEnvironment(new ArrayList<>());
                }
                // set service name
                setEnv(service, ENV_SERVICE_NAME, serviceName);

                //set wait ports
                if (isNotEmpty(service.getWaitPortsBeforeRun())) {
                    String str = convertAddrPortsToString(service.getWaitPortsBeforeRun());
                    setEnv(service, ENV_WAIT_PORTS_BEFORE_RUN, str);
                }

                //set run delay
                if (service.getRunDelay() > 0) {
                    setEnv(service, ENV_RUN_DELAY, service.getRunDelay() + "");
                }

                //set check timeout
                if (isDebug()) {
                    service.setWaitTimeout(debugTimeout);

                    if (isDebugService(serviceName)) {
                        //set java remote debug opts
                        //-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005
                        int debugPort = nextDebugPort();
                        String debugOpts;
                        if (isJdk9OrLater) {
                            debugOpts = String.format("-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=*:%s", debugPort);
                        } else {
                            debugOpts = String.format("-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=%s", debugPort);
                        }
                        appendEnv(service, ENV_DEBUG_OPTS, debugOpts);

                        //mapping debug port
                        if (service.getPorts() == null) {
                            service.setPorts(new ArrayList<>());
                        }
                        service.getPorts().add(debugPort + ":" + debugPort);
                    }
                }
                if (service.getWaitTimeout() > 0) {
                    setEnv(service, ENV_WAIT_TIMEOUT, service.getWaitTimeout() + "");
                }

                // set jacoco
                if (jacocoEnable) {
                    //mount ${project.basedir}/target : DUBBO_APP_DIR
                    String jacocoPath = new File(service.getBasedir(), "target-jacoco").getCanonicalPath();
                    service.getVolumes().add(jacocoPath + ":" + DUBBO_JACOCO_RESULT_DIR);
                    String jacocoRunnerPath = new File(outputDir() + File.separator + "jacocoagent.jar").getCanonicalPath();
                    service.getVolumes().add(jacocoRunnerPath + ":" + DUBBO_JACOCO_RUNNER_FILE);

                    //set jacoco agent
                    String jacoco = "-javaagent:" + DUBBO_JACOCO_RUNNER_FILE + "=destfile=/usr/local/dubbo/target-jacoco/" + index++ + "-" + System.currentTimeMillis() + "-jacoco.exec";
                    appendEnv(service, ENV_JAVA_OPTS, jacoco);
                }

                if ("app".equals(type)) {
                    String mainClass = service.getMainClass();
                    if (StringUtils.isBlank(mainClass)) {
                        throw new RuntimeException("Missing 'mainClass' for app service [" + serviceName + "]");
                    }
                    //set SERVICE_TYPE
                    setEnv(service, ENV_SERVICE_TYPE, type);
                    //set mainClass env
                    setEnv(service, ENV_APP_MAIN_CLASS, mainClass);
                    //set SCENARIO_HOME
                    setEnv(service, ENV_SCENARIO_HOME, scenarioHome);

                    boolean addHealthcheck = false;
                    //set check_log env
                    if (StringUtils.isNotBlank(service.getCheckLog())) {
                        addHealthcheck = true;
                        setEnv(service, ENV_CHECK_LOG, service.getCheckLog());
                    }

                    //set check ports
                    if (isNotEmpty(service.getCheckPorts())) {
                        addHealthcheck = true;
                        String str = convertAddrPortsToString(service.getCheckPorts());
                        setEnv(service, ENV_CHECK_PORTS, str);
                    }

                    //add healthcheck
                    if (addHealthcheck) {
                        addHealthCheck(service);
                    }
                } else if ("test".equals(type)) {
                    String mainClass = service.getMainClass();
                    if (StringUtils.isNotBlank(mainClass)) {
                        throw new RuntimeException("Illegal attribute 'mainClass' for test service [" + serviceName + "]");
                    }
                    //set SERVICE_TYPE
                    setEnv(service, ENV_SERVICE_TYPE, type);

                    //set TEST_PATTERNS
                    if (isNotEmpty(service.getTests())) {
                        String str = StringUtils.join(service.getTests(), ';');
                        setEnv(service, ENV_TEST_PATTERNS, str);
                    }
                } else {
                    throw new RuntimeException("Illegal service type: " + type);
                }
            } else if (isNativeAppOrTestService(type)) {
                service.setImage(NATIVE_SAMPLE_TEST_IMAGE + ":" + testImageVersion);
                if (service.getVolumes() == null) {
                    service.setVolumes(new ArrayList<>());
                }
                //mount scenarioHome : DUBBO_SRC_DIR, cause pom.xml depends on parent pom,we have to mount root dir
                service.getVolumes().add(scenarioHome + ":" + DUBBO_SRC_DIR);
                //mount ${scenario_home}/logs : DUBBO_LOG_DIR
                service.getVolumes().add(scenarioLogDir + ":" + DUBBO_LOG_DIR);

                if (service.getEnvironment() == null) {
                    service.setEnvironment(new ArrayList<>());
                }
                // set service name
                setEnv(service, ENV_SERVICE_NAME, serviceName);
                // we need to know which dir to run mvn
                setEnv(service, ENV_SERVICE_DIR, service.getBasedir() == null ? "." : service.getBasedir());

                //set wait ports
                if (isNotEmpty(service.getWaitPortsBeforeRun())) {
                    String str = convertAddrPortsToString(service.getWaitPortsBeforeRun());
                    setEnv(service, ENV_WAIT_PORTS_BEFORE_RUN, str);
                }

                //set run delay
                if (service.getRunDelay() > 0) {
                    setEnv(service, ENV_RUN_DELAY, service.getRunDelay() + "");
                }

                if (service.getWaitTimeout() > 0) {
                    setEnv(service, ENV_WAIT_TIMEOUT, service.getWaitTimeout() + "");
                }

                //set SERVICE_TYPE
                setEnv(service, ENV_SERVICE_TYPE, type);
                //set SCENARIO_HOME
                setEnv(service, ENV_SCENARIO_HOME, scenarioHome);
                if ("nativeApp".equals(type)) {
                    boolean addHealthcheck = false;
                    //set check_log env
                    if (StringUtils.isNotBlank(service.getCheckLog())) {
                        addHealthcheck = true;
                        setEnv(service, ENV_CHECK_LOG, service.getCheckLog());
                    }

                    //set check ports
                    if (isNotEmpty(service.getCheckPorts())) {
                        addHealthcheck = true;
                        String str = convertAddrPortsToString(service.getCheckPorts());
                        setEnv(service, ENV_CHECK_PORTS, str);
                    }

                    //add healthcheck
                    if (addHealthcheck) {
                        addHealthCheck(service);
                    }
                }
            }

            // set hostname to serviceId if absent
            if (StringUtils.isBlank(service.getHostname())) {
                service.setHostname(serviceName);
            }

            //set jvmFlags
            if (isNotEmpty(service.getJvmFlags())) {
                String str = StringUtils.join(service.getJvmFlags(), ' ');
                appendEnv(service, ENV_JAVA_OPTS, str);
            }

            //set systemProps
            List<String> systemProps = mergeSystemProps(caseSystemProps, service.getSystemProps());
            if (isNotEmpty(systemProps)) {
                String str = convertSystemPropsToJvmFlags(systemProps);
                appendEnv(service, ENV_JAVA_OPTS, str);
            }

            if (service.getHealthcheck() != null) {
                healthcheckServices.add(serviceName);
            }
        }
    }

    private static void addHealthCheck(ServiceComponent service) {
        if (service.getHealthcheck() == null) {
            Map<String, Object> healthcheckMap = new LinkedHashMap<>();
            service.setHealthcheck(healthcheckMap);
        }
        Map<String, Object> healthcheck = service.getHealthcheck();
        healthcheck.putIfAbsent("test", Arrays.asList("CMD", "/usr/local/dubbo/healthcheck.sh"));
        healthcheck.putIfAbsent("interval", "5s");
        healthcheck.putIfAbsent("timeout", "5s");
        healthcheck.putIfAbsent("retries", 20);
        healthcheck.putIfAbsent("start_period", isNativeAppOrTestService(service.getType()) ? "300s" : "60s");
    }

    private void appendEnv(ServiceComponent service, String name, String value) {
        String prefix = name + "=";
        List<String> environments = service.getEnvironment();
        if (environments == null) {
            environments = new ArrayList<>();
            service.setEnvironment(environments);
        }
        for (int i = 0; i < environments.size(); i++) {
            String env = environments.get(i);
            if (env.startsWith(prefix)) {
                // append to exist env
                env += " " + value;
                environments.set(i, env);
                return;
            }
        }
        environments.add(name + "=" + value);
    }

    private void setEnv(ServiceComponent service, String name, String value) {
        List<String> environments = service.getEnvironment();
        addOrReplaceKVEntry(environments, name, value);
    }

    private void addOrReplaceKVEntry(List<String> map, String name, String value) {
        String prefix = name + "=";
        for (int i = 0; i < map.size(); i++) {
            String env = map.get(i);
            if (env.startsWith(prefix)) {
                //replace old env
                env = name + "=" + value;
                map.set(i, env);
                return;
            }
        }
        map.add(name + "=" + value);
    }

    //convert systemProp key=value to -Dkey=value
    private String convertSystemPropsToJvmFlags(List<String> systemProps) {
        StringBuilder sb = new StringBuilder();
        for (String propkv : systemProps) {
            sb.append("-D").append(propkv).append(' ');
        }
        String runtimeProps = System.getProperty("prop");
        if (StringUtils.isNotBlank(runtimeProps)) {
            sb.append(runtimeProps).append(' ');
        }
        return sb.toString();
    }

    private boolean isNotEmpty(List<String> list) {
        return list != null && list.size() > 0;
    }

    private String convertAddrPortsToString(List<String> addrPorts) {
        StringBuilder sb = new StringBuilder();
        for (String addrPort : addrPorts) {
            if (!addrPort.contains(":")) {
                addrPort = "127.0.0.1" + ":" + addrPort;
            }
            sb.append(addrPort).append(";");
        }
        return sb.toString();
    }

    private String toAbsolutePath(String path) throws IOException {
        if (path == null) {
            // if no dir set,use current dir
            path = ".";
        }
        File file = new File(path);
        if (file.isAbsolute()) {
            return file.getCanonicalPath();
        }
        //relative path to basedir of configuration file
        return new File(configBasedir, path).getCanonicalPath();
    }

    private static boolean isAppOrTestService(String type) {
        if (type == null) {
            return false;
        }
        switch (type) {
            case "app":
            case "test":
                return true;
        }
        return false;
    }

    private static boolean isNativeAppOrTestService(String type) {
        if (type == null) {
            return false;
        }
        switch (type) {
            case "nativeApp":
            case "nativeTest":
                return true;
        }
        return false;
    }

    private Pattern pattern = Pattern.compile("\\$\\{.*}");

    private String replaceHolders(String str, Map<String, String> props) {
        while (str.contains("${")) {
            String prefix = str.substring(0, str.indexOf("${"));
            String suffix = str.substring(str.indexOf("}") + 1);
            String placeholder = str.substring(str.indexOf("${") + 2, str.indexOf("}"));
            String propertyName;
            String defaultValue = "";
            if (placeholder.contains(":")) {
                propertyName = placeholder.substring(0, placeholder.indexOf(":"));
                defaultValue = placeholder.substring(placeholder.indexOf(":") + 1);
            } else {
                propertyName = placeholder;
            }
            str = prefix + props.getOrDefault(propertyName, defaultValue) + suffix;
        }

        return str;
    }

    @Override
    public ScenarioRunningScriptGenerator scenarioGenerator() {
        return new DockerComposeRunningGenerator();
    }

    @Override
    public CaseConfiguration caseConfiguration() {
        return this.configuration;
    }

    @Override
    public String scenarioName() {
        return scenarioName;
    }

    @Override
    public String scenarioVersion() {
        return System.getProperty("scenario.version");
    }

    @Override
    public String testImageVersion() {
        return testImageVersion;
    }

    @Override
    public String ipv6Cidr() {
        if (caseConfiguration().getProps() != null) {
            return caseConfiguration().getProps().get("ipv6Cidr");
        } else {
            return null;
        }
    }

    @Override
    public String dockerNetworkName() {
        return (scenarioName() + "-" + testImageVersion()).toLowerCase();
    }

    @Override
    public String dockerContainerName() {
        return (scenarioName() + "-" + scenarioVersion() + "-" + testImageVersion()).toLowerCase();
    }

    @Override
    public String scenarioHome() {
        return this.scenarioHome;
    }

    @Override
    public String outputDir() {
        return scenarioHome;
    }

    @Override
    public String jacocoHome() {
        return System.getProperty("jacoco.home");
    }

    @Override
    public boolean enableJacoco() {
        return jacocoEnable;
    }

    @Override
    public String debugMode() {
        return isDebug() ? "1" : "0";
    }

    private int nextDebugPort() {
        return javaDebugPort++;
    }

    @Override
    public Map<String, Object> toMap() {
        CaseConfiguration caseConfiguration = caseConfiguration();
        final Map<String, Object> root = new HashMap<>();

        root.put("scenario_home", scenarioHome());
        root.put("scenario_name", scenarioName());
        root.put("scenario_version", scenarioVersion());
        root.put("docker_container_name", dockerContainerName());
        root.put("jacoco_home", jacocoHome());
        root.put("debug_mode", debugMode());
        root.put("docker_compose_file", outputDir() + File.separator + "docker-compose.yml");
        root.put("network_name", dockerNetworkName());
        root.put("ipv6_cidr", ipv6Cidr());
        root.put("timeout", scenarioTimeout);

        final StringBuilder removeImagesScript = new StringBuilder();
        List<String> links = new ArrayList<>();
        if (caseConfiguration.getServices() != null) {
            caseConfiguration.getServices().forEach((name, service) -> {
                links.add(service.getHostname());
                if (service.isRemoveOnExit()) {
                    removeImagesScript.append("docker rmi ")
                            .append(service.getImage())
                            .append(System.lineSeparator());
                }
            });
        }
        root.put("removeImagesScript", removeImagesScript.toString());

//        add links to test service
//        caseConfiguration.getServices().forEach((name, service) -> {
//            if ("test".equals(service.getType())) {
//                if (service.getLinks() == null) {
//                    service.setLinks(new ArrayList<>());
//                }
//                for (String link : links) {
//                    if (!StringUtils.equals(link, service.getHostname())) {
//                        service.getLinks().add(link);
//                    }
//                }
//            }
//        });

        root.put("services", convertDockerServices(scenarioVersion(), caseConfiguration.getServices()));
        List<String> testServiceNames = findTestServiceNames(caseConfiguration);
        root.put("test_service_name", testServiceNames.size() > 0 ? testServiceNames.get(0) : "");

        return root;
    }

    private List<String> findTestServiceNames(CaseConfiguration caseConfiguration) {
        List<String> serviceNames = new ArrayList<>();
        for (Map.Entry<String, ServiceComponent> entry : caseConfiguration.getServices().entrySet()) {
            ServiceComponent service = entry.getValue();
            if ("test".equals(service.getType()) || "nativeTest".equals(service.getType())) {
                serviceNames.add(entry.getKey());
            }
        }
        return serviceNames;
    }

    protected List<DockerService> convertDockerServices(final String version,
                                                        Map<String, ServiceComponent> componentMap) {
        final ArrayList<DockerService> services = new ArrayList<>();
        if (componentMap == null) {
            return services;
        }
        componentMap.forEach((name, dependency) -> {
            DockerService service = new DockerService();

            String imageName = dependency.getImage();
            service.setName(name);
            service.setImageName(imageName);
            service.setBuild(dependency.getBuild());
            service.setHostname(dependency.getHostname());
            service.setExpose(dependency.getExpose());
            service.setPorts(dependency.getPorts());
            service.setEntrypoint(dependency.getEntrypoint());
            service.setLinks(dependency.getDepends_on());

            //convert depends_on to map
            if (dependency.getDepends_on() != null) {
                Map<String, String> dependsOnMap = new LinkedHashMap<>();
                service.setDepends_on(dependsOnMap);
                for (String serviceName : dependency.getDepends_on()) {
                    if (healthcheckServices.contains(serviceName)) {
                        dependsOnMap.put(serviceName, "{condition: service_healthy}");
                    } else {
                        dependsOnMap.put(serviceName, "{condition: service_started}");
                    }
                }
            }

            //convert healthcheck to string map
            if (dependency.getHealthcheck() != null) {
                Yaml yaml = new Yaml();
                Map<String, Object> healthcheckMap = dependency.getHealthcheck();
                Map<String, String> newMap = new LinkedHashMap<>();
                for (Map.Entry<String, Object> entry : healthcheckMap.entrySet()) {
                    String value = yaml.dump(entry.getValue());
                    newMap.put(entry.getKey(), value.trim());
                }
                service.setHealthcheck(newMap);
            }
            service.setEnvironment(dependency.getEnvironment());
            service.setVolumes(dependency.getVolumes());
            service.setVolumes_from(dependency.getVolumes_from());
            service.setRemoveOnExit(dependency.isRemoveOnExit());
            services.add(service);
        });
        return services;
    }
}
