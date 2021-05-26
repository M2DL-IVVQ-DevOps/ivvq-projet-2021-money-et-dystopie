package org.ups.m2dl.moneyetdystopieback.utils;

public class MoneyDystopieConstants {

    private MoneyDystopieConstants() {}

    public static final int TOKEN_LENGTH = 256;
    public static final int TOKEN_DURABILITY_IN_MINUTES = 10;
    public static final String COOKIE_NAME = "token";

    // ERRORS
    public static final String DEFAULT_ERROR_CONTENT = "Erreur du serveur.";
    public static final String NOT_CONNECTED_ERROR =
        "Vous devez être connecté pour effectuer cette action.";
    public static final String INVALID_CONNEXION_ERROR =
        "Impossible de vous connecter avec les identifiants renseignés.";
    public static final String EXPIRED_CONNEXION_ERROR =
        "Votre session a expiré, veuillez vous reconnecter.";

    public static final String UNREFERENCED_SHOP_ERROR =
        "La boutique n'est pas référencée.";
    public static final String UNFOUND_REFERENCED_SHOP_ERROR =
        "La boutique référencée n'a pu être trouvée.";
    public static final String REGISTER_SHOP_ERROR =
        "Une erreur est survenue lors de l'enregistrement de la boutique.";
    public static final String REGISTER_UNDEFINED_SHOP_ERROR =
        "Une boutique non défini ne peut être sauvegardé.";

    public static final String UNDEFINED_ITEM_ERROR =
        "Un article non défini ne peut être sauvegardé.";
    public static final String REGISTER_ITEM_ERROR =
        "Une erreur est survenue lors de l'enregistrement de l'article.";

    public static final String REGISTER_CUSTOMER_ERROR =
        "Une erreur est survenue lors de l'enregistrement de l'acheteur.";
    public static final String UNDEFINED_CUSTOMER_ERROR =
        "Un acheteur non défini ne peut être sauvegardé.";

    public static final String USER_NOT_CUSTOMER_OR_SELLER_ERROR =
        "Le compte doit être acheteur ou commerçant.";
    public static final String UNDEFINED_USER_ERROR =
        "Un utilisateur non défini ne peut être sauvegardé.";
    public static final String REGISTER_USER_ERROR =
        "Une erreur est survenue lors de l'enregistrement de l'utilisateur.";

    public static final String PASSWORD_SIZE_ERROR =
        "Votre mot de passe doit faire au moins 8 caractères.";
    public static final String PASSWORD_UPPERCASE_ERROR =
        "Votre mot de passe doit contenir au moins une majuscule.";
    public static final String PASSWORD_LOWERCASE_ERROR =
        "Votre mot de passe doit contenir au moins une minuscule.";
    public static final String PASSWORD_DIGIT_ERROR =
        "Votre mot de passe doit contenir au moins un chiffre.";
}
