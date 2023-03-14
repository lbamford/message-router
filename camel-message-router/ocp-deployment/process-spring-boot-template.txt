oc process -f fuse-spring-boot-template.yaml \
-p APP_NAME=camel-message-router \
-p GIT_REPO=https://github.com/lbamford/message-router.git \
-p GIT_REF=main \
-p GIT_CONTEXT_DIR=camel-message-router \
-p SERVICE_NAME=camel-message-router \
-p BUILDER_VERSION=1.11 \
-p CPU_REQUEST=1 \
-p MEMORY_REQUEST=1Gi \
-p CPU_LIMIT=2 \
-p MEMORY_LIMIT=8Gi \
-p BUILD_MEMORY_REQUEST=2Gi \
-p BUILD_MEMORY_LIMIT=3Gi \
-p DB_SERVICE_NAME=jdbc:oracle:thin:@192.168.0.71:51521/local \
-p DB_SERVICE_USERNAME=user \
-p DB_SERVICE_PASSWORD=password \
-o yaml > processed-fuse-sb-template.yaml

#oc create -f processed-fuse-template.yaml
#oc policy add-role-to-user view --serviceaccount=default
