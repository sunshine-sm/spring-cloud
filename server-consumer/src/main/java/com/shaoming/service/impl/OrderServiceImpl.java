package com.shaoming.service.impl;

import com.shaoming.model.Order;
import com.shaoming.model.Product;
import com.shaoming.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Override
    public Order getOrderById(Long id) {
//        return new Order(id, System.currentTimeMillis()+"", BigDecimal.valueOf(10000), getProductsByDiscoveryClient());
        return new Order(id, System.currentTimeMillis()+"", BigDecimal.valueOf(10000), getProductsByLoadBalancerClient());
//        return new Order(id, System.currentTimeMillis()+"", BigDecimal.valueOf(10000), getProductsByLoadBalancerAnnotation());
    }

    /**
     * 通过 DiscoveryClient 实现跨服务调用
     *      1. 通过服务名称获取到的是【服务列表】，如果该服务未做【集群】，获取到的列表中将只有一条数据
     *      2. 获取到的指定【服务列表】，该列表的顺序会根据最近使用的服务放到末尾，所以 instances.get(0) 获取到的会是最久未使用过的
     */
    private List<Product> getProductsByDiscoveryClient() {
        StringBuilder sb;

        List<String> services = discoveryClient.getServices();
        if (CollectionUtils.isEmpty(services)) {
            return null;
        }

        // 根据服务名获取指定服务
        List<ServiceInstance> instances = discoveryClient.getInstances("server-provider");
        if (CollectionUtils.isEmpty(instances)) {
            return null;
        }

        for (ServiceInstance instance : instances) {
            System.out.println("InstanceID: " + instance.getInstanceId());
        }

        // 拼装请求链接
        ServiceInstance si = instances.get(0);
        sb = new StringBuilder();
        sb.append("http://").append(si.getHost()).append(":").append(si.getPort()).append("/product/list");
        System.out.println("请求链接:" + sb);

        ResponseEntity<List<Product>> exchange = restTemplate.exchange(
                sb.toString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Product>>() {
                });
        return exchange.getBody();
    }

    /**
     * 通过 LoadBalancerClient 实现跨服务调用
     *      1. 通过【服务名称】获取相关【集群】服务中的一个；
     *      2. 会自动进行负载均衡，默认以轮循的方式；
     */
    private List<Product> getProductsByLoadBalancerClient() {
        StringBuilder sb;

        // 根据服务名获取指定服务
        ServiceInstance si = loadBalancerClient.choose("server-provider");
        if (null == si) return null;

        // 拼接链接
        sb = new StringBuilder();
        sb.append("http://").append(si.getHost()).append(":").append(si.getPort()).append("/product/list");
        System.out.println("请求链接:" + sb);

        ResponseEntity<List<Product>> exchange = restTemplate.exchange(
                sb.toString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Product>>() {
                });
        return exchange.getBody();
    }

    /**
     * 通过 LoadBalancer 注解进行跨服务调用
     *      1. 需在启动类中的 RestTemplate 加上注解【@LoadBalanced】
     *      2. 会自动进行负载均衡，默认以轮循的方式；
     */
    private List<Product> getProductsByLoadBalancerAnnotation() {
        ResponseEntity<List<Product>> exchange = restTemplate.exchange(
                "http://server-provider/product/list",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Product>>() {
                });
        return exchange.getBody();
    }

}
