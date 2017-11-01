SpringCloud服务网关Zuul
1) pom.xml中除了增加Zuul(spring-cloud-starter-zuul)、eureka(spring-cloud-starter-eureka)的依赖
2) 应用主类使用@EnableZuulProxy注解开启Zuul
@EnableZuulProxy
@SpringCloudApplication
//相当于@SpringBootApplication、@EnableDiscoveryClient、@EnableCircuitBreaker 
public class Application {
	public static void main(String[] args) {
		new SpringApplicationBuilder(Application.class).web(true).run(args);
	}
}
3)application.properties中配置Zuul应用的基础信息，如：应用名、服务端口等。
spring.application.name=api-gateway
server.port=5555
4)zuul路由配置
# routes to url
zuul.routes.api-a-url.path=/api-a-url/**
zuul.routes.api-a-url.url=http://localhost:2222/
该配置，定义了，所有到Zuul的中规则为：/api-a-url/**的访问都映射到http://localhost:2222/上，
也就是说当我们访问http://localhost:5555/api-a-url/add?a=1&b=2的时候，Zuul会将该请求路由到：
http://localhost:2222/add?a=1&b=2上。

通过url映射的方式对于Zuul来说，并不是特别友好，Zuul需要知道我们所有为服务的地址，才能完成所有的映射配置。
而实际上，我们在实现微服务架构时，服务名与服务实例地址的关系在eureka server中已经存在了，
所以只需要将Zuul注册到eureka server上去发现其他服务，我们就可以实现对serviceId的映射
zuul.routes.api-a.path=/api-a/**
zuul.routes.api-a.serviceId=service-A
zuul.routes.api-b.path=/api-b/**
zuul.routes.api-b.serviceId=service-B
eureka.client.serviceUrl.defaultZone=http://localhost:1111/eureka/

5)url访问测试
http://localhost:5555/api-a/add?a=1&b=2：通过serviceId映射访问service-A中的add服务
http://localhost:5555/api-b/add?a=1&b=2：通过serviceId映射访问service-B中的add服务
http://localhost:5555/api-a-url/add?a=1&b=2：通过url映射访问service-A中的add服务