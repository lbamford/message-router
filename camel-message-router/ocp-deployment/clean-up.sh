NAMESPACE=poc
oc project $NAMESPACE
oc delete pod --field-selector=status.phase==Succeeded -n $NAMESPACE
oc delete pod --field-selector=status.phase==Failed -n $NAMESPACE
oc adm prune deployments --keep-complete=1 --keep-failed=0 -n $NAMESPACE --confirm
oc adm prune builds --orphans --keep-complete=1 --keep-failed=0 -n $NAMESPACE --confirm
oc delete -n $NAMESPACE $(oc get all | grep replicaset.apps | awk '{if ($2 + $3 + $4 == 0) print $1}')
