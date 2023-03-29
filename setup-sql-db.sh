# Create a single database and configure a firewall rule

# Specify appropriate IP address values for your environment
# to limit access to the SQL Database server
startIp=174.165.43.130
endIp=174.165.43.130

# Variable block
let "randomIdentifier=$RANDOM*$RANDOM"
location="East US"
resourceGroup="dhlrepro-$randomIdentifier"
server="dhlrepro"
database="dhlreprodb"
login="dhlrepro"
password="Passw0rD1234"


echo "Using resource group $resourceGroup with login: $login, password: $password..."
echo "Creating $resourceGroup in $location..."
az group create --name $resourceGroup --location "$location"
echo "Creating $server in $location..."
az sql server create --name $server --resource-group $resourceGroup --location "$location" --admin-user $login --admin-password $password
echo "Configuring firewall..."
az sql server firewall-rule create --resource-group $resourceGroup --server $server -n AllowYourIp --start-ip-address $startIp --end-ip-address $endIp
echo "Creating $database on $server..."
az sql db create --resource-group $resourceGroup --server $server --name $database --sample-name AdventureWorksLT --edition GeneralPurpose --family Gen5 --capacity 2 --zone-redundant true # zone redundancy is only supported on premium and business critical service tiers