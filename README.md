# java-go-system-network-optimisation

## Contexte
L'application sur laquelle nous allons travailler est une base de connaissance.

### Concepts
La logique de l'application se base sur la création de concepts, par exemple "Apache kafka".
À partir d'un concept il est possible de générer du contenu par IA afin d'enrichir sa base de connaissance.

### Flashcards
À partir d'un concept il est possible de générer des flashcards afin de reviser un concept.
Une flashcard est une carte recto verso. Sur le recto on retrouve une question et sur le verso sa réponse.
L'implémentation de l'agorithme "SuperMemo 2" a permis de programmer une révision de la flashcard dans le temps.

## Objectif
L'objectif de notre projet est de détecter (dans le contenu d’un concept) un lien vers un autre concept et de les lier.
Un lien vers un concept dans son contenu est représenter par le patern suivant : ```[[<titre du concept>]]"```

## Optimisations
Pour lancer les benchmarks, il faut lancer le projet.
Un json dans dossier report. (Pour afficher le graphique : python3 viz.py).

### Descriptif des optis : 

- V0   : Version Naive
- V0.1 : Utilisation du parallel pour regarder si le lien existe déjà
- V1   : Utilisation d'une map pour permettre de créer les edges
- V1.1 : Utilisation du parallel pour créer la map
- V2   : Mise en place des Virtuals Thread pour parraléliser le traitement
- V3   : Mise en place d'une constant pour le pattern de regex + Utilisation des Set
- V4   : Utilisation des substrings et des index pour remplacer le pattern matching regex
- V5   : Mise en place des Virtuals Thread pour la génération de la map

