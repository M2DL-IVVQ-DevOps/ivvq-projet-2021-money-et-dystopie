package org.ups.m2dl.moneyetdystopieback.utils;

public class MoneyDystopieConstants {

    private MoneyDystopieConstants() {}

    // TOKEN
    public static final int TOKEN_LENGTH = 256;
    public static final int TOKEN_DURABILITY_IN_MINUTES = 10;
    public static final String COOKIE_NAME = "token";

    public static final String DEFAULT_ERROR_CONTENT = "Erreur du serveur.";
    // TOKEN ERRORS
    public static final String INVALID_CONNEXION_ERROR =
        "Impossible de vous connecter avec les identifiants renseignés.";
    public static final String EXPIRED_CONNEXION_ERROR =
        "Votre session a expiré, veuillez vous reconnecter.";

    // SHOP ERRORS
    public static final String UNDEFINED_SHOP_ERROR =
        "Une erreur est survenue lors de la récupération de la boutique.";
    public static final String REGISTER_SHOP_ERROR =
        "Une erreur est survenue lors de l'enregistrement de la boutique.";
    public static final String SHOP_ALREADY_EXISTS_ERROR =
            "Le nom de boutique '%s' est déjà utilisé par un autre utilisateur. Veuillez en choisir un autre.";

    // ITEM ERRORS
    public static final String UNDEFINED_ITEM_ERROR =
        "Une erreur est survenue lors de la récupération de l'article.";
    public static final String REGISTER_ITEM_ERROR =
        "Une erreur est survenue lors de l'enregistrement de l'article.";
    public static final String INCORRECT_ITEM_SELLER =
        "Vous devez être le vendeur d'un article pour pouvoir le modifier.";

    // CUSTOMER ERRORS
    public static final String UNDEFINED_CUSTOMER_ERROR =
        "Une erreur est survenue lors de la récupération du client.";
    public static final String REGISTER_CUSTOMER_ERROR =
        "Une erreur est survenue lors de l'enregistrement du client.";
    public static final String PSEUDO_ALREADY_EXISTS_ERROR =
            "Le pseudo '%s' est déjà utilisé par un autre utilisateur. Veuillez en choisir un autre.";

    // USER ERRORS
    public static final String USER_NOT_CUSTOMER_OR_SELLER_ERROR =
        "Un compte doit être client ou commerçant.";
    public static final String UNDEFINED_USER_ERROR =
        "Une erreur est survenue lors de la récupération de l'utilisateur.";
    public static final String REGISTER_USER_ERROR =
        "Une erreur est survenue lors de l'enregistrement de l'utilisateur.";
    public static final String USER_ALREADY_EXISTS_ERROR =
            "L'email '%s' est déjà utilisé par un autre utilisateur. Veuillez en choisir un autre.";

    // PASSWORD ERRORS
    public static final String PASSWORD_SIZE_ERROR =
        "Votre mot de passe doit faire au moins 8 caractères.";
    public static final String PASSWORD_UPPERCASE_ERROR =
        "Votre mot de passe doit contenir au moins une majuscule.";
    public static final String PASSWORD_LOWERCASE_ERROR =
        "Votre mot de passe doit contenir au moins une minuscule.";
    public static final String PASSWORD_DIGIT_ERROR =
        "Votre mot de passe doit contenir au moins un chiffre.";

    // ITEM COMMAND ERRORS
    public static final String UNDEFINED_ITEM_COMMAND_ERROR =
        "Une erreur est survenue lors de la récupération de l'item de commande.";
    public static final String REGISTER_ITEM_COMMAND_ERROR =
        "Une erreur est survenue lors de l'enregistrement de l'item de commande.";
    public static final String ITEM_COMMAND_AMOUNT_ERROR =
        "La quantité d'items demandée pour la commande est supérieure à celle pouvant être fournie.";

    // COMMAND ERRORS
    public static final String WRONG_CARD_NUMBER_ERROR =
        "Paiement refusé : Le numéro de carte bleue renseigné est incorrect.";
    public static final String UNDEFINED_COMMAND_ERROR =
        "Une erreur est survenue lors de la récupération de la commande.";
    public static final String REGISTER_COMMAND_ERROR =
        "Une erreur est survenue lors de l'enregistrement de la commande.";
}
