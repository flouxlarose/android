package com.lyne.annexe3

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
}