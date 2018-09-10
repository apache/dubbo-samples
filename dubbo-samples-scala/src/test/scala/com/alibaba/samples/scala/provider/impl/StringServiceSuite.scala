package com.alibaba.samples.scala.provider.impl

import com.alibaba.dubbo.samples.scala.EmbeddedZooKeeper
import com.alibaba.dubbo.samples.scala.provider.ProviderConfiguration
import com.alibaba.dubbo.samples.scala.provider.impl.StringServiceImpl
import org.junit.runner.RunWith
import org.scalatest.prop.PropertyChecks
import org.scalatest.{BeforeAndAfterAll, FunSuite, Matchers}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.{ContextConfiguration, TestContextManager}

@RunWith(classOf[SpringRunner])
@ContextConfiguration(classes = Array(classOf[ProviderConfiguration]))
class StringServiceSuite extends FunSuite with Matchers with BeforeAndAfterAll with PropertyChecks {

  override protected def beforeAll(): Unit = {
    super.beforeAll()
    new EmbeddedZooKeeper(2181, false).start()
    new TestContextManager(this.getClass).prepareTestInstance(this)
  }

  @Autowired
  private val stringService: StringServiceImpl = null

  test("StringService.reverse") {
    forAll { s: String ⇒
      stringService.reverse(s).reverse shouldEqual s
    }
  }
}
