package org.example.service;

import java.util.List ;
public interface ICrud <T>{
    public void ajouterEntite(T p) ;
    public void modifierEntite(T p) ;
    public  void supprimerEntite(T p) ;
}