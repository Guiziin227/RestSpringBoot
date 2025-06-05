package com.github.guiziin227.restspringboot.config;

public interface TestConfigs {
    int SERVER_PORT = 8888;

    String HEADER_PARAM_AUTHORIZATION = "Authorization";
    String HEADER_PARAM_ORIGIN = "Origin";

    String ORIGIN_LOCALHOST = "http://localhost:4200";
    String ORIGIN_ERRADA = "http://localhost:4201";
}
