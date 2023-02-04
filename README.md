![Android](https://img.shields.io/badge/Android-Studio-blue)
# Projet Maréu
## Présentation 
Le projet __Maréu__ est une application Android qui permet de gérer et planifier des réunions au sein de l'entreprise Lamzone.

Le projet se concentre sur l'utilisation de l'architecture MVP.

Les sources de l'application sont dans le répertoire __/Android/Mareu__.

Le projet est développé en __java__ avec __Android Studio__.

## Utilisation de Java
- Langage très populaire (grande communauté)
- Langage ancré (librairies et outils à disposition)
- Mises à jour fréquentes
- Langage natif du sdk

## Mise en place
- Téléchargez le code du projet, de préférence en utilisant git clone.  
- Télécharger __Android Studio__ : <https://developer.android.com/studio>  
- Dans Android Studio, sélectionnez Fichier | Ouvrez ... et pointez sur le répertoire ./Android/Mareu  

## Compilation et exécution
- Pour compiler le projet : Menu Build->Make project (Ctrl + F9)  
- Pour exécuter l’application : Menu Run->Run… (Alt+Maj+F10) puis sélectionner "app"  
- Pour exécuter les tests unitaires : Menu Run->Run… (Alt+Maj+F10) puis sélectionner ""  
- Pour exécuter les tests instrumentalisés : Menu Run->Run… (Alt+Maj+F10) puis sélectionner ""  

## Les tests unitaires
Des tests unitaires sont disponibles dans le répertoire /src/test/  
Ils utilisent junit4.  
Les méthodes disponibles sont :  
- emptyMeetingsAtServiceStartup() : Test qui vérifie qu'il n'y a aucune réunion dans le service au démarrage.
- getMeetings() : Test qui vérifie qu'il y a bien 4 réunions dans le service (après voir ajouté quatre réunions).
- addMeetings() : Test qui vérifie que les réunions sont bien ajoutées au service.
- deleteMeetings() : Test qui vérifie que les réunions sont bien effacées du service.
- sortMeetingsByDatetime() : Test qui vérifie que les réunions sont bien classées par date.

## Les tests instumentalisés
Des tests instrumentalisés sont disponibles dans le répertoire /src/androidTest/  
Ils utilisent junit et Espresso  
Les méthodes disponibles sont : 
- meetingsListTest_shouldNotBeEmpty() : Test qui vérifie que la liste des meetings n'est pas vide.
- meetingsListTest_shouldContainFourMeetings() : Test qui vérifie que la liste contient 4 réunions.
- meetingsListTest_createAction_shouldAddItem() : Test qui vérifie qu'une réunion a bien été ajoutée.
- meetingsListTest_deleteAction_shouldRemoveItem() : Test 
- meetingsListTest_filterAction_shouldHideItem()
- 
- Test qu'une réunion a bien été ajoutée
- Test the meetings drop meeting action on the list (recycler view)
