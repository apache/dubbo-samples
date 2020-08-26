# Steps to run the samples

### Config Kubernetes

There are two ways to use Kubernetes as registry in dubbo.

1. API Server

    `dubbo-samples-kubernetes-apiserver` is the sample of API server.

   1.1 Token
    
    In order to access Api Server, you need to set up a Service Account with the following permissions in your Kubernetes Cluster.
      
    - Read and Write permission to Pods
    - Read permission to Services
    - Read permission to Endpoints
    
    Here is an example to create this.
    
    ``` yaml
    apiVersion: v1
    kind: Namespace
    metadata:
      name: dubbo-demo
    ---
    apiVersion: rbac.authorization.k8s.io/v1
    kind: Role
    metadata:
      namespace: dubbo-demo
      name: dubbo-role
    rules:
    - apiGroups: [""]
      resources: ["pods"]
      verbs: ["get", "watch", "list", "update", "patch"]
    - apiGroups: [""] 
      resources: ["services", "endpoints"]
      verbs: ["get", "watch", "list"]
    ---
    apiVersion: v1
    kind: ServiceAccount
    metadata:
      name: dubbo-sa
      namespace: dubbo-demo
    ---
    apiVersion: rbac.authorization.k8s.io/v1
    kind: RoleBinding
    metadata:
      name: dubbo-sa-bind
      namespace: dubbo-demo
    roleRef:
      apiGroup: rbac.authorization.k8s.io
      kind: Role
      name: dubbo-role
    subjects:
    - kind: ServiceAccount
      name: dubbo-sa
    ```
   
    Then, Retrieve the token for the `dubbo-sa` service account.
    
    ```
    kubectl -n dubbo-demo describe secret $(kubectl -n dubbo-demo get secret | grep dubbo-sa | awk '{print $1}')
    ```
   
    Copy the <authentication_token> value from the output and replace with `oauthToken` property in properties file:
    
    ```
    Name:         dubbo-sa-token-2g5c6
    Namespace:    dubbo-demo
    Labels:       <none>
    Annotations:  kubernetes.io/service-account.name: dubbo-sa
                  kubernetes.io/service-account.uid: 963e68f3-738d-4f10-bf32-92a3fbf44774
    
    Type:  kubernetes.io/service-account-token
    
    Data
    ====
    ca.crt:     1025 bytes
    namespace:  10 bytes
    token:      <authentication_token>
    ```
   
   1.2 API URL
   
    Get the API URL by running this command:
    
    ```
    kubectl cluster-info | grep 'Kubernetes master' | awk '/http/ {print $NF}'
    ```
   
    You can get the output like:
    
    ```
    https://<API Server ip>:<API Server port>
    ```
   
    Copy <API Server ip> and <API Server port> to properties.
    
    Notice: protocol is needless to specify, dubbo will use `https` protocol as default.
    
2. DNS

    `dubbo-samples-kubernetes-dns` is the sample of DNS.

    In order to use DNS as registry, you need to retrieve DNS host from Kubernetes.
    You can get it by this.
    
    ```
    kubectl get service -n kube-system | grep kube-dns
    ```
   
    ```
    kube-dns   ClusterIP   <DNS ip>      <none>   53/UDP,53/TCP,9153/TCP   68d
    ```
   
    Copy <DNS ip> to properties.
    
### Start demo

1. Build maven project and deploy to docker registry with `Dockerfile`.
    
2. Deploy the provider to kubernetes cluster.

    ``` yaml
    apiVersion: apps/v1
    kind: ReplicaSet
    metadata:
      name: kubernetes-apiserver-demo-provider
    spec:
      replicas: 3
      selector:
        matchLabels:
          tier: apiserver-demo-provider
      template:
        metadata:
          labels:
            tier: apiserver-demo-provider
            io.dubbo: MyTest
        spec:
          containers:
          - name: server
            image: < Your image path >
            ports:
              - containerPort: 20880
            livenessProbe:
              httpGet:
                path: /live
                port: 22222
              initialDelaySeconds: 5
              periodSeconds: 5
            readinessProbe:
              httpGet:
                path: /ready
                port: 22222
              initialDelaySeconds: 5
              periodSeconds: 5
            startupProbe:
              httpGet:
                path: /startup
                port: 22222
              failureThreshold: 30
              periodSeconds: 10
    ---
    apiVersion: v1
    kind: Service
    metadata:
      name: kubernetes-apiserver-demo-provider
    spec:
      clusterIP: None
      selector:
        io.dubbo: MyTest
      ports:
        - protocol: TCP
          port: 20880
          targetPort: 20880
    ```

3. Run Dubbo consumer demo `org.apache.dubbo.samples.ConsumerBootstrap.main`