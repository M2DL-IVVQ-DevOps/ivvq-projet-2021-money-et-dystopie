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
                <md-input id="email" v-model="email" type="email" name="email"/>
            </md-field>

            <md-field>
                <label>Mot de passe</label>
                <md-input v-model="password" type="password"></md-input>
            </md-field>

            <div>
                <md-button v-on:click="connexionAccount()" id="connection-button">Connexion</md-button>
            </div>
        </form>
    </div>
</template>

<script>
    export default {
        name: 'ConnexionAccount',
        data: function () {
            return {
                errors: [],
                password: null,
                email: null
            };
        },
        props: ['connexion'],
        methods: {
            connexionAccount(){
               this.connexion();
            },
            checkForm: function (e) {
                this.errors = [];

                if (!this.password) {
                    this.errors.push("Mot de passe requis.");
                }
                if (!this.email) {
                    this.errors.push('email requis.');
                } else if (!this.validemail(this.email)) {
                    this.errors.push('email valide requis.');
                }

                if (!this.errors.length) {
                    return true;
                }

                e.preventDefault();
            },
            validemail: function (email) {
                var re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
                return re.test(email);
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
