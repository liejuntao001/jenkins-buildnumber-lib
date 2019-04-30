package com.github.liejuntao001.jenkins

import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import groovy.json.JsonSlurper
import static groovy.test.GroovyAssert.*


class SimpleHttpsTest {
  def http
  def json

  @Before
  void setUp() {
    http = new SimpleHttps()
    json = new JsonSlurper()
    http.setTestMode(true)
  }

  @Test
  void testGet() {
    def expect = json.parseText('{"hello": "world"}')
    def (result, error) = http.get("https://www.mocky.io/v2/5cb762f8320000a354cd4aae")
    if (error != null) {
      println(error)
    }
    assertEquals "result:", expect, result
  }

  @Test
  void testBadHost() {
    def (_, error) = http.get("https://badhost.com")
    if (error != null) {
      println(error)
    }
    assertNotNull(error)
  }

  @Test
  void testBadLink() {
    def (result, error) = http.get("https://www.mocky.io/v2/5cb762f8320000a354cd4abd")
    if (error != null) {
      println(error)
    }
    assertEquals "result:",-1, result
  }

  @Test
  void testPost() {
    def expect = json.parseText('{"hello": "world"}')
    def (result, error) = http.post("https://www.mocky.io/v2/5cb762f8320000a354cd4aae", null)
    if (error != null) {
      println(error)
    }

    assertEquals "result:", expect, result
  }

  @Test
  //@Ignore ("provide correct uri")
  void testPost1() {
    def uuid = UUID.randomUUID().toString()
    def expect = json.parseText('{"bn": 1}')
    def (result, error) = http.post("https://buildnumber1.herokuapp.com/" + uuid, null)
    if (error != null) {
      println(error)
    }

    assertEquals "result:", expect, result
  }
}