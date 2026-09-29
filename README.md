# capacity-planning .
capacity-planning

**To run locally:**
SpringBoot: CptWebApplication
VM arguments: 
-DshowHSQLConsole=true
-Djava.awt.headless=false
Active Profile:
HSQLDB

## CD/CD configuration

### Local variables defined in .gtilab-ci.yaml file:
* **DOCKER_DRIVER**: default value: overlay2 - because of performance
* **IMAGE_BACKEND_NAME**: image name of backend: <project_name>-backend:<branch_name>
* **IMAGE_UI_NAME**: image name of ui: <project_name>-ui:<branch_name>
### Variables comes from CICD:
* **CI_REGISTRY_IMAGE**: project name
* **CI_COMMIT_REF_SLUG**: branch name
### GitLab
should be added on Gitlab: settings/CICD/Variables. With options:
- Type: Variable
- Environments: All
- Visibility: Masked
- Flag Protect variable: selected
- Flag Expand variable reference: selected


* **CI_REGISTRY_USER**: username for login to gitlab - used to push/pull docker image on Container Registry (Deploy/Container Registry) 
* **CI_REGISTRY_PASSWORD**: password for login to gitlab - used to push/pull docker image on Container Registry (Deploy/Container Registry)
* **GITLAB_USER_EMAIL**: email of user to gitlab - needed to create secret on azure
* **AZURE_CLIENT_ID**: field 'appId' in response of command: az ad sp create-for-rbac on azure
* **AZURE_CLIENT_SECRET**: field 'password' in response of command: az ad sp create-for-rbac on azure
* **AZURE_TENANT_ID**: field 'tenant' in response of command: az ad sp create-for-rbac on azure
* **AZURE_RESOURCE_GROUP**: name of resource group on azure
* **AZURE_KUBERNETES_NAME**: name of kubernetes (AKS) on azure
* **SPRING_PROFILES_ACTIVE**: active spring profiles for cpt-web-app

### AZURE
- create resource group (ARG)
- create Kubernetes service (AKS)
- execute commands:
  - az ad sp create-for-rbac --name "gitlab-cicd" --role contributor --scopes /subscriptions/<Subscription_ID>/resourceGroups/<ARG_name>
    - Subscription_ID & ARG_name you can find in resource group details
    - response has data for gitlab variables: AZURE_CLIENT_ID, AZURE_CLIENT_SECRET, AZURE_TENANT_ID
    

If commands throw errors with access denied etc., try to login first:
- az login
- az aks get-credentials --resource-group <ARG_name> --name <AKS_name>
