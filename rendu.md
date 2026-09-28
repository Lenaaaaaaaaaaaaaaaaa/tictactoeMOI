# TP3 Refactoring

Etudiante : Léna Sagon

## Refactoring

### Question 2.1 

Après avoir analysé la qualité du code avec sonarCube, j'ai trouvé deux problèmes dans la classe Board, que j'ai résolu.

- Remove this empty statement L13 -> enum créée dans son propre fichier
- Replace this if-then-else statement by a single return statemen L92 -> un seul return pour toute la fonction

### Question 2.2 

Afin de pouvoir rendre le code plus générique pour qu'il soit facilement extensible à d'autres jeux, il faut : 

- passer la taille du plateau dans le  constructeur, valeurs mises en attribut, modif des méthodes nécéssaires
- passer le nombre de pions nécessaire pour avoir une victoire en paramètre de constructeur, modif des méthodes nécessaires
- pour l'ajout de contraintes de pose, comme la gravité pour le puissance 4, j'ai mis en place une interface GameRule avec la fonction isValid pour vérifier que le placemement du pion suit les règles.
- pour l'ajout de type de pions j'ai créé un enum Category ainsi que l'ajout d'un attribut à la classe Cell
