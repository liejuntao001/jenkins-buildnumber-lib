package com.github.liejuntao001.jenkins

import groovy.json.JsonSlurper
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import java.security.cert.CertificateException
import java.security.cert.X509Certificate

class SimpleHttps {
  private boolean testMode = false
  TrustManager[] trustAllCerts = [new X509TrustManager() {
    void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

    void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

    X509Certificate[] getAcceptedIssuers() {return new X509Certificate[0]}
  }]

  def setTestMode(boolean test) {
    this.testMode = test
  }

  def get(String uri) {
    httpInternal(uri, null, false)
  }

  def post(String uri, String body) {
    httpInternal(uri, body, true)
  }

  def httpInternal(String uri, String body, boolean isPost) {
    def response = [:]
    def error
    try {

      if (testMode) {
        SSLContext sc = SSLContext.getInstance("TLS")
        sc.init(null, trustAllCerts, new java.security.SecureRandom())
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory())
      }

      def http = new URL(uri).openConnection() as HttpsURLConnection
      if (isPost) {
        http.setRequestMethod('POST')
        http.setDoOutput(true)
        if (body) {
          http.outputStream.write(body.getBytes("UTF-8"))
        }
      }

      http.setRequestProperty('User-Agent', 'groovy-2.4.6')
      http.setRequestProperty("Accept", 'application/json')
      http.setRequestProperty("Content-Type", 'application/json')
      http.connect()

      if (http.responseCode == 200) {
        response = new JsonSlurper().parseText(http.inputStream.getText('UTF-8'))
      } else {
        // in case of codes other than 200
        response = -1
      }
    } catch (Exception e) {
      // handle exception, e.g. Host unreachable, timeout etc.
      //println(e)
      error = e
    }
    return [response, error]
  }
}
