/plugin

run: claude plugins install mattpocock-skills
that created -> 
~/Code/AWS/capacity-planning/.claude/settings.json

run: /setup-matt-pocock-skills
result:
I've set up the Matt Pocock skills for this repo. I created four files and haven't committed anything:

- CLAUDE.md: the ## Agent skills section, pointing to the three files below.
- docs/agents/issue-tracker.md: issues live in GitHub Issues and are managed with the gh command-line tool. Pull requests are not treated as requests to triage.
- docs/agents/triage-labels.md: the five default labels (needs-triage, needs-info, ready-for-agent, ready-for-human, wontfix).
- docs/agents/domain.md: the repo is treated as one context, with one CONTEXT.md and docs/adr/ at the root. Neither exists yet; /domain-modeling will create them when needed.


~/Code/AWS/capacity-planning/docs/agents/triage-labels.md