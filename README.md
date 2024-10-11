# L'âge de psyche
## Projet final du groupe A1-B2
### OYER Julien - VIEZ Rémi - PORET Théo - ROYER Nathan - DUJARDIN Thao - CAZO Joey

---

# L'âge de psyche

## Compiler et lancer le projet

Afin de lancer le projet, exécutez la commande suivante dans le dossier <b>psyche</b>

```bash
javac @compile.list -d ../class && cd ../class && java psyche.Controleur && cd ../psyche
```

Cette commande permet de compiler le projet dans le dossier <b>class</b> et de lancer le programme.

## Compiler et lancer le projet avec un scénario

Vous pouvez lancer différent scénario (De 1 à 3) avec cette commande

```bash
javac @compile.list -d ../class && cd ../class && java psyche.Controleur scenario_x.run && cd ../psyche
```

Où <i>x</i> est le numéro du scénario que vous souhaitez lancer.

## Compiler et lancer le projet avec un scénario et une map

Vous pouvez lancer différent scénario (De 1 à 3) avec une map personnalisée avec cette commande

```bash
javac @compile.list -d ../class && cd ../class && java psyche.Controleur scenario_x.run map.data && cd ../psyche
```

# Créateur de map

## Compiler et lancer le créateur de map

Cette application, indépendante de l'application principale, permet de créer des maps pour le jeu.

Afin de lancer le créateur de map, exécutez la commande suivante dans le dossier <b>routes</b>

```bash
javac @compile.list -d ../class && cd ../class && java routes.Controleur && cd ../routes
```

Placez ensuite le fichier <b>map.data</b> dans le dossier <b>routes/theme</b> pour pouvoir utiliser votre map dans l'application L'âge de psyche.

