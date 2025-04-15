# java-go-system-network-optimasation

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
