package org.apache.dubbo.samples.scala

import org.apache.dubbo.samples.scala.consumer.{ConsumerConfiguration, StringServiceConsumer}
import org.hamcrest.CoreMatchers.is
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner


@RunWith(classOf[SpringJUnit4ClassRunner])
@ContextConfiguration(classes = Array(classOf[ConsumerConfiguration]))
class ScalaServicesIT {

  @Autowired
  private val stringServiceConsumer: StringServiceConsumer  = null

  @Test def testScala(): Unit = {
    val response = stringServiceConsumer.reverse("hello world")
    println("result: " + response)
    assertThat(response, is("dlrow olleh"))
  }

}
