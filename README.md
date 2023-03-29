# sockFactory3

## Overview
This is a testing app that evaluates the performance of using `TCP_FASTACK` for JDBC connections to SQL Server.
There are two images published at twright/pocapp:
-`twrightmsft/pocapp:v1` is a simple app to query a single table on AdventureWorks DB (`docker pull twrightmsft/pocapp:v1`).
-`twrightmsft/pocapp:v2` is a simple app to that queries a larger table on a custom DB created by the sql-create-4ktable.sql file in this project (`docker pull twrightmsft/pocapp:v2`).

The test connects to SQL DB using the settings provided in the env vars and then executes a simple T-SQL query against the AdventureWorks database 5 times with 2 seconds in between each iteration.
If you need a SQL DB with AdventureWorks DB you can create one by following the [documentation](https://learn.microsoft.com/en-us/azure/azure-sql/database/scripts/create-and-configure-database-cli?view=azuresql#run-the-script).

DON'T FORGET TO OPEN YOUR FIREWALL!!  Easiest thing to do is try running the app once and you'll get a firewall failure error message in it with the node IP address in the error message. You can then just go add that to the firewall exception rules in your SQL DB server.  https://learn.microsoft.com/en-us/azure/azure-sql/database/firewall-create-server-level-portal-quickstart?view=azuresql

## Deployment

### Kubernetes

You can deploy to K8s by using `pod.yaml` (`kubectl create -f pod.yaml`).
When you deploy the pod, you need to edit the pod.yaml file to specify the env vars of the SQL DB to connect to.  The app assumes SQL DB in Azure (i.e. `<someservername>.database.windows.net`).
Example:

```terminal
    env:
    - name: DBNAME
      value: "domedbname"
    - name: USERNAME
      value: "azureuser"
    - name: SERVERNAME
      value: "someservername"
    - name: PASSWORD
      value: "somepassword12345"
    - name: PORT
      value: "1433"
    - name: USEFASTACK
      value: "true" #true/false
```

`USEFASTACK` determines whether or not TCP_FASTACK is used on the socket or not.

Deployment and viewing output:

```terminal
kubectl create -f pod.yaml
kubectl logs pocapp
```

### On a standalone VM using Docker container

* Deploy VM (Ubuntu 22.04 for example)
* [Install docker](https://docs.docker.com/engine/install/ubuntu/#install-using-the-repository)
* Install Azure CLI (`curl -sL https://aka.ms/InstallAzureCLIDeb | sudo bash`)
* Install git (sudo apt install git) if it isn't installed already.
* Git clone this repo: `git clone https://github.com/twright-msft/sockFactory3.git`
* Switch to main branch: `git checkout main`
* Deploy the Azure SQL DB by editing the variables in the setup-sql-db.sh file and running it, especially the client IP address that needs to be added to the firewall allow list. **Change the password and other parameters if you want, but be sure to also change the to match in the `docker run` command below as well.**
 * Run the following command to start the container and connect the app to the Azure SQL DB:

```terminal
sudo docker run -it -e DBNAME=dhlreprodb -e SERVERNAME=dhlrepro -e USERNAME=dhlrepro -e PASSWORD=Passw0rD1234 -e PORT=1433 -e USEFASTACK=false twrightmsft/pocapp:v1
 ```
