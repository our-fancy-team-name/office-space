package com.ourfancyteamname.officespace.support;

import java.net.URI;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.support.HttpRequestWrapper;

import com.ourfancyteamname.officespace.test.enums.RestClientConst;

import lombok.SneakyThrows;

public class HttpRequestModifier extends HttpRequestWrapper {

  private final int port;

  public HttpRequestModifier(HttpRequest request, int port) {
    super(request);
    this.port = port;
  }

  @Override
  @SneakyThrows
  public URI getURI() {
    return new URI(RestClientConst.urlApi(port, super.getURI().toString()));
  }
}
