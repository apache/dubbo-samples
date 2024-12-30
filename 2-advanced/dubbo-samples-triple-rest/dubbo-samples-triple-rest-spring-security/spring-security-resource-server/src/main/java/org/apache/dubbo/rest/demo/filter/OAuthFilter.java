/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.dubbo.rest.demo.filter;

import jakarta.servlet.Filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.protocol.tri.rest.filter.RestExtension;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.io.IOException;

@Activate
public class OAuthFilter implements Filter, RestExtension {

    private static final String HOST = System.getProperty("authorization.address", "localhost");

    String issuer = "http://" + HOST + ":9000";

    private JwtDecoder jwtDecoder;
    private JwtAuthenticationConverter jwtAuthenticationConverter;

    @Override
    public void init(FilterConfig filterConfig) {
        // Initialize the JwtDecoder and obtain the public key from the configured authorization server URL for decoding the JWT
        jwtDecoder = NimbusJwtDecoder.withIssuerLocation(issuer).build();
        // Initialize JwtAuthenticationConverter to convert JWT
        jwtAuthenticationConverter = new JwtAuthenticationConverter();
        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
    }

    @Override
    public String[] getPatterns() {
        return new String[] {"/**"}; // Intercept all requests
    }

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain) throws IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            String jwtToken = authorization.substring("Bearer ".length());
            // Decode the JWT token
            try {
                Jwt jwt = jwtDecoder.decode(jwtToken);
                jwtAuthenticationConverter.convert(jwt);
                filterChain.doFilter(request, response);
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
            }

        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing JWT token");
        }

    }

    @Override
    public int getPriority() {
        return -200;
    }
}
