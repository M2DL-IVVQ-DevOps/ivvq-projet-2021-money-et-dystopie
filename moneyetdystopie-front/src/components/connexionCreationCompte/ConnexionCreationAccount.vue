<template>
    <div>
        <img src="https://cdn.dribbble.com/users/427368/screenshots/4750378/asset_35.jpg?compress=1&resize=800x600"/>
        <div class="flex">
            <div class="flex-contenu">
                <h2>Connexion</h2>
                <ConnexionAccount :connexion="connexion"></ConnexionAccount>
            </div>
            <div class="flex-contenu">
                <h2>Création d'un compte</h2>
                <CreationAccount :creation="creation"></CreationAccount>
            </div>
        </div>
    </div>
</template>

<script>
    import ConnexionAccount from "./ConnexionAccount";
    import CreationAccount from "./CreationAccount";

    export default {
        name: "ConnexionCreationCompte",
        components: {
            ConnexionAccount,
            CreationAccount
        },
        props: ['connexion', 'creation'],
    }
</script>

<style scoped>
    .flex-contenu{
        width: 50%;
    }
    .flex{
        display: flex;
    }
</style>
