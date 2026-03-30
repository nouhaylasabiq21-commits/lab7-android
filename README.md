"# lab7-android" 
# 🌟 StarsGallery - Android App
---

## 📱 Description

**StarsGallery** est une application Android qui affiche une liste de célébrités avec leurs images et leurs notes ⭐.

L’utilisateur peut :
- 🔍 Rechercher une star
- ⭐ Voir et modifier les notes
- 📤 Partager l’application
- 🚀 Voir un écran de démarrage (Splash)

---

## ✨ Fonctionnalités

- 🖼️ Images circulaires (CircleImageView)
- ⭐ RatingBar (notation)
- 🔍 Recherche dynamique (SearchView)
- ✏️ Modification de note (AlertDialog)
- 📤 Partage de l’application
- 🚀 Splash Screen

---

## 🧱 Architecture du projet


com.example.starsgallery
│
├── adapter
│ └── StarAdapter.java
│
├── beans
│ └── Star.java
│
├── dao
│ └── IDao.java
│
├── service
│ └── StarService.java
│
├── ui
│ ├── SplashActivity.java
│ └── ListActivity.java


---

## 🖼️ Demo

https://github.com/user-attachments/assets/51d29e25-cfe0-4ee3-89cf-3af4963d992b


---

## ⚙️ Technologies utilisées

- Java ☕
- Android SDK 📱
- RecyclerView
- ConstraintLayout
- CircleImageView
- AlertDialog
- SearchView

---


# Les images sont stockées localement dans :

res/drawable/

Exemple :

emma.jpg
tom.jpg
scarlett.jpg
leo.jpg
star.png

Dans le code :

new Star("Emma Watson", "emma", 4.5f);

Chargement :

int resId = context.getResources().getIdentifier(
    star.getImg(),
    "drawable",
    context.getPackageName()
);
holder.imgStar.setImageResource(resId);


#👨‍💻 Auteur
Nom : NOUHAYLA SABIQ
Projet : TP Android – StarsGallery
