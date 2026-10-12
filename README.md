# capacity-planning

Capacity Planning Tool (CPT): plan which people work on which pod, by half-day.
Spring Boot backend (`modules/cpt-web-app`) and Angular UI (`modules/cpt-ui/cpt-ui-angular`).

**Live demo:** https://cpt-ui.lemoncoast-28aa3d0d.centralindia.azurecontainerapps.io
(in-memory database: data resets when the app restarts or scales to zero; no login yet, see #16)

## Run locally

**In the IDE:**
- SpringBoot: `CptWebApplication`
- VM arguments: `-DshowHSQLConsole=true -Djava.awt.headless=false`
- Active profile: `HSQLDB`
- UI: `npm start` in `modules/cpt-ui/cpt-ui-angular` (proxies `/api` to http://localhost:8080)

**With Docker:**
`docker compose up --build`, then open http://localhost:8081

**Tests:** `mvn -B -Pbuild-mvn verify`

## Deployment

CI/CD runs on GitHub Actions ([.github/workflows/ci-cd.yml](.github/workflows/ci-cd.yml)):

- **Pull requests into `main`:** backend tests and the production UI build.
- **Merge to `main`:** both images are built and pushed to GitHub Container Registry
  (`ghcr.io/amarpandav/capacity-planning/cpt-web-app` and `.../cpt-ui`, tagged with the commit SHA),
  then deployed to Azure Container Apps. The run summary shows the app URL.
  - Backend `cpt-web-app`: internal only, reachable from the UI.
  - UI `cpt-ui`: public; nginx proxies `/api` to the backend.
  - Both scale to zero when idle.
- **Rollback:** redeploy an older image, e.g.
  `az containerapp update -n cpt-web-app -g cpt-demo-rg --image ghcr.io/amarpandav/capacity-planning/cpt-web-app:<sha>`

**Azure setup (one-time):** `./scripts/azure-setup.sh` walks you through creating the Azure resources and the GitHub deploy identity (see #20).
run az login and above command and .env.azure file will be re-created. we can't push this file as my repo is public.
See [.env.azure.example](.env.azure.example) for the values it stores.
