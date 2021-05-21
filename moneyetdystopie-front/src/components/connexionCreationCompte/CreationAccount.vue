<template id="app">
    <div class="base">
        <form @submit="checkForm" action="https://vuejs.org/" method="post" novalidate="true">

            <div v-if="errors.length">
                <strong class=".error">Veuillez corriger les erreurs suivantes :</strong>
                <ul>
                    <li v-for="(error, index) in errors" v-bind:key="error+index">{{ error }}</li>
                </ul>
            </div>

            <md-field>
                <label>Nom</label>
                <md-input id="lastName" v-model="lastName" type="text" name="lastName"/>
            </md-field>

            <md-field>
                <label>Prénom</label>
                <md-input id="firstName" v-model="firstName" type="text" name="firstName"/>
            </md-field>

            <md-field>
                <label>Mail</label>
                <md-input id="email" v-model="email" type="email" name="email"/>
            </md-field>

            <md-field>
                <label>Mot de passe</label>
                <md-input v-model="password" type="password"></md-input>
            </md-field>

            <md-checkbox v-model="seller">Je souhaite être commerçant.</md-checkbox>

            <md-field v-if="seller">
                <label>Nom de ma boutique</label>
                <md-input v-model="storeName" type="text"></md-input>
            </md-field>

            <md-checkbox v-model="customer">Je souhaite être acheteur.</md-checkbox>

            <md-field v-if="customer">
                <label>Pseudo</label>
                <md-input v-model="pseudo" type="text"></md-input>
            </md-field>
            <md-field v-if="customer">
                <label>Adresse postale</label>
                <md-input v-model="address" type="text"></md-input>
            </md-field>
            <div v-if="accountCreationError">
                <label v-text="accountCreationErrorMessage"></label>
            </div>
            <div v-if="customer || seller">
                <md-button v-on:click="creationAccount()">Créer un compte</md-button>
            </div>
        </form>
    </div>
</template>

<script>
    export default {
        name: 'CreationCompte',
        data: function () {
            return {
                errors: [],
                lastName: null,
                firstName: null,
                password: null,
                email: null,
                storeName: null,
                pseudo: null,
                address: null,
                seller: false,
                customer: false,
                accountCreationError: false,
                accountCreationErrorMessage: null
            };
        },
        props: ['creation'],
        methods: {
            async creationAccount(){
                this.accountCreationError = false;
                this.accountCreationErrorMessage = "";
                let userCreation = {
                    lastName: this.lastName,
                    firstName: this.firstName,
                    email: this.email,
                    password: this.password
                };
                if (this.customer){
                    userCreation.customerAccount = {
                        pseudo: this.pseudo,
                        address: this.address
                    }
                }
                if (this.seller){
                    userCreation.sellerAccount = {
                        storeName: this.storeName
                    }
                }
                let returnedMessage = await this.creation(userCreation);
                console.log("Reçu : " + returnedMessage);
                if (returnedMessage !== ""){
                    this.accountCreationError = returnedMessage;
                    this.accountCreationErrorMessage = true;
                }
                this.creation(userCreation);
            },
            checkForm: function (e) {
                this.errors = [];

                if (!this.lastName) {
                    this.errors.push("Nom requis.");
                }
                if (!this.firstName) {
                    this.errors.push("Prenom requis.");
                }
                if (!this.password) {
                    this.errors.push("Mot de passe requis.");
                }
                if (!this.email) {
                    this.errors.push('Mail requis.');
                } else if (!this.validEmail(this.email)) {
                    this.errors.push('Mail valide requis.');
                }
                if (this.seller && !this.storeName) {
                    this.errors.push("Nom de votre boutique requise.");
                }
                if (this.customer && !this.pseudo) {
                    this.errors.push("Pseudo requis.");
                }
                if (this.customer && !this.address) {
                    this.errors.push("Adresse requise.");
                }

                if (!this.errors.length) {
                    return true;
                }

                e.preventDefault();
            },
            validEmail: function (email) {
                var re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
                return re.test(email);
            },
            purgeFields: function () {
                this.lastName = null;
                this.firstName = null;
                this.password = null;
                this.email = null;
                this.storeName = null;
                this.pseudo = null;
                this.address = null;
                this.seller = false;
                this.customer = false;
                this.error = false;
                this.errorMessage = "";
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
