package fr.formation.spring.bibliotech.security;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ApiKeyAuthFilter implements Filter {

    // Injection des valeurs depuis application.properties
    @Value("${bibliotech.security.api-key.header-name}")
    private String apiKeyHeaderName;

    @Value("${bibliotech.security.api-key.value}")
    private String validApiKey;

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 1. Extraire la clé de l'en-tête de la requête
        String apiKey = httpRequest.getHeader(apiKeyHeaderName);

        // 2. Vérifier la clé
        if (validApiKey.equals(apiKey)) {
            // Clé valide, on laisse la requête continuer son chemin
            chain.doFilter(request, response);
        } else {
            // Clé invalide ou manquante, on bloque la requête
            httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            httpResponse.getWriter().write("API Key invalide ou manquante.");
        }
    }
}


