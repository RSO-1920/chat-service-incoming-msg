# chat-service-incoming-msg
chat incoming msg

## DOCKER RUN
```docker run -d --name incomingmsg --network rso1920 -e KUMULUZEE_CONFIG_ETCD_HOSTS=http://etcd:2379 -e KUMULUZEE_DISCOVERY_ETCD_HOSTS=http://etcd:2379 -p 8090:8090 rso1920/incomingmsg```