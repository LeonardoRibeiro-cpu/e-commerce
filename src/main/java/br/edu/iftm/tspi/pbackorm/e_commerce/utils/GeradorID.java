package br.edu.iftm.tspi.pbackorm.e_commerce.utils;

import java.util.Random;

public class GeradorID {
    
    private static final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static String gerarID(){
        Random random = new Random();
        StringBuilder id = new StringBuilder();
        for(int i = 0; i < 5; i++) {
            id.append(chars.charAt(random.nextInt(chars.length())));
        }
        return id.toString();
    }
}
