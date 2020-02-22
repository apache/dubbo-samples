## kubernetes 

```bash 

# create service-account
kubectl create -f ./registry/kubernetes/sa.yaml

# create role 
kubectl create -f ./registry/kubernetes/role.yaml

# bind role and service-account
kubectl create -f ./registry/kubernetes/role-binding.yaml

# create server
kubectl create -f ./registry/kubernetes/server.yaml

# create client
kubectl create -f ./registry/kubernetes/client.yaml

# read the client log
kubectl logs -f client

## uninstall 
kubectl delete -f ./registry/kubernetes/sa.yaml
kubectl delete -f ./registry/kubernetes/role.yaml
kubectl delete -f ./registry/kubernetes/role-binding.yaml
kubectl delete -f ./registry/kubernetes/server.yaml
kubectl delete -f ./registry/kubernetes/client.yaml
```