<template>
    <div>
        <img src="https://cdn.dribbble.com/users/427368/screenshots/4750378/asset_35.jpg?compress=1&resize=800x600"/>
        <div class="flex">
            <div class="flex-contenu">
                <h2>Connexion</h2>
                <Connexion :connexion="connexion"></Connexion>
            </div>
            <div class="flex-contenu">
                <h2>Création d'un compte</h2>
                <CreationCompte :creation="creation"></CreationCompte>
            </div>
        </div>
    </div>
</template>

<script>
    import Connexion from "./Connexion";
    import CreationCompte from "./CreationCompte";

    export default {
        name: "ConnexionCreationCompte",
        components: {
            Connexion,
            CreationCompte
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
