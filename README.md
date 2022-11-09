## sbt project compiled with Scala 3

### Usage

This is a normal sbt project. You can compile code with `sbt compile`, run it with `sbt run`, and `sbt console` will start a Scala 3 REPL.

For more information on the sbt-dotty plugin, see the
[scala3-example-project](https://github.com/scala/scala3-example-project/blob/main/README.md).

## Exemple de carte

                        Nord
    |      |      |      |      |      |      |
    |------|------|------|------|------|------|
    | 0, 5 | 1, 5 | 2, 5 | 3, 5 | 4, 5 | 5, 5 |
    | 0, 4 | 1, 4 | 2, 4 | 3, 4 | 4, 4 | 5, 4 |
WEST| 0, 3 | 1, 3 | 2, 3 | 3, 3 | 4, 3 | 5, 3 | EAST
    | 0, 2 | 1, 2 | 2, 2 | 3, 2 | 4, 2 | 5, 2 |
    | 0, 1 | 1, 1 | 2, 1 | 3, 1 | 4, 1 | 5, 1 |
    | 0, 0 | 1, 0 | 2, 0 | 3, 0 | 4, 0 | 5, 0 |
                        Sud


## Structure

Map : [][]
Orientation: enum(N, E, W, S)
état d'une tondeuse a un instant T: (0, 0, N)
Il peut y avoir plusieurs tondeuse.
Fonctionne avec une pile d'instruction :
A -> Avancer
D -> rotation Droit
G -> rotation Gauche
On ne peut pas sortir de la carte mais pas d'erreur juste aucun changement d'êtat

On Utilise un fichier de configuration
1 er ligne : X Y
Ensuite 2 lignes position de la tonseuse :
X Y Orientation
Instructions

Example :
5 5
1 2 N
GAGAGAGAA
