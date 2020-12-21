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

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.scenario.builder.exception.ConfigureFileNotFoundException;
import org.apache.dubbo.scenario.builder.vo.CaseConfiguration;
import org.apache.dubbo.scenario.builder.vo.DockerService;
import org.apache.dubbo.scenario.builder.vo.ServiceComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfigurationImpl implements IConfiguration {
    public static final String DUBBO_APP_PATH = "/usr/local/dubbo/app";
    public static final String ENV_SERVICE_TYPE = "SERVICE_TYPE";
    public static final String ENV_APP_MAIN_CLASS = "APP_MAIN_CLASS";
    public static final String ENV_CHECK_LOG = "CHECK_LOG";
    public static final String ENV_WAIT_PORTS_BEFORE_RUN = "WAIT_PORTS_BEFORE_RUN";
    public static final String ENV_CHECK_PORTS_AFTER_RUN = "CHECK_PORTS_AFTER_RUN";
    public static final String ENV_TEST_PATTERNS = "TEST_PATTERNS";
    public static final String ENV_JAVA_OPTS = "JAVA_OPTS";
    public static final String ENV_SCENARIO_HOME = "SCENARIO_HOME";

    private static final Logger logger = LoggerFactory.getLogger(ConfigurationImpl.class);
    private final CaseConfiguration configuration;
    private Map<String, String> props;
    private String scenarioHome;
    private String configBasedir;
    private String scenarioName;

    public ConfigurationImpl() throws IOException, ConfigureFileNotFoundException {
        String configureFile = System.getProperty("configure.file");
        if (StringUtils.isBlank(configureFile)) {
            throw new ConfigureFileNotFoundException();
        }
        this.configBasedir = new File(configureFile).getParentFile().getCanonicalPath();

        //set default scenarioHome dir to ${configBasedir}/target
        this.scenarioHome = System.getProperty("scenario.home");
        if (StringUtils.isBlank(scenarioHome)) {
            scenarioHome = configBasedir + "/target";
        }

        //set default scenarioName
        scenarioName = System.getProperty("scenario.name");
        if (StringUtils.isBlank(scenarioName)) {
            scenarioName = new File(configBasedir).getName();
        }

        this.configuration = loadCaseConfiguration(configureFile);
    }

    private CaseConfiguration loadCaseConfiguration(String configureFile) throws IOException {
        // read 'props'
        String configYaml = readFully(configureFile);
        CaseConfiguration tmpConfiguration = parseConfiguration(configYaml);
        this.props = tmpConfiguration.getProps();

        // process 'from', load parent config
        CaseConfiguration parentConfiguration = null;
        if (StringUtils.isNotBlank(tmpConfiguration.getFrom())) {
            String parentConfigYaml = loadParentConfigYaml(tmpConfiguration);
            CaseConfiguration tmpParentConfiguration = parseConfiguration(parentConfigYaml);

            //merge props
            Map<String, String> newProps = new HashMap<>(tmpParentConfiguration.getProps());
            newProps.putAll(props);
            props = newProps;

            // replace variables '${...}'
            String newParentConfigYaml = replaceHolders(parentConfigYaml, props);
            parentConfiguration = parseConfiguration(newParentConfigYaml);
        }

        // replace variables '${...}'
        String newConfigYaml = replaceHolders(configYaml, props);
        CaseConfiguration caseConfiguration = parseConfiguration(newConfigYaml);

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

    private String loadParentConfigYaml(CaseConfiguration caseConfiguration) throws IOException {
        try {
            String file = "configs/" + caseConfiguration.getFrom();
            InputStream inputStream = CaseConfiguration.class.getClassLoader().getResourceAsStream(file);
            return readFully(inputStream);
        } catch (Exception e) {
            logger.error("load parent config failed: " + caseConfiguration.getFrom(), e);
            throw new IOException("load parent config failed: " + caseConfiguration.getFrom(), e);
        }
    }

    private CaseConfiguration parseConfiguration(String configYaml) {
        return new Yaml().loadAs(configYaml, CaseConfiguration.class);
    }

    private void fillupServices(CaseConfiguration caseConfiguration) throws IOException {
        for (Map.Entry<String, ServiceComponent> entry : caseConfiguration.getServices().entrySet()) {
            String serviceId = entry.getKey();
            ServiceComponent service = entry.getValue();
            String type = service.getType();
            if (isAppService(type)) {
                service.setImage("dubbo-sample-test");
                service.setBasedir(toAbsolutePath(service.getBasedir()));
                if (service.getVolumes() == null) {
                    service.setVolumes(new ArrayList<>());
                }
                //mount ${basedir}/target : DUBBO_APP_PATH
                String targetPath = new File(service.getBasedir(), "target").getCanonicalPath();
                service.getVolumes().add(targetPath + ":" + DUBBO_APP_PATH);

                if (service.getEnvironment() == null) {
                    service.setEnvironment(new ArrayList<>());
                }
                //set wait ports
                if (isNotEmpty(service.getWaitPortsBeforeRun())) {
                    String str = convertAddrPortsToString(service.getWaitPortsBeforeRun());
                    setEnv(service, ENV_WAIT_PORTS_BEFORE_RUN, str);
                }

                if ("app".equals(type)) {
                    String mainClass = service.getMainClass();
                    if (StringUtils.isBlank(mainClass)) {
                        throw new RuntimeException("Missing 'mainClass' for app service [" + serviceId + "]");
                    }
                    //set SERVICE_TYPE
                    setEnv(service, ENV_SERVICE_TYPE, type);
                    //set mainClass env
                    setEnv(service, ENV_APP_MAIN_CLASS, mainClass);
                    //set SCENARIO_HOME
                    setEnv(service, ENV_SCENARIO_HOME, scenarioHome);

                    //set check_log env
                    if (StringUtils.isNotBlank(service.getCheckLog())) {
                        setEnv(service, ENV_CHECK_LOG, service.getCheckLog());
                    }

                    //set check ports
                    if (isNotEmpty(service.getCheckPortsAfterRun())) {
                        String str = convertAddrPortsToString(service.getCheckPortsAfterRun());
                        setEnv(service, ENV_CHECK_PORTS_AFTER_RUN, str);
                    }
                } else if ("test".equals(type)) {
                    String mainClass = service.getMainClass();
                    if (StringUtils.isNotBlank(mainClass)) {
                        throw new RuntimeException("Illegal attribute 'mainClass' for test service [" + serviceId + "]");
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
            }

            // set hostname to serviceId if absent
            if (StringUtils.isBlank(service.getHostname())) {
                service.setHostname(serviceId);
            }

            //set jvmFlags
            if (isNotEmpty(service.getJvmFlags())) {
                String str = StringUtils.join(service.getJvmFlags(), ' ');
                appendEnv(service, ENV_JAVA_OPTS, str);
            }

            //set systemProps
            if (isNotEmpty(service.getSystemProps())) {
                String str = convertSystemPropsToJvmFlags(service.getSystemProps());
                appendEnv(service, ENV_JAVA_OPTS, str);
            }

        }
    }

    private void appendEnv(ServiceComponent service, String name, String value) {
        String prefix = name + "=";
        List<String> environments = service.getEnvironment();
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
        String prefix = name + "=";
        List<String> environments = service.getEnvironment();
        for (int i = 0; i < environments.size(); i++) {
            String env = environments.get(i);
            if (env.startsWith(prefix)) {
                //replace old env
                env = name + "=" + value;
                environments.set(i, env);
                return;
            }
        }
        environments.add(name + "=" + value);
    }

    //convert systemProp key=value to -Dkey=value
    private String convertSystemPropsToJvmFlags(List<String> systemProps) {
        StringBuilder sb = new StringBuilder();
        for (String propkv : systemProps) {
            sb.append("-D").append(propkv).append(' ');
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
        File file = new File(path);
        if (file.isAbsolute()) {
            return file.getCanonicalPath();
        }
        //relative path to basedir of configuration file
        return new File(configBasedir, path).getCanonicalPath();
    }

    private boolean isAppService(String type) {
        if (type == null) {
            return false;
        }
        switch (type) {
            case "app":
            case "test":
                return true;
        }
        throw new RuntimeException("Illegal service type: " + type);
    }

    private String readFully(String file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            return readFully(fis);
        }
    }

    private String readFully(InputStream input) throws IOException {
        DataInputStream dis = new DataInputStream(input);
        byte[] bytes = new byte[input.available()];
        dis.readFully(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    private Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}");

    private String replaceHolders(String str, Map<String, String> props) {
        StringBuffer buf = new StringBuffer(str.length());
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            String var = matcher.group(1);
            String value = props.get(var);
            matcher.appendReplacement(buf, value != null ? value : matcher.group());
        }
        matcher.appendTail(buf);
        return buf.toString();
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
    public String dockerImageVersion() {
        return System.getProperty("docker.image.version", "latest");
    }

    @Override
    public String dockerNetworkName() {
        return (scenarioName() + "-" + dockerImageVersion()).toLowerCase();
    }

    @Override
    public String dockerContainerName() {
        return (scenarioName() + "-" + scenarioVersion() + "-" + dockerImageVersion()).toLowerCase();
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
    public String debugMode() {
        return System.getProperty("debug.mode", "0");
    }

    public String getTimeoutInSeconds() {
        String timeout = System.getProperty("timeout");
        if (StringUtils.isBlank(timeout)) {
            Map<String, String> props = this.caseConfiguration().getProps();
            if (props != null) {
                timeout = props.get("timeout");
            }
        }
        return StringUtils.isNotBlank(timeout) ? timeout : "90";
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
        root.put("timeout", getTimeoutInSeconds());

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
            if ("test".equals(service.getType())) {
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
            service.setHostname(dependency.getHostname());
            service.setExpose(dependency.getExpose());
            service.setDepends_on(dependency.getDepends_on());
            service.setLinks(dependency.getDepends_on());
            service.setEntrypoint(dependency.getEntrypoint());
            service.setHealthcheck(dependency.getHealthcheck());
            service.setEnvironment(dependency.getEnvironment());
            service.setVolumes(dependency.getVolumes());
            service.setRemoveOnExit(dependency.isRemoveOnExit());
            services.add(service);
        });
        return services;
    }
}
