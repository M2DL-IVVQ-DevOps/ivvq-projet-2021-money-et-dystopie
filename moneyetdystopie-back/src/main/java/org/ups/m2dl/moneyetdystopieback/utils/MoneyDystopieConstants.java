package org.ups.m2dl.moneyetdystopieback.utils;

public class MoneyDystopieConstants {

    private MoneyDystopieConstants() {}

    public static final int TOKEN_LENGTH = 256;
    public static final int TOKEN_DURABILITY_IN_MINUTES = 10;
    public static final String COOKIE_NAME = "token";

    // TOKEN ERRORS
    public static final String DEFAULT_ERROR_CONTENT = "Erreur du serveur.";
    public static final String NOT_CONNECTED_ERROR =
        "Vous devez être connecté pour effectuer cette action.";
    public static final String INVALID_CONNEXION_ERROR =
        "Impossible de vous connecter avec les identifiants renseignés.";
    public static final String EXPIRED_CONNEXION_ERROR =
        "Votre session a expiré, veuillez vous reconnecter.";

    // SHOP ERRORS
    public static final String UNREFERENCED_SHOP_ERROR =
        "La boutique n'est pas référencée.";
    public static final String UNFOUND_REFERENCED_SHOP_ERROR =
        "La boutique référencée n'a pu être trouvée.";
    public static final String REGISTER_SHOP_ERROR =
        "Une erreur est survenue lors de l'enregistrement de la boutique.";
    public static final String REGISTER_UNDEFINED_SHOP_ERROR =
        "Une boutique non défini ne peut être sauvegardée.";

    // ITEM ERRORS
    public static final String UNDEFINED_ITEM_ERROR =
        "Un article non défini ne peut être sauvegardé.";
    public static final String REGISTER_ITEM_ERROR =
        "Une erreur est survenue lors de l'enregistrement de l'article.";
    public static final String INCORRECT_ITEM_SELLER =
        "Vous n'êtes pas le vendeur de l'article que vous souhaitez modifier.";
    public static final String UNREFERENCED_ITEM_ERROR =
        "L'article n'est pas référencé.";

    // CUSTOMER ERRORS
    public static final String REGISTER_CUSTOMER_ERROR =
        "Une erreur est survenue lors de l'enregistrement du client.";
    public static final String UNDEFINED_CUSTOMER_ERROR =
        "Impossible de trouver les données du client.";

    // USER ERRORS
    public static final String USER_NOT_CUSTOMER_OR_SELLER_ERROR =
        "Le compte doit être client ou commerçant.";
    public static final String UNDEFINED_USER_ERROR =
        "Un utilisateur non défini ne peut être sauvegardé.";
    public static final String REGISTER_USER_ERROR =
        "Une erreur est survenue lors de l'enregistrement de l'utilisateur.";

    // PASSWORD ERRORS
    public static final String PASSWORD_SIZE_ERROR =
        "Votre mot de passe doit faire au moins 8 caractères.";
    public static final String PASSWORD_UPPERCASE_ERROR =
        "Votre mot de passe doit contenir au moins une majuscule.";
    public static final String PASSWORD_LOWERCASE_ERROR =
        "Votre mot de passe doit contenir au moins une minuscule.";
    public static final String PASSWORD_DIGIT_ERROR =
        "Votre mot de passe doit contenir au moins un chiffre.";

    // COMMAND ERRORS
    public static final String WRONG_CART_NUMBER_ERROR =
        "Le numéro de carte bleue renseigné est incorrect. Paiement refusé.";
    public static final String UNDEFINED_COMMAND_ERROR =
        "Une commande non définie ne peut être sauvegardée.";
    public static final String REGISTER_COMMAND_ERROR =
        "Une erreur est survenue lors de l'enregistrement de la commande.";
}
