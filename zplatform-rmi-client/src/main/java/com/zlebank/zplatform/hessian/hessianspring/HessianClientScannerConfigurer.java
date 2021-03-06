/* 
 * HessianClientScannerConfigurer.java  
 * 
 * version TODO
 *
 * 2016年1月14日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.hessian.hessianspring;


import static org.springframework.util.Assert.notNull;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;
import org.springframework.util.StringUtils;

import com.zlebank.zplatform.hessian.core.Context;

 

/**
 * hessian 接口客户端自动扫描注入
 *
 * @author guojia
 * @version
 * @date 2016年1月14日 下午2:34:11
 * @since 
 */
public class HessianClientScannerConfigurer implements
        BeanDefinitionRegistryPostProcessor, InitializingBean,
        ApplicationContextAware, BeanNameAware {
 
    protected final Logger logger = LoggerFactory.getLogger(getClass());
 
    private String beanName;
    private String basePackage;
     
    private Resource[] locations;
 
    private boolean includeAnnotationConfig = true;
 
    private ApplicationContext applicationContext;
 
    // 实现了该接口
    private Class<?> markerInterface;
    // 配置了该注解
    private Class<? extends Annotation> annotationClass;
 
    private BeanNameGenerator nameGenerator;
     
    @Override
    public void postProcessBeanFactory(
            ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }
 
    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }
 
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }
 
    @Override
    public void afterPropertiesSet() throws Exception {
        notNull(this.locations, "Property 'locations' is required " + beanName);
        notNull(this.basePackage, "Property 'basePackage' is required " + beanName);
        Properties props = new Properties();
        loadProperties(props);
        for (Entry<Object, Object> resource : props.entrySet()) {
            System.setProperty((String)resource.getKey(), (String)resource.getValue());
        }
    }
     
    @Override
    public void postProcessBeanDefinitionRegistry(
            BeanDefinitionRegistry registry) throws BeansException {
        HessianClassPathScanner scan = new HessianClassPathScanner(registry);
        scan.setResourceLoader(this.applicationContext);
        scan.setBeanNameGenerator(this.nameGenerator);
        // 引入注解配置
        scan.setIncludeAnnotationConfig(this.includeAnnotationConfig);
        scan.registerFilters();
 
        String[] basePackages = StringUtils.tokenizeToStringArray(this.basePackage, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
        scan.scan(basePackages);
    }
 
    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }
 
    public void setIncludeAnnotationConfig(boolean includeAnnotationConfig) {
        this.includeAnnotationConfig = includeAnnotationConfig;
    }
 
    public Class<?> getMarkerInterface() {
        return markerInterface;
    }
 
    public void setMarkerInterface(Class<?> markerInterface) {
        this.markerInterface = markerInterface;
    }
 
    public Class<? extends Annotation> getAnnotationClass() {
        return annotationClass;
    }
 
    public void setAnnotationClass(Class<? extends Annotation> annotationClass) {
        this.annotationClass = annotationClass;
    }
 
    public BeanNameGenerator getNameGenerator() {
        return nameGenerator;
    }
 
    public void setNameGenerator(BeanNameGenerator nameGenerator) {
        this.nameGenerator = nameGenerator;
    }
     
    public void setLocations(Resource... locations) {
        this.locations = locations;
    }
     
    protected void loadProperties(Properties props) throws IOException {
        if (this.locations != null) {
            for (Resource location : this.locations) {
                if (logger.isInfoEnabled()) {
                    logger.info("Loading properties file from " + location);
                }
                try {
                    PropertiesLoaderUtils.fillProperties(props, location);
                }catch (IOException ex) {
                    if (logger.isWarnEnabled()) {
                        logger.warn("Could not load properties from " + location + ": " + ex.getMessage());
                    }
                }
            }
        }
    }
     
    private class HessianClassPathScanner extends ClassPathBeanDefinitionScanner {
 
        public HessianClassPathScanner(BeanDefinitionRegistry registry) {
            super(registry, false);
        }
 
        @Override
        public Set<BeanDefinitionHolder> doScan(String... basePackages) {
            Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
            if (beanDefinitions.isEmpty()) {
                logger.warn("No hessian was found in '"
                        + Arrays.toString(basePackages)
                        + "' package. Please check your configuration.");
            } else {
                for (BeanDefinitionHolder holder : beanDefinitions) {
                    GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();
 
                    if (logger.isDebugEnabled()) {
                        logger.debug("Creating HessianFactoryBean with name '"
                                + holder.getBeanName() + "' and '"
                                + definition.getBeanClassName()
                                + "' hessianInterface");
                    }
                     
                    AnnotationMetadata metadata = ((ScannedGenericBeanDefinition)definition).getMetadata();
                    Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(annotationClass.getName());
                    Context context = (Context)annotationAttributes.get("context");
                    String uri = (String)annotationAttributes.get("uri");
                    Boolean overloadEnabled = MapUtils.getBoolean(annotationAttributes, "overloadEnabled", false);
                     
                     
                    definition.getPropertyValues().add("serviceUrl", context.getRemoteUrl() + uri);
                    definition.getPropertyValues().add("serviceInterface", definition.getBeanClassName());
//                  新增overloadEnabled属性，并把它的值设置为true，默认是false，则Hessian就能支持方法的重载了
                    definition.getPropertyValues().add("overloadEnabled", overloadEnabled);
                    definition.setBeanClass(HessianProxyFactoryBean.class);
                     
                     
                    // the mapper interface is the original class of the bean
                    // but, the actual class of the bean is HessianFactoryBean
//                  definition.getPropertyValues().add("hessianInterface", definition.getBeanClassName());
//                  definition.setBeanClass(HessianFactoryBean.class);
                }
            }
            return beanDefinitions;
 
        }
 
        @Override
        protected boolean isCandidateComponent(
                AnnotatedBeanDefinition beanDefinition) {
            return (beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent());
        }
 
        /**
         * {@inheritDoc}
         */
        @Override
        protected boolean checkCandidate(String beanName,
                BeanDefinition beanDefinition) throws IllegalStateException {
            if (super.checkCandidate(beanName, beanDefinition)) {
                return true;
            } else {
                logger.warn("Skipping HessianFactoryBean with name '" + beanName
                        + "' and '" + beanDefinition.getBeanClassName()
                        + "' hessianInterface"
                        + ". Bean already defined with the same name!");
                return false;
            }
        }
 
        public void registerFilters() {
            boolean acceptAllInterfaces = true;
 
            // if specified, use the given annotation and / or marker interface
            if (HessianClientScannerConfigurer.this.annotationClass != null) {
              addIncludeFilter(new AnnotationTypeFilter(HessianClientScannerConfigurer.this.annotationClass));
              acceptAllInterfaces = false;
            }
 
            // override AssignableTypeFilter to ignore matches on the actual marker interface
            if (HessianClientScannerConfigurer.this.markerInterface != null) {
              addIncludeFilter(new AssignableTypeFilter(HessianClientScannerConfigurer.this.markerInterface) {
                @Override
                protected boolean matchClassName(String className) {
                  return false;
                }
              });
              acceptAllInterfaces = false;
            }
             
            if (acceptAllInterfaces) {
                // default include filter that accepts all classes
                addIncludeFilter(new TypeFilter() {
                    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
                        return true;
                    }
                });
            }
 
            // exclude package-info.java
            addExcludeFilter(new TypeFilter() {
                public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
                    String className = metadataReader.getClassMetadata().getClassName();
                    return className.endsWith("package-info");
                }
            });
        }
    }
}