package com.sed.backend.infrastructure.adapters.email;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class EmailTemplateService {

    public String renderTemplate(String path, Map<String, Object> variables) {
        try {
            String template = StreamUtils.copyToString(new ClassPathResource(path).getInputStream(),
                    StandardCharsets.UTF_8);
            if (variables == null || variables.isEmpty()) {
                return template;
            }
            String rendered = template;
            for (Map.Entry<String, Object> entry : variables.entrySet()) {
                rendered = rendered.replace("{{" + entry.getKey() + "}}", String.valueOf(entry.getValue()));
            }
            return rendered;
        } catch (IOException ex) {
            throw new IllegalStateException("No se pudo cargar la plantilla de correo: " + path, ex);
        }
    }
}