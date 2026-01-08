package com.wallet_system.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
// import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "Wallet System API", version = "1.0", description = "API for wallet operations"),
        servers = {@Server(url = "http://localhost:8080", description = "Local server")}
)
@Configuration
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi walletApi() {
        return GroupedOpenApi.builder()
                .group("wallet")
                .pathsToMatch("/api/v1/wallets/**")
                .packagesToScan("com.wallet_system.controller")
                .build();
    }
}
