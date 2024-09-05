package br.com.Gateway.config;

import org.springframework.stereotype.Component;


public record Routes(
        String id,
        String url,
        String port
) {



}
