# Projet AL - La belle tondeuse

[![Workflow](https://github.com/SwannHERRERA/tondeuse-scala/actions/workflows/main.yml/badge.svg)](https://github.com/SwannHERRERA/tondeuse-scala/actions/workflows/main.yml)

## Informations

Ce projet est une implémentation de la tondeuse en Scala.

Il a été réalisé entièrement en pair programming, un seul utilisateur est donc présent dans les commits, même si ce
n'est pas représentatif de la réalisation du projet.

## Structure du projet

Ceci est le projet de validation du cours d'initiation à la programmation fonctionnelle. Le code source doit être écrit
dans le répertoire `./src/main/scala`. Vous pourrez créer autant de package que vous voulez.
Les tests unitaires doivent être écrit dans le répertoire `./src/test/scala`. Pour écrire des tests unitaires, veuillez
vous reporter à la section [Tests Unitaires](#tests-unitaires).

## Projet SBT

Ce projet est un application Scala standalone. Il est géré par `sbt`, le build tool Scala. Sa documentation est
disponible [ici](https://www.scala-sbt.org/1.x/docs/).

Nous allons lister ici une liste de commandes utiles avec `sbt`:

- `sbt`: cette commande lance un invite de commande interactif

- `run` (ou `sbt run` hors de l'invite de commande): lance la classe `Main` du projet `sbt`

- `compile` (ou `sbt compile` hors de l'invite de commande): lance la compilation de l'ensemble du projet `sbt` (compile
  toutes les classes)

- `test` (ou `sbt test` hors de l'invite de commande): lance les tests unitaires du projet `sbt`
