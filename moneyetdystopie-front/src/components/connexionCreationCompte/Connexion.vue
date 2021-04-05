<template id="app">
    <div class="base">
        <form @submit="checkForm" action="https://vuejs.org/" method="post" novalidate="true">

            <div v-if="errors.length">
                <b>Please correct the following error(s):</b>
                <ul>
                    <li v-for="(error, index) in errors" v-bind:key="error+index">{{ error }}</li>
                </ul>
            </div>

            <md-field>
                <label>Mail</label>
                <md-input id="mail" v-model="mail" type="mail" name="mail"/>
            </md-field>

            <md-field>
                <label>Mot de passe</label>
                <md-input v-model="motDePasse" type="password"></md-input>
            </md-field>

            <div>
                <md-button v-on:click="connexionCompte()">Connexion</md-button>
            </div>
        </form>
    </div>
</template>

<script>
    export default {
        name: 'Connexion',
        data: function () {
            return {
                errors: [],
                motDePasse: null,
                mail: null
            };
        },
        props: ['connexion'],
        methods: {
            connexionCompte(){
                console.log("coucou");
               this.connexion();
            },
            checkForm: function (e) {
                this.errors = [];

                if (!this.motDePasse) {
                    this.errors.push("Mot de passe requis.");
                }
                if (!this.mail) {
                    this.errors.push('Mail requis.');
                } else if (!this.validmail(this.mail)) {
                    this.errors.push('Mail valide requis.');
                }

                if (!this.errors.length) {
                    return true;
                }

                e.preventDefault();
            },
            validmail: function (mail) {
                var re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
                return re.test(mail);
            }
        }
    }
</script>

<style scoped>
    h2{
        color: black;
    }
    .base{
        background-color: white;
        width: 60%;
        margin: 0 auto;
        border-radius: 30px;
        padding: 50px;
    }
</style>
