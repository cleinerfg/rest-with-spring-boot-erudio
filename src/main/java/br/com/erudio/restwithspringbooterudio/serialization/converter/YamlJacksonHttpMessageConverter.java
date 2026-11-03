package br.com.erudio.restwithspringbooterudio.serialization.converter;

//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;

// Deprecated
//public final class YamlJackson2HttpMessageConverter extends AbstractJackson2HttpMessageConverter {
//
//    protected YamlJackson2HttpMessageConverter() {
//        super(new YAMLMapper()
//                        .setSerializationInclusion(JsonInclude.Include.NON_NULL),
//                MediaType.parseMediaType("application/yaml"));
//    }
//}

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * <p><strong>ATTENTION:</strong> In Spring Boot 4 (Spring Framework 7) this custom
 * YAML {@code HttpMessageConverter} class is <strong>no longer required</strong>.</p>
 *
 * <p>Simply add the dependency below and the built-in
 * {@code JacksonYamlHttpMessageConverter} is registered automatically:</p>
 *
 * <pre>{@code
 * <dependency>
 *     <groupId>tools.jackson.dataformat</groupId>
 *     <artifactId>jackson-dataformat-yaml</artifactId>
 * </dependency>
 * }</pre>
 */

@SuppressWarnings("removal")
public class YamlJacksonHttpMessageConverter extends MappingJackson2HttpMessageConverter {

    public YamlJacksonHttpMessageConverter() {
        super(new ObjectMapper(new YAMLFactory()));
        setSupportedMediaTypes(java.util.Arrays.asList(
                new MediaType("application", "x-yaml"),
                new MediaType("text", "yaml"),
                new MediaType("application", "yaml")
        ));
    }
}
