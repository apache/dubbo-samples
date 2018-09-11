package com.alibaba.dubbo.samples.scala.provider

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo
import org.springframework.context.annotation.{Configuration, PropertySource}

@Configuration
@EnableDubbo(scanBasePackages = Array("com.alibaba.dubbo.samples.scala.provider"))
@PropertySource(Array("classpath:/dubbo-provider.properties"))
class ProviderConfiguration {

}