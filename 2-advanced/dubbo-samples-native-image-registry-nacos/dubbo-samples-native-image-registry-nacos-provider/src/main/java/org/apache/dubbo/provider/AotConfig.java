package org.apache.dubbo.provider;

import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.apache.dubbo.provider.util.AotUtil.getClassBySupport;

/**
 * @author 杨泽翰
 */
@Configuration
@ImportRuntimeHints(AotConfig.HintsImpl.class)
public class AotConfig {
    public static class HintsImpl implements RuntimeHintsRegistrar {
        @Override
        public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
            List<String> basePackagelist = new ArrayList<>();
            basePackagelist.add("org.apache.dubbo");
            List<Set<Class<?>>> classBySupport = getClassBySupport(basePackagelist, Serializable.class);
            classBySupport.forEach(set -> set.forEach(item -> {
                hints.reflection().registerType(item, MemberCategory.values());
                Class<?>[] declaredClasses = item.getDeclaredClasses();
                if (declaredClasses.length != 0) {
                    Arrays.stream(declaredClasses).forEach(kid -> {
                        hints.reflection().registerType(kid, MemberCategory.values());
                    });
                }
            }));
        }

    }

}
