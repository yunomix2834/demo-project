# Add kubernetes-dashboard repository
helm repo add kubernetes-dashboard https://kubernetes.github.io/dashboard/
# Deploy a Helm Release named "kubernetes-dashboard" using the kubernetes-dashboard chart
helm upgrade --install kubernetes-dashboard kubernetes-dashboard/kubernetes-dashboard --create-namespace --namespace kubernetes-dashboard

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

helm ls

helm search hub wordpress

helm repo add bitnami https://charts.bitnami.com/bitnami                                                     
helm install happy-panda bitnami/wordpress

export SERVICE_IP=$(kubectl get svc --namespace default happy-panda-wordpress --template "{{ range (index .status.loadBalancer.ingress 0) }}{{ . }}{{ end }}")
echo "WordPress URL: http://$SERVICE_IP/"
echo "WordPress Admin URL: http://$SERVICE_IP/admin"

helm env

helm uninstall happy-panda

helm create eazybank-common

helm dependencies build

helm template . 