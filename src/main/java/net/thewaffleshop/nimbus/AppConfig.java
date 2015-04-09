package net.thewaffleshop.nimbus;

import net.thewaffleshop.nimbus.security.SecurityConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.webjars.WebJarAssetLocator;


/**
 *
 * @author rhollencamp
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableJpaRepositories
@Import(SecurityConfig.class)
public class AppConfig
{
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

	@Bean
	public WebJarAssetLocator assetLocator()
	{
		return new WebJarAssetLocator();
	}
}
