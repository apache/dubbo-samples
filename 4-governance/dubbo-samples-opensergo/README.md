# governance dubbo using OpenSergo Tag router
1. step1 run `BasicProvider.java`

> it will start an zookeeper and register two service with dubbo port 20880
2. step2 run `BasicProviderOtherPort.java`
> it will register another two service with dubbo port 20881
3. step3 Start OpenSergoControlPlane and apply traffic config
[First start OpenSergo control plane](https://opensergo.io/docs/quick-start/opensergo-control-plane/) , Then we publish the label routing rules through the OpenSergo control plane. We publish a TrafficRouter rule.
```YAML
kubectl apply -f - << EOF
apiVersion: traffic.opensergo.io/v1alpha1
kind: TrafficRouter
metadata:
  name: governance-opensergo-traffic-provider
  namespace: default
  labels:
    app: governance-opensergo-traffic-provider
spec:
  hosts:
    - governance-opensergo-traffic-provider
  http:
  - match:
    - headers:
        tag:
          exact: v2
    route:
    - destination:
        host: governance-opensergo-traffic-provider
        subset: v2
  - route:
    - destination:
        host: governance-opensergo-traffic-provider
        subset: v1
EOF
```
This [TrafficRouter](https://github.com/opensergo/opensergo-specification/blob/main/specification/en/traffic-routing.md)  
specifies the simplest label routing rule. Dubbo requests with the v2 header are routed to v2, and the rest of the traffic is routed to v1.

4. step4 run `BasicConsumer.java`
>  it will consume service by rule defined by yml file
> * All consumer taged by `v2` come from provider in port 20881
> * All consumer taged by `v1` come from provider in port 20880