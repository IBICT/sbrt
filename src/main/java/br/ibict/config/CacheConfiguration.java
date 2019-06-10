package br.ibict.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(br.ibict.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(br.ibict.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(br.ibict.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(br.ibict.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(br.ibict.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(br.ibict.domain.LegalEntity.class.getName(), jcacheConfiguration);
            cm.createCache(br.ibict.domain.LegalEntity.class.getName() + ".answers", jcacheConfiguration);
            cm.createCache(br.ibict.domain.LegalEntity.class.getName() + ".questions", jcacheConfiguration);
            cm.createCache(br.ibict.domain.LegalEntity.class.getName() + ".persons", jcacheConfiguration);
            cm.createCache(br.ibict.domain.Contact.class.getName(), jcacheConfiguration);
            cm.createCache(br.ibict.domain.Person.class.getName(), jcacheConfiguration);
            cm.createCache(br.ibict.domain.Person.class.getName() + ".legalEntities", jcacheConfiguration);
            cm.createCache(br.ibict.domain.Person.class.getName() + ".contacts", jcacheConfiguration);
            cm.createCache(br.ibict.domain.Question.class.getName(), jcacheConfiguration);
            cm.createCache(br.ibict.domain.Question.class.getName() + ".answers", jcacheConfiguration);
            cm.createCache(br.ibict.domain.Answer.class.getName(), jcacheConfiguration);
            cm.createCache(br.ibict.domain.Answer.class.getName() + ".keywords", jcacheConfiguration);
            cm.createCache(br.ibict.domain.Answer.class.getName() + ".references", jcacheConfiguration);
            cm.createCache(br.ibict.domain.AnswerSummary.class.getName(), jcacheConfiguration);
            cm.createCache(br.ibict.domain.Keyword.class.getName(), jcacheConfiguration);
            cm.createCache(br.ibict.domain.Cnae.class.getName(), jcacheConfiguration);
            cm.createCache(br.ibict.domain.Cnae.class.getName() + ".answers", jcacheConfiguration);
            cm.createCache(br.ibict.domain.Cnae.class.getName() + ".legalEntities", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
