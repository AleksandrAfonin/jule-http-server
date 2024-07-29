package ru.otus.jule.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
  private static final Logger logger = LogManager.getLogger(HttpRequest.class.getName());
  private String rawRequest;
  private String uri;
  private HttpMethod method;
  private Map<String, String> parameters;
  private Map<String, String> headers;
  private String body;

  public String getRoutingKey() {
    return method + " " + uri;
  }

  public String getUri() {
    return uri;
  }

  public String getBody() {
    return body;
  }

  public HttpRequest(String rawRequest) {
    this.rawRequest = rawRequest;
    this.parse();
    logger.info("\nuri: " + uri + "\nmethod: " + method + "\nbody:\n" + body);
    logger.debug("RawRequest:\n" + rawRequest);
  }

  private void parse() {
    int startIndex = rawRequest.indexOf(' ');
    int endIndex = rawRequest.indexOf(' ', startIndex + 1);
    this.uri = rawRequest.substring(startIndex + 1, endIndex);
    this.method = HttpMethod.valueOf(rawRequest.substring(0, startIndex));
    this.parameters = new HashMap<>();
    if (uri.contains("?")) {
      String[] elements = uri.split("[?]");
      this.uri = elements[0];
      String[] keysValues = elements[1].split("&");
      for (String o : keysValues) {
        String[] keyValue = o.split("=");
        this.parameters.put(keyValue[0], keyValue[1]);
      }
    }
    // Парсинг заголовков
    this.headers = new HashMap<>();
    startIndex = rawRequest.indexOf("\r\n") + 2;
    endIndex = rawRequest.indexOf("\r\n\r\n");
    String rawHeaders = rawRequest.substring(startIndex, endIndex);
    String[] linesHeaders = rawHeaders.split("\r\n");
    for (String o : linesHeaders) {
      String[] keyValue = o.split(": ");
      this.headers.put(keyValue[0], keyValue[1]);
    }

    if (method == HttpMethod.POST) {
      this.body = rawRequest.substring(
              rawRequest.indexOf("\r\n\r\n") + 4
      );
    }
  }

  public boolean containsParameter(String key) {
    return parameters.containsKey(key);
  }

  public String getParameter(String key) {
    return parameters.get(key);
  }

  public String getHeader(String key) {
    return headers.get(key);
  }

}
