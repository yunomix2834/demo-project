kubectl get deployments

kubectl get services

kubectl get replicaset

kubectl get pods

kubectl apply -f configserver.yml

kubectl delete <pods_id>

kubectl -n kubernetes-dashboard port-forward svc/kubernetes-dashboard-kong-proxy 8443:443

kubectl get secret admin-user -n kubernetes-dashboard -o jsonpath="{.data.token}" | base64 -d

kubectl get events --sort-by=.metadata.creationTimestamp

kubectl scale deployment <deployment_name> --replicas=1

kubectl describe pod <pod_name>

kubectl set image deployment gatewayserver-deployment gatewayserver=eazybytes/gatewayserver:s11 --record

kubectl rollout history deployment gatewayserver-deployment

kubectl undo deployment gatewayserver-deployment --to-revision=1

kubectl delete -f <yml>