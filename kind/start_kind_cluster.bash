#!/bin/bash
# To get here - cd "/mnt/c/Users/andre/Documents/IMPORTANT/GitHub Portfolio/Note_Plan/kind"
# To convert the file to unix - dos2unix start_kind_cluster.bash
# To run the script - bash start_kind_cluster.bash
# To port forward run - kubectl port-forward svc/springboot-app-service 8080:8080

kind delete cluster --name noteplan # tries to delete the cluster if it already exists
kind create cluster --name noteplan

echo "Waiting for Kubernetes to be ready..."
until kubectl get nodes &> /dev/null; do
    echo -n "."
    sleep 1
done

echo -e "\nCluster is ready."

kubectl apply -f secret.yaml
kubectl apply -f mysql-db.yaml
kubectl apply -f springboot-app.yaml

echo -e "\nCreated services:"
kubectl get services
echo -e "\nDone! - check the created pods by running: kubectl get pods"

