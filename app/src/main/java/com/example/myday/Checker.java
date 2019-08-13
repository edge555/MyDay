package com.example.myday;

public class Checker {
    public boolean name(String a){
        int i;
        for(i=0;i<a.length();i++){
            if(!((a.charAt(i)>='a' && a.charAt(i)<='z') || (a.charAt(i)>='A' && a.charAt(i)<='Z') || a.charAt(i)==' '))
                return false;
        }
        if(a.length()<6 || a.length()>20)
            return false;
        return true;
    }
    public boolean pass(String a){
        int i;
        for(i=0;i<a.length();i++){
            if(a.charAt(i)==' ')
                return false;
        }
        if(a.length()<6 || a.length()>20)
            return false;
        return true;
    }
    public boolean username(String a){
        int i;
        for(i=0;i<a.length();i++){
            if(!((a.charAt(i)>='a' && a.charAt(i)<='z') || (a.charAt(i)>='0' && a.charAt(i)<='9')))
                return false;
        }
        if(a.length()<6 || a.length()>20)
            return false;
        return true;
    }
}
