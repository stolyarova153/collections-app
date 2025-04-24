package ru.spbpu.collections;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;

import java.util.Arrays;

@Slf4j
@SpringBootApplication
public class CollectionsAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(CollectionsAppApplication.class, args);
    }

    @EventListener
    public void handleContextRefresh(final ContextRefreshedEvent event) {

        final Environment env = event.getApplicationContext().getEnvironment();

        log.info("Active profiles: {}", Arrays.toString(env.getActiveProfiles()));

        final MutablePropertySources sources = ((AbstractEnvironment)env).getPropertySources();

        sources.stream()
               .filter(s -> s instanceof EnumerablePropertySource<?>)
               .map(s -> ((EnumerablePropertySource<?>)s).getPropertyNames())
               .flatMap(Arrays::stream)
               .distinct()
               .filter(s -> !(s.contains("credentials") || s.contains("password")))
               .forEach(s -> log.info("{}: {}", s, env.getProperty(s)));
    }

}
