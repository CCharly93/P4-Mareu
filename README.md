![Android](https://img.shields.io/badge/Android-Studio-blue)
# Projet Entrevoisins
## Présentation 
Le projet __Entrevoisins__ est une application Android qui permet de mettre en relation des personnes d'un même quartier afin qu'ils se rendent de petits services.
Cette application appartient à l'entreprise du même nom __Entrevoisins__.

Les sources de l'application sont dans le répertoire __/Android/Entrevoisins__.

Le projet est développé en __java__ avec __Android Studio__.

## Mise en place
- Téléchargez le code du projet, de préférence en utilisant git clone.  
- Télécharger __Android Studio__ : <https://developer.android.com/studio>  
- Dans Android Studio, sélectionnez Fichier | Ouvrez ... et pointez sur le répertoire ./Android/Entrevoisins  

## Compilation et exécution
- Pour compiler le projet : Menu Build->Make project (Ctrl + F9)  
- Pour exécuter l’application : Menu Run->Run… (Alt+Maj+F10) puis sélectionner "app"  
- Pour exécuter les tests unitaires : Menu Run->Run… (Alt+Maj+F10) puis sélectionner "NeighbourServiceTest"  
- Pour exécuter les tests instrumentalisés : Menu Run->Run… (Alt+Maj+F10) puis sélectionner "NeighboursListTest"  

## Les tests unitaires
Des tests unitaires sont disponibles dans le répertoire /src/test/  
Ils utilisent junit4.  
Les méthodes disponibles sont :  
- getNeighboursWithSuccess() : Test qui vérifie que la liste initiale correspond bien aux voisins générés.
- deleteNeighbourWithSuccess() : Test la suppression d'un voisin.
- getFavoriteNeighboursListNotEmpty() : Test qui vérifie que la liste des voisins favoris n'est pas vide.
- enableFavoriteNeighbourWithSuccess() : Test qui vérifie la bonne modification d'un voisin en favoris.
- disableFavoriteNeighbourWithSuccess() : Test qui vérifie le retrait d'un voisin favoris en voisin "commun".

## Les tests instumentalisés
Des tests instrumentalisés sont disponibles dans le répertoire /src/androidTest/  
Ils utilisent junit et Espresso  
Les méthodes disponibles sont :  
- myNeighboursList_shouldNotBeEmpty() : Des données doivent s’afficher dans la listes des voisins.
- myNeighboursList_deleteAction_shouldRemoveItem() : Test du bouton supprimer dans la liste des voisins.
- myNeighboursList_onClickItem_shouldOpenProfileActivity() : Test l’ouverture de la fenêtre de détail (page profil) d’un voisin.
- nameText_onProfileActivity_isCorrect() : Test que l’ouverture de la page profil d’un voisin affiche le bon voisin.
- myFavoritesList_onFavoriteTabItem_showOnlyFavoriteNeighbours() : Test qui vérifie que l'onglet Favoris ne montre que les voisins favoris.
