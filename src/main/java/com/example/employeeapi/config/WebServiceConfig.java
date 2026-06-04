package com.example.employeeapi.config;

import java.util.List;
import java.util.Properties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.endpoint.adapter.method.MarshallingPayloadMethodProcessor;
import org.springframework.ws.server.endpoint.adapter.method.MethodArgumentResolver;
import org.springframework.ws.server.endpoint.adapter.method.MethodReturnValueHandler;
import org.springframework.ws.soap.server.endpoint.SoapFaultDefinition;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(
            ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    @Bean(name = "employees")
    public DefaultWsdl11Definition employeesWsdl11Definition(XsdSchema employeesSchema) {
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("EmployeesPort");
        definition.setLocationUri("/ws");
        definition.setTargetNamespace("http://example.com/employeeapi/ws");
        definition.setSchema(employeesSchema);
        return definition;
    }

    @Bean
    public XsdSchema employeesSchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/employees.xsd"));
    }

    @Bean(name = "departments")
    public DefaultWsdl11Definition departmentsWsdl11Definition(XsdSchema departmentsSchema) {
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("DepartmentsPort");
        definition.setLocationUri("/ws");
        definition.setTargetNamespace("http://example.com/employeeapi/ws");
        definition.setSchema(departmentsSchema);
        return definition;
    }

    @Bean
    public XsdSchema departmentsSchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/departments.xsd"));
    }

    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan("com.example.employeeapi.soap.dto");
        return marshaller;
    }

    @Bean
    public SoapFaultMappingExceptionResolver soapFaultMappingExceptionResolver() {
        SoapFaultMappingExceptionResolver resolver = new SoapFaultMappingExceptionResolver();

        Properties errorMappings = new Properties();
        errorMappings.setProperty("java.lang.UnsupportedOperationException", "SERVER");
        errorMappings.setProperty("java.lang.IllegalArgumentException", "CLIENT");

        SoapFaultDefinition defaultFault = new SoapFaultDefinition();
        defaultFault.setFaultCode(SoapFaultDefinition.SERVER);

        resolver.setExceptionMappings(errorMappings);
        resolver.setDefaultFault(defaultFault);
        resolver.setOrder(1);
        return resolver;
    }

    @Override
    public void addArgumentResolvers(List<MethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new MarshallingPayloadMethodProcessor(jaxb2Marshaller(), jaxb2Marshaller()));
    }

    @Override
    public void addReturnValueHandlers(List<MethodReturnValueHandler> returnValueHandlers) {
        returnValueHandlers.add(new MarshallingPayloadMethodProcessor(jaxb2Marshaller(), jaxb2Marshaller()));
    }
}
