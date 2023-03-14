/**
 *  Copyright 2005-2016 Red Hat, Inc.
 *
 *  Red Hat licenses this file to you under the Apache License, version
 *  2.0 (the "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied.  See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package example.com;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.apache.camel.builder.xml.Namespaces;
import org.apache.camel.LoggingLevel;

/**
 * A spring-boot application that includes a Camel route builder to setup the Camel routes
 */
@SpringBootApplication
@ImportResource({"classpath:spring/camel-context.xml"})
public class Application extends RouteBuilder {

    // must have a main method spring-boot can run
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    final Namespaces ns = new Namespaces("{{namespacePrefix}}", "{{namespaceUri}}");

   @Override
    public void configure() throws Exception {
       from("cxf:bean:entryPoint?dataFormat={{dataFormat}}&loggingFeatureEnabled={{loggingFeature}}")
               .setHeader("{{routeValue}}", ns.xpath("//{{routeValue}}/text()",String.class))
               .log(LoggingLevel.INFO, "{{routeValue}}: ${header.{{routeValue}}}")
               .to("log:input").to("log:output").log(LoggingLevel.INFO," send to > {{targetHost}} / ${header.{{routeValue}}}");
               //.toD("{{targetHost}}/${header.{{routeValue}}}");
    }

    @Bean
    ServletRegistrationBean servletRegistrationBeanCXF() {
        ServletRegistrationBean servlet = new ServletRegistrationBean(
                new CXFServlet(), "/cxf/*");
        servlet.setName("CXFServlet");
        return servlet;
    }
}
