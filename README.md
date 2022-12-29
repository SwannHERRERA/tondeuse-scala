# Projet AL - La belle tondeuse

[![Workflow](https://github.com/SwannHERRERA/tondeuse-scala/actions/workflows/main.yml/badge.svg)](https://github.com/SwannHERRERA/tondeuse-scala/actions/workflows/main.yml)

## Informations

Ce projet est une implémentation de la tondeuse en Scala.

Il a été réalisé entièrement en pair programming, un seul utilisateur est donc présent dans les commits, même si ce
n'est pas représentatif de la réalisation du projet.

Les spécifications du projet sont disponibles dans le fichier [sujet.md](./sujet.md).

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

## Fichier d'entrée

Le fichier de configuration est un fichier texte qui contient les informations de la tondeuse et de la pelouse.

Pour un exemple où le fichier suivant est fourni en entrée :

```
5 5
1 2 N
GAGAGAGAA
3 3 E
AADAADADDA
```

Les 2 tondeuses devront respectivement se terminer avec les positions suivantes :

```
1 3 N
5 1 E
```

Il est aussi possible de renseigner ces informations en console.

## Configuration

Le fichier de configuration est un fichier texte qui contient la configuration du comportement du programme.

Il se trouve dans le dossier `./src/main/resources` et s'appelle `application.conf`.

Il est de la forme suivante :

```conf
application {
  name = "FunProg"
  name = ${?APP_NAME} # Pour surcharger la valeur par un variable d'environnement

  input-mode = "file"
  input-mode = ${?INPUT_MODE} # file / console

  output-mode = "file"
  output-mode = ${?APP_OUTPUT_MODE} # console / file / both

  input-file = "./input/input.txt"
  input-file = ${?INPUT_FILE}

  output-json-file = "./out/output.json"
  output-json-file = ${?OUTPUT_JSON_FILE}

  output-csv-file = "./out/output.csv"
  output-csv-file = ${?OUTPUT_CSV_FILE}

  output-yaml-file = "./out/output.yaml"
  output-yaml-file = ${?OUTPUT_YAML_FILE}
}
```

Les paramètres sont les suivants :

- `application.name` : Nom de l'application
- `application.input-mode` : Mode d'entrée des données, `file` pour un fichier ou `console` pour la console
- `application.output-mode` : Mode de sortie des données, `file` pour un fichier ou `console` pour la console ou `both`
  pour les deux
- `application.input-file` : Chemin du fichier d'entrée
- `application.output-json-file` : Chemin du fichier de sortie au format JSON
- `application.output-csv-file` : Chemin du fichier de sortie au format CSV
- `application.output-yaml-file` : Chemin du fichier de sortie au format YAML

## Tests Unitaires

Les tests unitaires sont écrits avec le framework de test `ScalaTest`. Sa documentation est disponible
[ici](https://www.scalatest.org/user_guide).

Les tests unitaires sont écrits dans le répertoire `./src/test/scala`. Vous pouvez créer autant de package que vous
voulez.

Pour lancer les tests unitaires, vous pouvez utiliser la commande `sbt test` ou `test` dans l'invite de commande `sbt`.

