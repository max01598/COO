# POO rapport
## Introduction :

Dans ce rapport, nous allons vous expliquer nos choix de technologie utilisée pour construire ce système de clavardage. Ainsi faire un petit manuel d’utilisation du produit illustré de quelques images pour expliquer nos propos.

## Installation :
**Pré-requis :**
  - Java installé
  - Eclipse (IDE)
 

 **Configuration base de données**

Les différents paramètres d'accès à la base de données se trouvent dans les constantes du fichier chatApp.java se trouvant dans le dossier chatApp2/src/AppMain/.

Si vous souhaitez modifier le code et utiliser votre base de données vous devez modifier les constantes du fichier chatApp.java, puis après avoir recompilé, soit re exécuter le projet soit l'exporter en "Runnable jar". Par conséquent, vous devez alors exécuter la commande SQL suivante :

>CREATE TABLE Message

>(

>	id smallint unsigned not null auto_increment,

>	login_Emmeteur VARCHAR(50),

>	date Date,

>	login_Destinataire VARCHAR(50),

>	contenu VARCHAR(500),

>	constraint pk_example primary key (id)

>);

 **Caractéristiques des machines hôtes :**
 
Pour utiliser Applichat, les machines hôtes devront se trouver sur le même réseau. Et pour l’utilisation de la base de données telle qu'elle est configurée dans le code source, il vous faudra être sur une machine de l’INSA Toulouse.
Pour lancer l'application, il vous faut **se placer dans le dossier ChatApp2 et executer la commande : java -jar Applichat.jar. 

## Utiliser Applichat :

  Applichat est une application de clavardage permettant l’échange de messages entre utilisateurs se trouvant sur le même réseau. Pour l’installation, se reporter à la section : Installation. Pour rappel, Applichat permet de communiquer uniquement avec des utilisateurs du même réseau. 

### Premiere approche :

  À chaque utilisation d'Applichat, vous sera demandé un pseudo qui permettra aux autres utilisateurs de vous identifier. Ce pseudo doit être unique, pas d’inquiétude un pseudo qui ne respecte pas l’unicité sera refusé. Vous pouvez faire plusieurs tentatives si le pseudo est déjà pris pour trouver un pseudo unique. Attention, il est important de comprendre que votre pseudo est limité à l’utilisation actuelle, il n’est pas nécessaire de toujours avoir le même. Concrètement, c’est l’ordinateur avec son adresse IP qui permettra de récupérer l’historique des conversations effectuées sur la machine. Pour valider votre pseudo, vous pouvez appuyer sur la touche “Entrer” ou bien cliquer sur le bouton de connexion. **Les espaces peuvent faire buguer le projet.**

![alt text](https://raw.githubusercontent.com/max01598/COO/master/img/login.PNG)

### Utiliser Applichat :

  Une fois le pseudo validé, vous êtes redirigé vers l’interface principale de l’application, avec la liste des utilisateurs connectés qui se met à jour pendant l’utilisation. 
  
![alt text](https://raw.githubusercontent.com/max01598/COO/master/img/HomeView.PNG)

**Communiquer avec les autres utilisateurs :**

  Sur le côté gauche se trouve la liste des utilisateurs connectés en même temps que vous. Pour communiquer avec une personne, vous cliquez sur le nom de la personne avec qui vous souhaitez communiquer.

  Une fenêtre s’ouvre alors, permettant de communiquer avec l’autre utilisateur. L’historique de la conversation s’affiche instantanément. Vous pouvez écrire votre message à l’aide de la barre de texte qui se trouve en bas. 

![alt text](https://raw.githubusercontent.com/max01598/COO/master/img/chat.png)

**Changer son pseudo :** 

  Tout le long de l'utilisation, vous avez la possibilité de modifier votre pseudo. Pour ce faire, rendez-vous sur la page d'accueil, en haut à gauche se trouve l’option  “Gestion pseudo”. Cliquez dessus et vous serez redirigé vers une fenêtre permettant la modification du pseudo (le critère d’unicité s’applique toujours).

![alt text](https://raw.githubusercontent.com/max01598/COO/master/img/gestionPseudo.PNG)

**Se déconnecter :**

Pour vous déconnecter, vous pouvez, soit fermer la fenêtre d'accueil soit cliquer sur l’option “Déconnexion”. Les autres utilisateurs seront avertis et ne vous verront plus en tant qu’utilisateur connecté.



