/**
 * 
 */
package com.harmeetsingh13.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Harmeet Singh(Taara)
 *
 */

@Configuration
@EnableWebMvc
@ComponentScan({"com.harmeetsingh13"})
public class WebConfiguration extends WebMvcConfigurerAdapter{

}
