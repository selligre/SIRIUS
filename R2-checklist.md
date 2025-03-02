# Attentes techniques R2

- [ ] les WI R2

## CICD

- ### Projet
- [ ] Plus de “proto-front”
- [ ] Plus de dossiers inutiles “target, node_modules, etc”
- ### Integration
- [ ] Pas de branche par personne
- [ ] Pas de demo local sinon 0
    - #### Gestion de prod
        - [ ] Branche de prod (chaque commit est une release)

## Code

- ### Git
- [ ] La review aura lieu sur la branche de prod
- [ ] Pas de branche avec nom de personne
- [ ] Utiliser des branches feature
- [ ] Commits reguliers avec des messages clairs
- ### Decoupage tech termine
- [ ] Back = controller service repo
- [ ] Front = views components api
- ### Implementer une regle metier
- [ ] Presenter la doc fonctionnelle
- [ ] Fonction pure qui contient l’algo
- [ ] Commentaires javadoc sur les fonctions metier
- ### …ou une brique technique
- [ ] Implementer un design pattern
- [ ] Documentation technique detaillant la reflexion accompagné d’un diagramme technique

## Organisation du frontend = reutiliser les components

- ### page = 1 route = 1 url
- ### utilisation de components
    - topsection
        - [ ] header
        - [ ] nav
    - contentsection
        - [ ] content = flex
    - footersection