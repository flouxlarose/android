package com.lyne.annexe3

import android.content.Context
import android.content.Context.MODE_PRIVATE
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

object SingletonSimple {

    //liste de mémos
    private var liste =  ArrayList<Memo>()

    //fonction pour récupérer la liste
    fun getListe(): ArrayList<Memo>
    {
        return liste
    }

    //fonction pour ajouter memo a la liste
    fun addMemo(memoAjouter : Memo)
    {
        liste.add(memoAjouter)
    }

    //écrire la liste dans un fichier de sérialisation
    fun serialiserListe(context: Context){

        try {
            val fos : FileOutputStream = context.openFileOutput("fichier.ser", MODE_PRIVATE)
            val oos = ObjectOutputStream(fos) //buffer spécial pour écrire des objets dans le flux de données binaire

            oos.use{
                oos.writeObject(liste)
            }
        }catch (e: Exception) {
            println("problème")

        }
    }

    //recupérer la liste du fichier de sérialisation et retourner une copie de la liste
    fun deserialiserListe(context: Context) : ArrayList<Memo>{
        //pour éviter d'utiliser le fichier de serialisation sans en avoir réellement besoin (ajouter 5 memos de suite)
        if(liste.isEmpty()){
            val fis : FileInputStream = context.openFileInput("fichier.ser")
            val ois = ObjectInputStream(fis)

            ois.use{
                liste = ois.readObject() as ArrayList<Memo>
            }
        }
        return liste
    }
}