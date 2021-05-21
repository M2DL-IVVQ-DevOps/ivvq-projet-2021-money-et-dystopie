package org.ups.m2dl.moneyetdystopieback.utils;

public class MoneyDystopieConstants {

    private MoneyDystopieConstants() {}

    public static final int TOKEN_LENGTH = 256;
    public static final int TOKEN_DURABILITY_IN_MINUTES = 10;

    //ERRORS
    public static final String DEFAULT_ERROR_CONTENT = "Erreur du serveur.";
    public static final String NOT_CONNECTED_ERROR = "Vous devez être connecté pour effectuer cette action.";
    public static final String INVALID_CONNEXION_ERROR = "Impossible de vous connecter avec les identifiants renseignés.";
    public static final String EXPIRED_CONNEXION_ERROR = "Votre session a expiré, veuillez vous reconnecter.";
}
