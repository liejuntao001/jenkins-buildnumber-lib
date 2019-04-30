#!/usr/bin/env groovy

import com.github.liejuntao001.jenkins.SimpleHttps

//assume server uri ends with /
def get(String server, String uuid) {
  def https = new SimpleHttps()
  def (res, error) = https.post(server + uuid, null)
  if (error == null && res != -1) {
    return res.bn
  } else {
    println(error)
    println(res)
    return 0
  }
}
