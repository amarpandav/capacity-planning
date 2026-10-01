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

/ask-matt what should i do next:y
1. Merge setup-agent-skills into main. It has one commit (d1ac487) and nothing else depends on it waiting. Before merging, fix line 1 of CLAUDE.md. It reads /ask##  
   Agent skills, which looks like a stray paste, and it breaks that heading.
2. Fix the broken build (issue #3): "mvn clean install fails on root pom because of modules/cpt-ui". This is your only open issue, and it's an on-ramp. Until mvn    
   clean install works, /tdd and /implement have no feedback loop, so it blocks everything else.
    - Run /diagnosing-bugs on it. The failing mvn clean install already gives you the one red command the skill needs.
    - The issue is labelled needs-triage. That's mainly useful when you have a pile of incoming issues; with just one, go straight to fixing it.
3. After that, choose based on what you want:
    - To build a feature: run /grill-with-docs on the idea. The repo has no CONTEXT.md or docs/adr/ yet, so this also starts building the project's shared vocabulary.
      The repo's recent commits mention a "userSkillsDialog", which would make a good first topic.
    - No feature in mind: run /improve-codebase-architecture. The code hasn't been touched in about two years, so it should turn up candidates you can feed into      
      /grill-with-docs.