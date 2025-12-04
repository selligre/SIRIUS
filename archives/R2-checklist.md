# Attentes techniques R2

- [ ] les WI R2

## CICD

- ### Projet
- [x] Plus de “proto-front”
- [x] Plus de dossiers inutiles “target, node_modules, etc”
- ### Integration
- [x] Pas de branche par personne
- [x] Pas de demo local sinon 0
    - #### Gestion de prod
        - [x] Branche de prod (chaque commit est une release)

## Code

- ### Git
- [ ] La review aura lieu sur la branche de prod
- [x] Pas de branche avec nom de personne
- [x] Utiliser des branches feature
- [x] Commits reguliers avec des messages clairs
- ### Decoupage tech termine
- [x] Back = controller service repo
- [x] Front = views components api
- ### Implementer une regle metier
- [ ] Presenter la doc fonctionnelle
- [x] Fonction pure qui contient l’algo
- [x] Commentaires javadoc sur les fonctions metier
- ### …ou une brique technique
- [x] Implementer un design pattern
- [x] Documentation technique detaillant la reflexion accompagné d’un diagramme technique

## Organisation du frontend = reutiliser les components

- ### page = 1 route = 1 url
- ### utilisation de components
    - topsection
        - [ ] header
        - [ ] nav
    - contentsection
        - [ ] content = flex
    - footersection