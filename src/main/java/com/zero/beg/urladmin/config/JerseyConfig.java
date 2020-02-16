package com.zero.beg.urladmin.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.jaxrs.FastJsonFeature;
import com.zero.beg.urladmin.rest.URLAdminResource;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Configuration
public class JerseyConfig extends ResourceConfig {


    public JerseyConfig() {
//        register(ObjectMapperContextResolver.class);
//        register(JacksonFeature.class);
        register(CORSResponseFilter.class);
        register(FastJsonResolver.class);
        register(FastJsonFeature.class);
        register(URLAdminResource.class);
//        register(mapper);
    }

    @Provider
    static class FastJsonResolver implements ContextResolver<FastJsonConfig> {
        // 用FastJson替换掉默认的Jackson
        public FastJsonConfig getContext(Class<?> type) {
            FastJsonConfig fastJsonConfig = new FastJsonConfig();
//            SerializerFeature[] serializerFeatures = new SerializerFeature[]{
//                    SerializerFeature.BrowserSecure,
////                    SerializerFeature.WriteClassName
//                    };
//            fastJsonConfig.setSerializerFeatures(serializerFeatures);
            return fastJsonConfig;
        }
    }

    @Provider
    static class CORSResponseFilter implements ContainerResponseFilter {
        public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
                throws IOException {

            MultivaluedMap<String, Object> headers = responseContext.getHeaders();

            headers.add("Access-Control-Allow-Origin", "*");
            //headers.add("Access-Control-Allow-Origin", "http://abcd.org"); //allows CORS requests only coming from abcd.org
            headers.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
            headers.add("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, X-Codingpedia");
        }
    }
}
